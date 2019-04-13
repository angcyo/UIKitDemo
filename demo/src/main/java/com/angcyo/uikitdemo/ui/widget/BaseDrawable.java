package com.angcyo.uikitdemo.ui.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.angcyo.lib.L;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/04/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class BaseDrawable extends Drawable {

    protected Paint paint;

    public BaseDrawable() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        L.i("...");
    }

    @Override
    public void setAlpha(int alpha) {

        L.i("...");
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

        L.i("...");
    }

    @Override
    public int getOpacity() {
        L.i("...");
        return 0;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        L.i("...");
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
        L.i("...");
    }

    @NonNull
    @Override
    public Rect getDirtyBounds() {
        L.i("...");
        return super.getDirtyBounds();
    }

    @Override
    public void setChangingConfigurations(int configs) {
        super.setChangingConfigurations(configs);
        L.i("...");
    }

    @Override
    public int getChangingConfigurations() {
        L.i("...");
        return super.getChangingConfigurations();
    }

    @Override
    public void setDither(boolean dither) {
        super.setDither(dither);
        L.i("...");
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        super.setFilterBitmap(filter);
        L.i("...");
    }

    @Override
    public boolean isFilterBitmap() {
        L.i("...");
        return super.isFilterBitmap();
    }

    @Nullable
    @Override
    public Callback getCallback() {
        L.i("...");
        return super.getCallback();
    }

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
        L.i("...");
    }

    @Override
    public void scheduleSelf(@NonNull Runnable what, long when) {
        super.scheduleSelf(what, when);
        L.i("...");
    }

    @Override
    public void unscheduleSelf(@NonNull Runnable what) {
        super.unscheduleSelf(what);
        L.i("...");
    }

    @Override
    public int getLayoutDirection() {
        L.i("...");
        return super.getLayoutDirection();
    }

    @Override
    public boolean onLayoutDirectionChanged(int layoutDirection) {
        L.i("...");
        return super.onLayoutDirectionChanged(layoutDirection);
    }

    @Override
    public int getAlpha() {
        L.i("...");
        return super.getAlpha();
    }

    @Override
    public void setColorFilter(int color, @NonNull PorterDuff.Mode mode) {
        super.setColorFilter(color, mode);
        L.i("...");
    }

    @Override
    public void setTint(int tintColor) {
        super.setTint(tintColor);
        L.i("...");
    }

    @Override
    public void setTintList(@Nullable ColorStateList tint) {
        super.setTintList(tint);
        L.i("...");
    }

    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        super.setTintMode(tintMode);
        L.i("...");
    }

    @Nullable
    @Override
    public ColorFilter getColorFilter() {
        L.i("...");
        return super.getColorFilter();
    }

    @Override
    public void clearColorFilter() {
        super.clearColorFilter();
        L.i("...");
    }

    @Override
    public void setHotspot(float x, float y) {
        super.setHotspot(x, y);
        L.i("...");
    }

    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        super.setHotspotBounds(left, top, right, bottom);
        L.i("...");
    }

    @Override
    public void getHotspotBounds(@NonNull Rect outRect) {
        super.getHotspotBounds(outRect);
        L.i("...");
    }

    @Override
    public boolean isStateful() {
        L.i("...");
        return super.isStateful();
    }

    @Override
    public boolean setState(@NonNull int[] stateSet) {
        L.i("...");
        return super.setState(stateSet);
    }

    @NonNull
    @Override
    public int[] getState() {
        L.i("...");
        return super.getState();
    }

    @Override
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        L.i("...");
    }

    @NonNull
    @Override
    public Drawable getCurrent() {
        L.i("...");
        return super.getCurrent();
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        L.i("...");
        return super.setVisible(visible, restart);
    }

    @Override
    public void setAutoMirrored(boolean mirrored) {
        super.setAutoMirrored(mirrored);
        L.i("...");
    }

    @Override
    public boolean isAutoMirrored() {
        L.i("...");
        return super.isAutoMirrored();
    }

    @Override
    public void applyTheme(@NonNull Resources.Theme t) {
        super.applyTheme(t);
        L.i("...");
    }

    @Override
    public boolean canApplyTheme() {
        L.i("...");
        return super.canApplyTheme();
    }

    @Nullable
    @Override
    public Region getTransparentRegion() {
        L.i("...");
        return super.getTransparentRegion();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        L.i("...");
        return super.onStateChange(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        L.i("...");
        return super.onLevelChange(level);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        L.i("...");
    }

    @Override
    public int getIntrinsicWidth() {
        L.i("...");
        return super.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        L.i("...");
        return super.getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        L.i("...");
        return super.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        L.i("...");
        return super.getMinimumHeight();
    }

    @Override
    public boolean getPadding(@NonNull Rect padding) {
        L.i("...");
        return super.getPadding(padding);
    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        super.getOutline(outline);
        L.i("...");
    }

    @NonNull
    @Override
    public Drawable mutate() {
        L.i("...");
        return super.mutate();
    }

    @Override
    public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs) throws IOException, XmlPullParserException {
        super.inflate(r, parser, attrs);
        L.i("...");
    }

    @Override
    public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs, @Nullable Resources.Theme theme) throws IOException, XmlPullParserException {
        super.inflate(r, parser, attrs, theme);
        L.i("...");
    }

    @Nullable
    @Override
    public ConstantState getConstantState() {
        L.i("...");
        return super.getConstantState();
    }
}
