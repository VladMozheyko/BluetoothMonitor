package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//тип 3 GrafElement_t3 - Прямоугольник - является окном, объединяющим нескольких граф. элементов в одну группу,
//его цвет является фоном;
//
//1. GrafElement_t3(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorVar)
//2. Имя элемента - nameVar, размещается в верхней части графического поля;
//3. Длина в пикселях - length;  // размеры
//4. Ширина в пикселях -width;  // размеры
//5. Координата Х в пикселях - coordX;
//6. Координата У в пикселях - coordY;
//7. Номер цвета - colorVar;

public class Type3 extends GraphElement {
    public Type3(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorIndex1, int[] globalColorArray) {
        super(numberVar, nameVar, length, width, coordX, coordY, colorIndex1, -1, globalColorArray);
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();
        paint.setColor(getColorByIndex(colorIndex1));
        canvas.drawRect(coordX, coordY, coordX + length, coordY + width, paint);

        if (nameVar != null) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            canvas.drawText(String.join("", nameVar), coordX, coordY + 20, paint);
        }
    }
}
