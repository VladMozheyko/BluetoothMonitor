package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

//тип 6 GrafElement_t6 - Равнобедренный треугольник - интерактивный, реагирует на касания - кнопка;
//
//1. GrafTouch_t5(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorVar, int colorActiv, int positTriang)
//2. Имя элемента nameVar, не выводится, может быть пустым;
//3. Катет в пикселях - length;  // размеры
//4. Гипотенуза в пикселях -width;  // размеры
//5. Координата Х в пикселях - coordX;
//6. Координата У в пикселях - coordY;
//7. Номер цвета пассивного состояния - colorVar;
//8. Номер цвета активного состояния - colorActiv;
//9. Тип размещения - positTriang:  1 - "+", 2 -"-", в массиве bitMassivOutVars[] по номеру numberVar в
//соответствующем бите должен устанавливать значение, соответствующее нажатию(касанию);

public class Type6 extends GraphElement {
    public int positTriang;

    public Type6(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY, int colorIndex1, int colorIndex2, int[] globalColorArray, int positTriang) {
        super(numberVar, nameVar, length, width, coordX, coordY, colorIndex1, colorIndex2, globalColorArray);
        this.positTriang = positTriang;
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();
        paint.setColor(bitMassivInVars[numberVar] ? getColorByIndex(colorIndex2) : getColorByIndex(colorIndex1));

        float rotationAngle = positTriang * 45;

        // Сохраняем текущие параметры канваса
        canvas.save();

        // Поворачиваем канвас вокруг центра треугольника
        canvas.rotate(rotationAngle, coordX + length / 2, coordY + width / 2);

        // Создаем массив точек треугольника
        float[] points = {
                coordX, coordY,
                coordX + length, coordY,
                coordX + length / 2, positTriang % 2 == 0 ? coordY - width : coordY + width
        };

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

