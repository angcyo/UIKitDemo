package com.angcyo.uikitdemo.ui.recycler;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.angcyo.uiview.less.recycler.manager.RPagerSnapHelper;

public class LoopSnapHelper extends RPagerSnapHelper {

    @Override
    public boolean onFling(int velocityX, int velocityY) {
        super.onFling(velocityX, velocityY);
        //拦截fling操作
        return true;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return super.findSnapView(layoutManager);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager lm, int velocityX, int velocityY) {
        //return super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        //重写fling操作
        if (lm instanceof LoopLayoutManager) {
        } else {
            return RecyclerView.NO_POSITION;
        }
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null) {
            return RecyclerView.NO_POSITION;
        }

        final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();

        LoopLayoutManager layoutManager = (LoopLayoutManager) lm;
        int orientation = layoutManager.getOrientation();

        final boolean forwardDirection;
        if (layoutManager.canScrollHorizontally()) {
            forwardDirection = velocityX > 0;
        } else {
            forwardDirection = velocityY > 0;
        }
        final int offsetPosition;
        if (forwardDirection) {
            offsetPosition = 1;
        } else {
            offsetPosition = -1;
        }

        final int currentPosition = layoutManager.getCurrentPosition();//mCurrentPosition
        if ((orientation == ViewPagerLayoutManager.VERTICAL
                && Math.abs(velocityY) > minFlingVelocity) || (orientation == ViewPagerLayoutManager.HORIZONTAL
                && Math.abs(velocityX) > minFlingVelocity)) {

            int position = layoutManager.getReverseLayout() ?
                    currentPosition - offsetPosition : currentPosition + offsetPosition;
            mRecyclerView.smoothScrollToPosition(position);
        }

        //不需要默认的fling操作
        return RecyclerView.NO_POSITION;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) targetView.getLayoutParams();
        int position = params.getViewAdapterPosition();
        int left = targetView.getLeft();
        int right = targetView.getRight();
        int top = targetView.getTop();
        int bottom = targetView.getBottom();
        ViewGroup viewGroup = (ViewGroup) targetView.getParent();

        int[] out = new int[]{0, 0};
        boolean isLastItem;
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            isLastItem = position == layoutManager.getItemCount() - 1/*最后一个*/ && right == viewGroup.getMeasuredWidth();
            out[0] = left;
            out[1] = 0;
        } else {
            isLastItem = position == layoutManager.getItemCount() - 1/*最后一个*/ && bottom == viewGroup.getMeasuredHeight();
            out[0] = 0;
            out[1] = top;
        }

        if (mOnPageListener != null && mCurrentPosition != position) {
            int currentPosition = mCurrentPosition;
            boolean listener = false;
            if (mOrientation == LinearLayoutManager.HORIZONTAL && (out[0] == 0 || isLastItem)) {
                listener = true;
            } else if (mOrientation == LinearLayoutManager.VERTICAL && (out[1] == 0 || isLastItem)) {
                listener = true;
            }

            if (listener) {
                mCurrentPosition = position;
                mOnPageListener.onPageSelector(mCurrentPosition);
                mOnPageListener.onPageSelector(currentPosition, mCurrentPosition);
            }
        }
        return out;
    }
}