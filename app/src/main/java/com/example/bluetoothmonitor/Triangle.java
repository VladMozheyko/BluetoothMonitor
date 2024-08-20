package com.example.bluetoothmonitor;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Triangle {
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;

    public Triangle(float x, float y, float size) {
        this.x1 = x;
        this.y1 = y;
        this.x2 = x + size;
        this.y2 = y;
        this.x3 = x + (size / 2);
        this.y3 = y - size;
    }

    public void drawTriangle(Canvas canvas, Paint paint){
        Path path = new Path();
        path.moveTo(x1, y1); // задаем начальную точку
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.close();
        canvas.drawPath(path, paint);
    }
}
