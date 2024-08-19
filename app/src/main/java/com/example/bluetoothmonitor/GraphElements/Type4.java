package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Paint;

//тип 4 StringVar_t4 - Текстовая переменная, выводится в любом месте экрана поверх других элементов

//1. StringVar_t4(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorVar)
//2. Имя элемента - nameVar;
//3. Тип шрифта - length:
//        3. Размер шрифта - width;  // размеры
//4. Координата Х в пикселях - coordX;
//5. Координата У в пикселях - coordY;
//6. Номер цвета шрифта;

public class Type4 extends GraphElement {
    public Type4(int numberVar, String[] nameVar, int fontSize, int coordX, int coordY, int colorIndex1, int[] globalColorArray) {
        super(numberVar, nameVar, fontSize, 0, coordX, coordY, colorIndex1, -1, globalColorArray);
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();
        paint.setColor(getColorByIndex(colorIndex1));
        paint.setTextSize(length); // Используем length как размер шрифта
        canvas.drawText(String.join("", nameVar), coordX, coordY, paint);
    }
}
