package com.angcyo.uikitdemo.component

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import com.angcyo.uiview.less.kotlin.extName
import com.angcyo.uiview.less.kotlin.noExtName
import com.angcyo.uiview.less.kotlin.toast_tip
import com.angcyo.uiview.less.media.RRecord
import com.angcyo.uiview.less.utils.RCacheManager
import com.angcyo.uiview.less.utils.Root
import java.io.File
import kotlin.math.max

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
        onRecordStart: () -> Boolean = { true } /*返回true, 表示可以开始录制*/,
        onRecordEnd: (voiceFile: File) -> Unit = {}
    ) {
        if (record == null) {
            val folder = Root.getAppExternalFolder("Record")
            record = RRecord(activity, folder)
            RCacheManager.instance().addCachePath(folder)
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
                    if (onRecordStart.invoke()) {
                        view.isSelected = true
                        recordUI.onGetMeterCount = {
                            var result = 1

                            record?.let {
                                result = 7 * it.maxAmplitude / 32768
                            }

                            max(1, result)
                        }
                        recordUI.show(activity, v, event.rawY)
                        record?.stopPlayback()
                        record?.startRecord(Root.createFileName())
                    } else {
                        view.isSelected = false
                    }
                }
                event.actionMasked == MotionEvent.ACTION_UP ||
                        event.actionMasked == MotionEvent.ACTION_CANCEL -> {
                    if (view.isSelected) {
                        if (recordUI.isMinRecordTime) {
                            toast_tip("至少需要录制 " + recordUI.minRecordTime + " 秒")
                            onEnd(true)
                        } else {
                            onEnd(recordUI.isCancel)
                        }
                    }
                }

                event.actionMasked == MotionEvent.ACTION_MOVE -> {
                    if (view.isSelected) {
                        recordUI.checkCancel(event)
                    }
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

    /**用录制时间重命名文件*/
    fun rename(sampleFile: File): File {
        val recordTime = recordUI.currentRecordTime
        val time = (recordTime / 1000).toInt()
        val recordFile = File(
            sampleFile.parent,
            "${sampleFile.name.noExtName()}_t_${time}.${sampleFile.name.extName()}"
        )
        sampleFile.renameTo(recordFile)
        return recordFile
    }
}