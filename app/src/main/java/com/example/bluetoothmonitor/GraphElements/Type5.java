package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

////тип 5 GrafElement_t5 - Прямоугольник - интерактивный, реагирует на касания;
//
//1. GrafTouch_t5(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorVar, int colorActiv, int typeSwitch)
//2. Имя элемента nameVar, размещается внутри него;
//3. Длина в пикселях - length;  // размеры
//4. Ширина в пикселях -width;  // размеры
//5. Координата Х в пикселях - coordX;
//6. Координата У в пикселях - coordY;
//7. Номер цвета пассивного состояния - colorVar;
//8. Номер цвета активного состояния - colorActiv;
//9. Тип интерактивности - typeSwitch:  1 - кнопка, 2 - переключатель, в массиве bitMassivOutVars[] по номеру numberVar в
//соответствующем бите должен устанавливать значение, соответствующее нажатию(касанию) или активному состоянию для переключ.;

public class Type5 extends GraphElement {
    int typeSwitch;

    public Type5(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorIndex1, int colorIndex2, int[] globalColorArray, int typeSwitch) {
        super(numberVar, nameVar, length, width, coordX, coordY, colorIndex1, colorIndex2, globalColorArray);
        this.typeSwitch = typeSwitch;
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();
        float cornerRadius = 10f;  // Радиус скругления углов

        // Основной прямоугольник
        RectF rect = new RectF(coordX, coordY, coordX + length, coordY + width);

        // Фон прямоугольника с эффектом вдавленности
        paint.setStyle(Paint.Style.FILL);
        int backgroundColor = bitMassivInVars[numberVar] ? getColorByIndex(colorIndex2) : getColorByIndex(colorIndex1);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        // Добавляем темную тень (внешнюю) для эффекта вдавленности
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4); // Увеличиваем толщину для более выраженного эффекта
        paint.setColor(Color.DKGRAY); // Более темный цвет для внешней тени
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        // Добавляем светлую тень (внутреннюю) для эффекта вдавленности
        paint.setStrokeWidth(2);
        paint.setColor(Color.LTGRAY); // Более светлый цвет для внутренней тени
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        // Настройки для текста
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(16);
        paint.setTypeface(android.graphics.Typeface.DEFAULT); // Установите шрифт по умолчанию или другой, если требуется
        paint.setTextAlign(Paint.Align.CENTER); // Выравнивание текста по центру

        // Создаем строку текста
        String text = String.join("", nameVar);

        // Измеряем текст
        float textWidth = paint.measureText(text);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        float textOffset = (textHeight / 2) - fontMetrics.bottom;

        float textX = coordX + (length / 2);
        float textY = coordY + (width / 2) - (fontMetrics.ascent + fontMetrics.descent) / 2;

        // Рисуем текст
        canvas.drawText(text, textX, textY, paint);
    }
}

