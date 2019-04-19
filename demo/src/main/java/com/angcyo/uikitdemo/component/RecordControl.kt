package com.angcyo.uikitdemo.component

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import com.angcyo.uiview.less.media.RRecord
import com.angcyo.uiview.less.utils.Root
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/18
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RecordControl {

    val recordUI = RecordUI()
    var record: RRecord? = null

    /**
     * 监听那个view的事件, 触发录制
     * */
    fun wrap(
        view: View, activity: Activity,
        onRecordStart: () -> Unit = {}, onRecordEnd: (voiceFile: File) -> Unit = {}
    ) {
        if (record == null) {
            record = RRecord(activity, Root.getAppExternalFolder("Record"))
        }

        fun onEnd(isCancel: Boolean) {
            view.isSelected = false
            recordUI.hide()
            record?.stopRecord()

            if (!isCancel) {
                record?.let {
                    onRecordEnd.invoke(it.sampleFile)
                }
            }
        }

        view.setOnTouchListener { v, event ->
            when {
                event.actionMasked == MotionEvent.ACTION_DOWN -> {
                    view.isSelected = true
                    recordUI.show(activity, v)
                    record?.stopPlayback()
                    record?.startRecord(Root.createFileName())
                    onRecordStart.invoke()
                }
                event.actionMasked == MotionEvent.ACTION_UP ||
                        event.actionMasked == MotionEvent.ACTION_CANCEL -> {
                    onEnd(recordUI.isCancel)
                }

                event.actionMasked == MotionEvent.ACTION_MOVE -> {
                    recordUI.checkCancel(event)
                }
            }
            //L.i("Touch:${event.actionMasked} x:${event.x}  y:${event.y} rawY:${event.rawY}")
            true
        }

        recordUI.onMaxRecordTime = Runnable {
            onEnd(false)
        }
    }

    fun release() {
        recordUI.hide()
        record?.release()
    }
}