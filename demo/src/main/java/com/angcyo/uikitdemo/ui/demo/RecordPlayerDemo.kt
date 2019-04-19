package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.component.PlayControl
import com.angcyo.uikitdemo.component.RecordLayoutControl
import com.angcyo.uikitdemo.component.RecordUI
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.media.RPlayer
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
    lateinit var player: RPlayer
    var playControl = PlayControl()
    var recordLayoutControl = RecordLayoutControl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        record = RRecord(mAttachContext, Root.getAppExternalFolder("Record"))
        player = RPlayer()
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val path = "http://ting666.yymp3.com:86/new27/liyugang6/6.mp3"
                holder.tv(R.id.path_view).text = path
                holder.click(R.id.play_view) {
                    playControl.play(requireActivity(), path)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_player_layout
            }
        })

        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val path = "http://ting666.yymp3.com:86/new27/xuezhiqian8/8.mp3"
                holder.tv(R.id.path_view).text = path
                holder.click(R.id.play_view) {
                    playControl.play(requireActivity(), path)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_player_layout
            }
        })

        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val path = "http://ting666.yymp3.com:86/new27/xuezhiqian8/8.mp3"
                RecordLayoutControl().apply {
                    wrap(holder.group(R.id.record_wrap_layout))
                    singleShowVoice(path, 100 * 1000)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.layout_record_control
            }
        })

        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                val path = "http://ting666.yymp3.com:86/new27/xuezhiqian8/8.mp3"
                RecordLayoutControl().apply {
                    wrap(holder.group(R.id.record_wrap_layout))
                    singleShowText(path)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.layout_record_control
            }
        })

        pathList.onEach { path ->
            singleItems.add(object : SingleItem() {
                override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                    holder.tv(R.id.path_view).text = path
                    holder.click(R.id.play_view) {
                        playControl.play(requireActivity(), path)
                    }
                }

                override fun getItemLayoutId(): Int {
                    return R.layout.item_player_layout
                }
            })
        }

        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                recordLayoutControl.wrap(holder.group(R.id.record_wrap_layout))

//                holder.view(R.id.record_view).setOnTouchListener { v, event ->
//                    when {
//                        event.actionMasked == MotionEvent.ACTION_DOWN -> {
//                            recordUI.show(requireActivity(), v)
//                            record.stopPlayback()
//                            record.startRecord(Root.createFileName())
//                        }
//                        event.actionMasked == MotionEvent.ACTION_UP ||
//                                event.actionMasked == MotionEvent.ACTION_CANCEL -> {
//                            recordUI.hide()
//                            record.stopRecord()
//                            if (recordUI.isCancel) {
//
//                            } else {
//                                pathList.add(record.sampleFile.absolutePath.apply {
//                                    L.w("文件路径:$this")
//                                })
//                                baseViewHolder.post {
//                                    refreshLayout()
//                                }
//                            }
//                        }
//
//                        event.actionMasked == MotionEvent.ACTION_MOVE -> {
//                            recordUI.checkCancel(event)
//                        }
//                    }
//                    //L.i("Touch:${event.actionMasked} x:${event.x}  y:${event.y} rawY:${event.rawY}")
//                    true
//                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_record_layout
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        record.release()
        player.release()
        playControl.release()
        recordLayoutControl?.release()
    }

}