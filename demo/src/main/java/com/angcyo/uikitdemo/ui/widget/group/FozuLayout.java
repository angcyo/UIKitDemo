package com.angcyo.uikitdemo.ui.widget.group;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.angcyo.lib.L;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/03/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FozuLayout extends FrameLayout {

    /**
     * 间隔大小
     */
    int itemSpace = 200;
    /**
     * 需要缩放多少大小
     */
    int itemWidthScaleSize = 300;
    int itemHeightScaleSize = 0;

    GestureDetectorCompat gestureDetectorCompat;

    public FozuLayout(@NonNull Context context) {
        this(context, null);
    }

    public FozuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy(0, (int) distanceY);
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //不支持wrap_content

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int count = Math.max((childCount - 1 - i), 0);
            int widthUsedSize = count * itemWidthScaleSize;
            int heightUsedSize = count * itemHeightScaleSize;

            measureChildWithMargins(child, widthMeasureSpec, widthUsedSize, heightMeasureSpec, heightUsedSize);
        }

        setMeasuredDimension(resolveSize(0, widthMeasureSpec), resolveSize(0, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //super.onLayout(changed, left, top, right, bottom);

        //不支持 layout_gravity
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            FrameLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

            int childLeft = (getMeasuredWidth() - child.getMeasuredWidth()) / 2;
            int childTop = getPaddingTop() + layoutParams.topMargin + i * itemSpace;
            child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, Math.max(Math.min(y, 0), -getMeasuredHeight() + itemSpace));
        progress = -getScrollY() * 1f / getMeasuredHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        L.i("l:" + l + " t:" + t + " oldl:" + oldl + " oldt:" + oldt);
        scaleLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            resetLayout();
        }
        return true;
    }

    private void scaleLayout() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int count = i + 1;

            if (i != childCount - 1) {
                //非最上层
                child.setScaleX(1 + progress / (childCount - count));
                child.setScaleY(1 + progress / (childCount - count));
            }
        }
    }

    private void resetLayout() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getScrollY(), 0);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollTo(0, (Integer) animation.getAnimatedValue());
            }
        });
    }

    float progress = 0f;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        AnimUtil.valueAnimator(6000, new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                progress = (float) animation.getAnimatedValue();
//
//                scrollTo(0, -(int) (progress * 1000));
//            }
//        }).start();
    }
}
