package com.angcyo.uikitdemo.ui.widget.group;

import android.animation.ValueAnimator;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.core.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/03/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FozuLayout2 extends FrameLayout {

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

    /**
     * 只用来控制顶层view的移动距离
     */
    int lastMoveY = 0;
    /**
     * 只用来控制底层view的移动距离
     */
    int firstMoveY = 0;

    public FozuLayout2(@NonNull Context context) {
        this(context, null);
    }

    public FozuLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                moveBy(distanceY);
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
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

            int childLeft = (getMeasuredWidth() - child.getMeasuredWidth()) / 2;
            int childTop = getPaddingTop() + layoutParams.topMargin + i * itemSpace;
            child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
        }
    }

    private void moveBy(float distanceY) {
        //顶上是手指方向移动
        lastMoveY += -distanceY;
        //底下是反方向移动
        firstMoveY += distanceY / 4;

        //边界限制
        int maxLastMoveY = getMeasuredHeight() - 3 * itemSpace;
        int minLastMoveY = -itemSpace;

        int maxFirstMoveY = itemSpace;
        int minFirstMoveY = -100;

        lastMoveY = MathUtils.clamp(lastMoveY, minLastMoveY, maxLastMoveY);
        firstMoveY = MathUtils.clamp(firstMoveY, minFirstMoveY, maxFirstMoveY);

        refreshLayout();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return gestureDetectorCompat.onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            resetLayout();
        }
        return true;
    }

    private void refreshLayout() {
        int childCount = getChildCount();
        View lastChild = null;
        View firstChild = null;
        if (childCount > 0) {
            lastChild = getChildAt(childCount - 1);
        }
        if (childCount > 1) {
            firstChild = getChildAt(0);
        }

        if (lastChild != null) {
            lastChild.setTranslationY(lastMoveY);
        }
        if (firstChild != null) {
            firstChild.setTranslationY(firstMoveY);
        }
    }

    private void resetLayout() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                lastMoveY = (int) ((1 - value) * lastMoveY);
                firstMoveY = (int) ((1 - value) * firstMoveY);
                refreshLayout();
            }
        });
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() > 2) {
            //不支持多个child
            throw new IllegalArgumentException("不支持多个child");
        }
    }
}
