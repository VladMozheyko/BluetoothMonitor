package com.example.bluetoothmonitor;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BtListActivity extends AppCompatActivity {
    private final int BT_REQUEST_PERM = 111;
    private ListView listView;
    private BTadapter adapter;
    private BluetoothAdapter btAdapter;
    private List<ListItem> list;
    private boolean isBtPermissionGranted = false;
    private boolean isDiscovery = false;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_list);
        getBtPermission();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(bReciever, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(bReciever);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bt_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isDiscovery) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    requestBluetoothPermissions();
                    return true;
                }
                btAdapter.cancelDiscovery();
                isDiscovery = false;
                getPairedDevices();
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.id_search) {
            if (isDiscovery) return true;
            ab.setTitle(R.string.discovering);
            list.clear(); // Очистить список перед началом нового поиска
            ListItem itemTitle = new ListItem();
            itemTitle.setItemType(BTadapter.TITLE_ITEM_TYPE);
            list.add(itemTitle);
            adapter.notifyDataSetChanged();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                requestBluetoothPermissions();
                return true;
            }
            btAdapter.startDiscovery();
            isDiscovery = true;
        }
        return true;
    }

    private void requestBluetoothPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT
                },
                BT_REQUEST_PERM);
    }

    private void init() {
        ab = getSupportActionBar();
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        list = new ArrayList<>();
        if (ab == null) return;
        ab.setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listView);
        adapter = new BTadapter(this, R.layout.bt_list_item, list);
        listView.setAdapter(adapter);
        getPairedDevices();
        onItemClickListener();
    }

    private void onItemClickListener() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log.d("MyLog", "Item click");
            ListItem item = (ListItem) parent.getItemAtPosition(position);
            if (item.getItemType().equals(BTadapter.DISCOVERY_ITEM_TYPE)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    requestBluetoothPermissions();
                    return;
                }
                item.getBtDevice().createBond();
            }
        });
    }

    private void getPairedDevices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            requestBluetoothPermissions();
            return;
        }
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            list.clear(); // Очистить список перед добавлением сопряженных устройств
            for (BluetoothDevice device : pairedDevices) {
                ListItem item = new ListItem();
                item.setBtDevice(device);
                list.add(item);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BT_REQUEST_PERM) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                isBtPermissionGranted = true;
                Toast.makeText(this, "Разрешения получены!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Нет разрешения на использование Bluetooth!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getBtPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
            }, BT_REQUEST_PERM);
        } else {
            isBtPermissionGranted = true;
        }
    }

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Проверяем, чтобы не добавлять уже существующие устройства
                if (!isDeviceInList(device)) {
                    ListItem item = new ListItem();
                    item.setBtDevice(device);
                    item.setItemType(BTadapter.DISCOVERY_ITEM_TYPE);
                    list.add(item);
                    adapter.notifyDataSetChanged();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                isDiscovery = false;
                ab.setTitle(R.string.app_name);
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                // Не обрабатываем состояние сопряжения, если хотите отображать только доступные устройства
            }
        }
    };

    private boolean isDeviceInList(BluetoothDevice device) {
        for (ListItem item : list) {
            if (item.getBtDevice().getAddress().equals(device.getAddress())) {
                return true;
            }
        }
        return false;
    }
}
