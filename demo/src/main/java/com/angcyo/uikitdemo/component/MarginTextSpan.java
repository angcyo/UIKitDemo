package com.angcyo.uikitdemo.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

public class MarginTextSpan extends ReplacementSpan {
    protected float offsetY = 0;

    protected float offsetX = 0;

    public MarginTextSpan() {
    }

    public MarginTextSpan(float offsetY) {
        this.offsetY = offsetY;
    }

    public MarginTextSpan(float offsetY, float offsetX) {
        this.offsetY = offsetY;
        this.offsetX = offsetX;
    }

    @Override
    public int getSize(@NonNull Paint paint,
                       CharSequence text, int start, int end,
                       @Nullable Paint.FontMetricsInt fm) {
        return (int) paint.measureText(String.valueOf(text.subSequence(start, end)));
    }

    @Override
    public void draw(@NonNull Canvas canvas,
                     CharSequence text, int start, int end,
                     float x, int top, int y, int bottom,
                     @NonNull Paint paint) {
        x += offsetX;
        y += offsetY;
        canvas.drawText(text, start, end, x, y, paint);
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }
}