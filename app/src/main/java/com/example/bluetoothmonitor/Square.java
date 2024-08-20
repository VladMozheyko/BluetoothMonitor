package com.example.bluetoothmonitor;

import android.graphics.Canvas;
import android.text.Html;
import android.text.Layout;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;

public class Square {
    private float right;
    private float left;
    private float top;
    private float bottom;

    private float textX;
    private float textY;
    private String text;

    public Square(float left, float top, float size) { // просто потому что сайз - это соглашение
        this.right = left + size;
        this.left = left;
        this.top = top;
        this.bottom = top + size;
        this.textX = left + 10; // Начальные координаты текста
        this.textY = top + 50; // Начальные координаты текста
        this.text = ""; // Начальный текст
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextPosition(float textX, float textY) {
        this.textX = textX;
        this.textY = textY;
    }

    public void drawRect(Canvas canvas, TextPaint paint) {
        canvas.drawRect(left, top, right, bottom, paint); // Исправлено на правильные параметры
    }

    public void drawText(Canvas canvas, TextPaint paint) {
        paint.setTextSize(50);
        int width = (int) (right - left) - 20; // Уменьшаем ширину текста для отступов

        Spanned spannedText = Html.fromHtml(text);

        StaticLayout staticLayout = new StaticLayout(spannedText, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, false);

        canvas.save();
        canvas.translate(textX, textY);
        staticLayout.draw(canvas);
        canvas.restore();
    }
}
