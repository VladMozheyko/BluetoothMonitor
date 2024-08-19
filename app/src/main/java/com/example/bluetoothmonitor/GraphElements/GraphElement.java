package com.example.bluetoothmonitor.GraphElements;

import android.graphics.Canvas;

public abstract class GraphElement {

    int numberVar;
    String[] nameVar;
    int length;
    int width;
    int coordX;
    int coordY;
    int colorIndex1;
    int colorIndex2;
    int[] globalColorArray;

    public GraphElement(int numberVar, String[] nameVar, int length, int width, int coordX,
                        int coordY, int colorIndex1, int colorIndex2, int[] globalColorArray) {
        this.numberVar = numberVar;
        this.nameVar = nameVar;
        this.length = length;
        this.width = width;
        this.coordX = coordX;
        this.coordY = coordY;
        this.colorIndex1 = colorIndex1;
        this.colorIndex2 = colorIndex2;
        this.globalColorArray = globalColorArray;
    }

    // получение цвета по индексу
    public int getColorByIndex(int index) {
        if (index >= 0 && index < globalColorArray.length) {
            return globalColorArray[index];
        }
        return 0; // черный по умолчанию
    }

    public abstract void drawGrafPar(Canvas canvas, boolean[] bitMassivInVars, int[] byteMassivInVars);
}
