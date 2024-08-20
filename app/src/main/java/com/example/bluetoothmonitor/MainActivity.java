package com.example.bluetoothmonitor;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private MenuItem menuItem;
    private BluetoothAdapter bluetoothAdapter;
    private final int ENABLE_REQUEST = 10;
    private boolean isPortrait = true;
    private static MyView imgview;
    private static final int REQ_ENABLE_BT = 10;
    private SharedPreferences pref;
    private BtConnection btConnection;
    private float mScale = 1f;
    int count = 0;
    float x1;
    float x2;
    float x3;
    private static final int REQUEST_BLUETOOTH_CONNECT = 1;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        imgview.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this, new GestureListener());

//        Button orientationButton = findViewById(R.id.orientation_button);
//
//        orientationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isPortrait) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                } else {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                }
//                isPortrait = !isPortrait;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.id_bt_button);
        setBtIcon();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.id_bt_button) {
            if (!bluetoothAdapter.isEnabled()) {
                enableBt();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                            REQUEST_BLUETOOTH_CONNECT);
                    return true; // Возвращаем true, чтобы предотвратить выполнение кода до получения разрешения
                }
                bluetoothAdapter.disable();
                menuItem.setIcon(R.drawable.bluetooth_enable);
            }
        } else if (id == R.id.id_menu) {
            if (bluetoothAdapter.isEnabled()) {
                Intent i = new Intent(MainActivity.this, BtListActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Включите блютуз для перехода на этот экран!",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.orientations_button) {
            // Смена ориентации экрана
            if (isPortrait) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            isPortrait = !isPortrait; // Инвертируем флаг ориентации
        } else if (id == R.id.clear) {
            imgview.clearZone();
        } else if (id == R.id.half_zone) {
            imgview.halfZone();
        } else if (id == R.id.full_zone) {
            imgview.fullZone();
        }

        return super.onOptionsItemSelected(item);
    }

    private void enableBt() {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivityForResult(i, ENABLE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENABLE_REQUEST) {
            if (resultCode == RESULT_OK) {
                setBtIcon();
            }
        }
    }

    private void init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btConnection = new BtConnection(this);
        imgview = findViewById(R.id.imageView);
    }

    private void setBtIcon() {
        if (bluetoothAdapter.isEnabled()) {
            menuItem.setIcon(R.drawable.bluetooth_disable);
        } else {
            menuItem.setIcon(R.drawable.bluetooth_enable);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (count == 0) {
                    x1 = motionEvent.getX();
                    count++;
                }
                if (count == 1) {
                    x2 = motionEvent.getX();
                    if (x1 - x2 >= view.getWidth() / 4 || x1 - x2 <= -view.getWidth() / 4) {
                        if (x1 - x2 > 60) {
                            x3 = 45;
                            count = 3;
                        }

                        if (x1 - x2 < 60) {
                            x3 = 45;
                            count = 3;
                        }
                    }
                }
                if (count == 3) {
                    count = 0;
                }
                break;
        }
        return true;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }
}
