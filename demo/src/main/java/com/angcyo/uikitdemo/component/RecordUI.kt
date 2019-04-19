package com.angcyo.uikitdemo.component

import android.app.Activity
import android.graphics.Rect
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import com.angcyo.uikitdemo.R

/**
 *  模仿微信录音对话框的UI
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RecordUI {

    companion object {
        /**
         * 从url中, 获取录制的音频时长
         */
        fun getRecordTime(url: String): Int {
            if (TextUtils.isEmpty(url)) {
                return -1
            }
            var result = -1
            try {
                val end = url.split("_t_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val index = end.indexOf(".")
                if (index != -1) {
                    result = Integer.parseInt(end.substring(0, index))
                } else {
                    result = Integer.parseInt(end)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }
    }

    var parent: ViewGroup? = null
    var recordLayout: ViewGroup? = null
    var touchView: View? = null

    var recordStartTime = 0L

    /**
     * 需要限制最大录制的时长 秒
     * */
    var maxRecordTime = -1L

    /**
     * 当前录制的时间, 毫秒
     * */
    var currentRecordTime = 0L
        set(value) {
            field = value
            showRecordTime(value)
        }

    val checkTimeRunnable: Runnable by lazy {
        Runnable {
            val time = System.currentTimeMillis()
            val millis = time - recordStartTime

            currentRecordTime = millis

            if (maxRecordTime > 0 && millis >= maxRecordTime * 1000) {
                //到达最大值
                onMaxRecordTime?.run()
            } else {
                touchView?.postDelayed(checkTimeRunnable, 300)
            }
        }
    }

    /**
     * 达到最大时间的回调
     * */
    var onMaxRecordTime: Runnable? = null

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

            recordStartTime = System.currentTimeMillis()
            touchView?.post(checkTimeRunnable)
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

    /**
     * 视图是否在屏幕偏下的位置
     * */
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
        recordLayout?.let {
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

    /**
     *
     * @param millis 多少毫秒
     * */
    fun showRecordTime(millis: Long) {
        recordLayout?.let {
            val timeView: TextView = it.findViewById(R.id.record_time_view)

            if (maxRecordTime > 0) {
                timeView.text = "${formatTime(millis)}/${formatTime(maxRecordTime * 1000)}"
            } else {
                timeView.text = formatTime(millis)
            }
        }
    }

    /**
     * 00:00的格式输出, 如果有小时: 01:00:00
     */
    fun formatTime(millisecond: Long /*毫秒*/): String {
        val mill = millisecond / 1000

        val min = mill / 60
        val hour = min / 60

        val h = hour % 24
        val m = min % 60
        val s = mill % 60

        val builder = StringBuilder()
        if (hour > 0) {
            builder.append(if (h >= 10) h else "0$h")
            builder.append(":")
        }
        builder.append(if (m >= 10) m else "0$m")
        builder.append(":")
        builder.append(if (s >= 10) s else "0$s")

        return builder.toString()
    }
}