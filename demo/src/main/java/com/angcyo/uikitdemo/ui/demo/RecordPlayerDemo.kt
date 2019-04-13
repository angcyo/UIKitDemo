package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.view.MotionEvent
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.RecordUI
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.media.RRecord
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.utils.Root
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/13
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class RecordPlayerDemo : BaseItemFragment() {
    val pathList = mutableListOf<String>()

    val recordUI = RecordUI()
    lateinit var record: RRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        record = RRecord(mAttachContext, Root.getAppExternalFolder("Record"))
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        pathList.onEach { path ->
            singleItems.add(object : SingleItem() {
                override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                    holder.tv(R.id.path_view).text = path
                    holder.click(R.id.play_view) {
                        record.stopPlayback()
                        record.startPlayback(path, 0f)
                    }
                }

                override fun getItemLayoutId(): Int {
                    return R.layout.item_player_layout
                }
            })
        }

        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.view(R.id.record_view).setOnTouchListener { v, event ->
                    when {
                        event.actionMasked == MotionEvent.ACTION_DOWN -> {
                            recordUI.show(requireActivity(), v)
                            record.stopPlayback()
                            record.startRecord(Root.createFileName())
                        }
                        event.actionMasked == MotionEvent.ACTION_UP ||
                                event.actionMasked == MotionEvent.ACTION_CANCEL -> {
                            recordUI.hide()
                            record.stopRecord()
                            if (recordUI.isCancel) {

                            } else {
                                pathList.add(record.sampleFile.absolutePath.apply {
                                    L.w("文件路径:$this")
                                })
                                baseViewHolder.post {
                                    refreshLayout()
                                }
                            }
                        }

                        event.actionMasked == MotionEvent.ACTION_MOVE -> {
                            recordUI.checkCancel(event)
                        }
                    }
                    //L.i("Touch:${event.actionMasked} x:${event.x}  y:${event.y} rawY:${event.rawY}")
                    true
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_record_layout
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        record.release()
    }

}