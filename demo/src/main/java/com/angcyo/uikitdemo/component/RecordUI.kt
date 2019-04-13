package com.angcyo.uikitdemo.component

import android.app.Activity
import android.graphics.Rect
import android.view.*
import android.widget.TextView
import com.angcyo.uikitdemo.R

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RecordUI {
    var parent: ViewGroup? = null
    var recordLayout: ViewGroup? = null
    var touchView: View? = null

    /**
     * @param activity 用来附着显示界面的Activity
     * @param touchView 用来请求拦截Touch事件
     * */
    fun show(activity: Activity, touchView: View? = null) {
        touchView?.parent?.requestDisallowInterceptTouchEvent(true)

        if (recordLayout != null) {

        } else {
            this.touchView = touchView

            parent = activity.window.findViewById(Window.ID_ANDROID_CONTENT)

            recordLayout = LayoutInflater.from(activity)
                .inflate(R.layout.layout_record_ui, parent, false) as? ViewGroup

            parent?.addView(recordLayout)
        }

        showCancel(false)
    }

    fun hide() {
        touchView?.parent?.requestDisallowInterceptTouchEvent(false)
        parent?.removeView(recordLayout)
        recordLayout = null
        parent = null
        touchView = null
    }

    private val tempRect: Rect by lazy {
        Rect()
    }

    fun isViewPreferBottom(): Boolean {
        var result = false
        touchView?.let { view ->
            val height = view.resources.displayMetrics.heightPixels
            view.getGlobalVisibleRect(tempRect)

            result = tempRect.bottom > height / 2
        }

        return result
    }

    /**
     * 自动检查 是否需要显示取消的提示
     * */
    fun checkCancel(event: MotionEvent) {
        recordLayout?.let { layout ->
            touchView?.let { view ->
                val height = view.resources.displayMetrics.heightPixels
                view.getGlobalVisibleRect(tempRect)

                var cancel = false
                if (isViewPreferBottom()) {
                    //当前View 在屏幕中下位置

                    cancel = tempRect.centerY() - event.rawY > height / 3
                } else {
                    cancel = event.rawY - tempRect.centerY() > height / 3
                }

                showCancel(cancel)
            }
        }
    }


    /**
     * 是否触发了取消
     * */
    var isCancel = false

    /**
     * 触发 取消录音提示
     * */
    fun showCancel(show: Boolean) {
        isCancel = show

        recordLayout?.let {
            val tipView: TextView = it.findViewById(R.id.record_cancel_tip_view)
            val tipImageView: View = it.findViewById(R.id.record_tip_layout)
            val tipImageCancelView: View = it.findViewById(R.id.record_cancel_tip_image_view)
            if (show) {
                tipView.text = "释放手指, 取消录制"
                tipView.setBackgroundResource(R.drawable.shape_cancel_record_tip)
                tipImageView.visibility = View.GONE
                tipImageCancelView.visibility = View.VISIBLE
            } else {
                if (isViewPreferBottom()) {
                    tipView.text = "上滑取消录制"
                } else {
                    tipView.text = "下滑取消录制"
                }
                tipView.background = null
                tipImageView.visibility = View.VISIBLE
                tipImageCancelView.visibility = View.GONE
            }
        }
    }
}