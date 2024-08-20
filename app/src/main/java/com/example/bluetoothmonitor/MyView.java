package com.example.bluetoothmonitor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;


import com.example.bluetoothmonitor.GraphElements.GraphElement;
import com.example.bluetoothmonitor.GraphElements.Type1;
import com.example.bluetoothmonitor.GraphElements.Type2;
import com.example.bluetoothmonitor.GraphElements.Type3;
import com.example.bluetoothmonitor.GraphElements.Type5;
import com.example.bluetoothmonitor.GraphElements.Type6;

import java.util.ArrayList;
import java.util.List;

public class MyView extends androidx.appcompat.widget.AppCompatImageView {
    TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    int delHeight = 255, delWidth = 255;
    int unitH, unitW;
    boolean flag = true;
    int counter = 5;

    int[] globalColors = new int[]{Color.BLACK, Color.WHITE, Color.YELLOW, Color.RED, Color.BLUE,
            Color.MAGENTA, Color.GRAY, Color.MAGENTA, Color.LTGRAY};

    private List<GraphElement> elements;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        elements = new ArrayList<>();






    }

    public void addElement(GraphElement element) {
        elements.add(element);
        invalidate(); // Перерисовываем View при добавлении нового элемента
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        unitH = canvas.getHeight() / delHeight;
        unitW = canvas.getWidth() / delWidth;

        initTypes();
        canvas.save();

        boolean[] bitMassivInVars = new boolean[256];
        int[] byteMassivInVars = new int[256];

        if (elements != null){
            for (GraphElement element : elements) {
                element.drawGrafPar(canvas, bitMassivInVars, byteMassivInVars);
            }
        }

        paint.setAntiAlias(true);

        canvas.restore();
    }

    private void initTypes() {
        Type3 type3 = new Type3(3, new String[]{"Фон_Т3"}, 800, 600,
                100*unitW, 65*unitH, 6, globalColors);
        addElement(type3);

        Type1 type1 = new Type1(1, new String[]{"ЭЛЕМ_T1"}, 100, 30,
                130*unitW, 120*unitH, 0, 4, globalColors);
        addElement(type1);

        Type1 type1_2 = new Type1(1, new String[]{"ЭЛЕМ_T1"}, 100, 30,
                160*unitW, 120*unitH, 0, 2, globalColors);
        addElement(type1_2);

        Type2 type2 = new Type2(2, new String[]{"ЭЛЕМ_Т2"}, 100, 30,
                130*unitW, 150*unitH, 1, globalColors, 1);
        addElement(type2);

//        // Создание и добавление элемента Type4 (текстовый элемент)
//        Type4 type4 = new Type4(4, new String[]{"Текст_Т4"}, 18,
//                300, 150, 3, globalColors);
//        addElement(type4);

        // Создание и добавление элемента Type5 (интерактивный прямоугольник)
        Type5 type5 = new Type5(5, new String[]{"ELEM_5"}, 100, 30,
                160*unitW, 150*unitH, 5, 0, globalColors, 1);
        addElement(type5);

//        // Создание и добавление элемента Type6 (интерактивный треугольник)
        Type6 type6 = new Type6(6, new String[]{"Треугольник_Т6"}, 160, 80,
                130*unitW, 200*unitH, 1, 3, globalColors, 1);
        addElement(type6);

        Type6 type7 = new Type6(6, new String[]{"Треугольник_Т6"}, 160, 80,
                130*unitW, 200*unitH, 0, 5, globalColors, 3
        );
        addElement(type7);

        Type6 type8 = new Type6(6, new String[]{"Треугольник_Т6"}, 160, 80,
                160*unitW, 200*unitH, 4, 5, globalColors, 2
        );
        addElement(type8);

        Type6 type9 = new Type6(6, new String[]{"Треугольник_Т6"}, 160, 80,
                160*unitW, 200*unitH, 2, 5, globalColors, 4
        );
        addElement(type9);
    }


    public void clearZone(){
        counter = 5;
        invalidate();
    }

    public void halfZone(){
        counter = 0;
        invalidate();
    }

    public void fullZone(){
        counter = 1;
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return super.onTouchEvent(event);
    }
}
