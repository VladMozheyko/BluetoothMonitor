package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Type6 extends GraphElement {
    private int rotationAngle;

    // Конструктор класса
    public Type6(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorIndex1, int colorIndex2, int[] globalColorArray, int rotationCode) {
        super(numberVar, nameVar, length, width, coordX, coordY, colorIndex1, colorIndex2, globalColorArray);
        this.rotationAngle = convertToRotationAngle(rotationCode);
    }

    private int convertToRotationAngle(int angleCode) {
        switch (angleCode) {
            case 1: return 0;
            case 2: return 90;
            case 3: return 180;
            case 4: return 270;
            default: return 0; // любое другое значение даст позицию 0
        }
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();
        paint.setColor(bitMassivInVars[numberVar] ? getColorByIndex(colorIndex2) : getColorByIndex(colorIndex1));

        // Создаем массив точек треугольника
        float[] points = {
                coordX, coordY,
                coordX + length, coordY,
                coordX + length / 2f, coordY - width
        };
        // находим центр треугольника
        float centerX = coordX + length / 2f;
        float centerY = coordY + width / 2f;

        // Сохраняем текущие параметры канваса
        canvas.save();

        canvas.rotate(rotationAngle, centerX, centerY); // повернули канву

        canvas.drawPath(createPath(points), paint);

        canvas.restore();
    }

    private Path createPath(float[] points) {
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        path.lineTo(points[2], points[3]);
        path.lineTo(points[4], points[5]);
        path.close();
        return path;
    }
}
