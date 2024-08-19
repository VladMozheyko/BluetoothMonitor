package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Type2 extends GraphElement {

    int typeByteVar;

    public Type2(int numberVar, String[] nameVar, int length, int width, int coordX, int coordY,
                 int colorIndex1, int[] globalColorArray, int typeByteVar) {
        super(numberVar, nameVar, length, width, coordX, coordY, colorIndex1, -1, globalColorArray);
        this.typeByteVar = typeByteVar;
    }

    @Override
    public void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars) {
        Paint paint = new Paint();

        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawLine(coordX + length, coordY, coordX + length, coordY + width, paint);
        canvas.drawLine(coordX, coordY + width, coordX + length, coordY + width, paint);

        paint.setColor(Color.DKGRAY);
        canvas.drawLine(coordX, coordY, coordX + length, coordY, paint);
        canvas.drawLine(coordX, coordY, coordX, coordY + width, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getColorByIndex(colorIndex1));
        canvas.drawRect(coordX, coordY, coordX + length, coordY + width, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        if (nameVar != null) {
            canvas.drawText(String.join("", nameVar), coordX, coordY - 10, paint);
        }

        String value = "";
        switch (typeByteVar) {
            case 1:
                value = String.valueOf(byteMassivInVars[numberVar] & 0xFF);
                break;
            case 2:
                value = String.valueOf((byteMassivInVars[numberVar] & 0xFF) - 40);
                break;
            case 3:
                value = String.format("%.1fВ", byteMassivInVars[numberVar] / 10.0);
                break;
            case 4:
                value = String.valueOf(byteMassivInVars[numberVar]);
                break;
        }

        paint.setTextSize(20);
        canvas.drawText(value, coordX + length / 2, coordY + width / 2, paint);
    }
}
