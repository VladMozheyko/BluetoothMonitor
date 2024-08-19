package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Type1 extends GraphElement {

    public Type1(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY,
                 int colorIndex1, int colorIndex2, int[] globalColorArray) {
        super(numberVar, nameVar, length, width, coordX, coordY, colorIndex1, colorIndex2, globalColorArray);
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();

        //  цвет в зависимости от состояния
        int color = bitMassivInVars[numberVar] ? getColorByIndex(colorIndex1) : getColorByIndex(colorIndex2);
        paint.setColor(color);
        canvas.drawRect(coordX, coordY, coordX + length, coordY + width, paint);

        // Рамка вокруг элемента
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        canvas.drawRect(coordX, coordY, coordX + length, coordY + width, paint);

        // Текст внутри элемента
        if (nameVar != null) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(18);
            canvas.drawText(String.join("", nameVar), coordX + 10, coordY + width / 2 + 5, paint);
        }
    }
}
