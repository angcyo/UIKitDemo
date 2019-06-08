package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.angcyo.http.Rx
import com.angcyo.lib.L
import com.angcyo.okdownload.FDown
import com.angcyo.okdownload.FDownListener
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseRecyclerFragment
import com.angcyo.uiview.less.kotlin.nowTime
import com.angcyo.uiview.less.kotlin.onScroll
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.less.utils.RSpan
import com.angcyo.uiview.less.widget.SimpleProgressBar
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.StatusUtil
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/08
 */
class FileDownDemo : AppBaseRecyclerFragment<DslAdapterItem>() {
    private val urls = mutableListOf(
        "http://package.mac.wpscdn.cn/mac_wps_pkg/1.1.0/WPS_Office_1.1.0(1454).dmg",
        "http://package.mac.wpscdn.cn/mac_wps_pkg/1.0.0/WPS2019_For_Mac_Beta_5_1.0.0(1304).dmg",
        "https://a.amap.com/lbs/static/zip/AMap3DMap_AMapNavi_AMapSearch_AMapTrack_AMapLocation.zip",
        "https://dl.google.com/dl/android/studio/install/3.4.1.0/android-studio-ide-183.5522156-mac.dmg"
    )

    override fun onCreateAdapter(datas: MutableList<DslAdapterItem>?): RBaseAdapter<DslAdapterItem> {
        return DslAdapter()
    }

    override fun isFirstNeedLoadData(): Boolean {
        return false
    }

    override fun onDetach() {
        super.onDetach()
        Rx.main {
            logAll()
        }
    }

    private fun logAll() {
        val builder = StringBuilder()
        builder.append("\nallListener -> ")
        builder.append(FDown.HostListener.instance().allListener.size)
        builder.appendln()
        FDown.HostListener.instance().mapListener.forEach { entry ->
            builder.append("${entry.key} -> ")
            builder.append(entry.value.size)
            builder.appendln()
        }
        L.e(builder.toString())
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        recyclerView?.onScroll {
            onRecyclerScrollStateChanged = { recyclerView, newState ->
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    logAll()
                }
            }
        }

        baseDslAdapter.apply {

            renderItem {
                var listener: FDownListener? = null

                itemLayoutId = R.layout.item_file_down_all
                itemBind = { itemHolder, _, _ ->
                    FDown.removeListener(listener)
                    listener = object : FDownListener() {
                        override fun onTaskStart(task: DownloadTask) {
                            super.onTaskStart(task)
                            itemHolder.tv(R.id.down_url).text = RSpan.get(nowTime().toString()).append(" 开始下载:\n")
                                .append(task.url).append("\n-->")
                                .append(task.filename ?: "").append("\n")
                                .append(task.file?.absolutePath ?: "")
                                .create()
                        }

                        override fun onTaskProgress(
                            task: DownloadTask,
                            totalLength: Long,
                            totalOffset: Long,
                            percent: Int,
                            increaseBytes: Long
                        ) {
                            super.onTaskProgress(task, totalLength, totalOffset, percent, increaseBytes)

                            itemHolder.v<SimpleProgressBar>(R.id.down_progress_bar).setProgress(percent)
                        }

                        override fun onTaskEnd(task: DownloadTask, isCompleted: Boolean, realCause: Exception?) {
                            super.onTaskEnd(task, isCompleted, realCause)
                            itemHolder.tv(R.id.down_url).text = RSpan.get(nowTime().toString()).append(" 下载结束:\n")
                                .append(task.url).append("\n-->")
                                .append("isCompleted:").append(isCompleted.toString()).append("\n")
                                .append("realCause:").append(realCause?.toString() ?: "")
                                .create()
                        }
                    }
                    FDown.listener(listener)
                }

                onItemViewDetachedToWindow = { itemHolder ->
                    FDown.removeListener(listener)
                }

                onItemViewAttachedToWindow = { itemHolder ->
                    FDown.listener(listener)
                }
            }

            urls.forEachIndexed { index, downUrl ->
                for (i in 0..if (index > 2) 10 else index * 2) {
                    renderItem {
                        var listener: FDownListener? = null

                        itemLayoutId = R.layout.item_file_down
                        itemBind = { itemHolder, itemPosition, _ ->
                            itemHolder.v<SimpleProgressBar>(R.id.down_progress_bar).setProgress(0)

                            itemHolder.tv(R.id.down_url).text = downUrl
                            val task = FDown.get(downUrl, File(FDown.defaultDownloadPath(downUrl)))

                            itemHolder.tv(R.id.down_button).text = when (FDown.getStatus(task)) {
                                StatusUtil.Status.RUNNING -> "..."
                                StatusUtil.Status.COMPLETED -> "finish"
                                StatusUtil.Status.IDLE -> "cancel"
                                else -> "Start"
                            }

                            itemHolder.click(R.id.down_button) {
                                if (FDown.getStatus(task) == StatusUtil.Status.RUNNING) {
                                    FDown.cancel(task.id)
                                } else {
                                    FDown.down(task)
                                }
                            }

                            FDown.removeListener(downUrl, listener)
                            listener = object : FDownListener() {
                                override fun onTaskStart(task: DownloadTask) {
                                    super.onTaskStart(task)
                                    itemHolder.tv(R.id.down_button).text = "..."
                                }

                                override fun onTaskProgress(
                                    task: DownloadTask,
                                    totalLength: Long,
                                    totalOffset: Long,
                                    percent: Int,
                                    increaseBytes: Long
                                ) {
                                    super.onTaskProgress(task, totalLength, totalOffset, percent, increaseBytes)
                                    itemHolder.v<SimpleProgressBar>(R.id.down_progress_bar).setProgress(percent)
                                    itemHolder.tv(R.id.down_button).text =
                                        "${FDown.calcTaskSpeed(task, increaseBytes)}/s"
                                }

                                override fun onTaskEnd(
                                    task: DownloadTask,
                                    isCompleted: Boolean,
                                    realCause: Exception?
                                ) {
                                    super.onTaskEnd(task, isCompleted, realCause)
                                    itemHolder.tv(R.id.down_button).text = if (isCompleted) "finish" else "cancel"

                                    itemHolder.v<SimpleProgressBar>(R.id.down_progress_bar).setProgress(100)
                                }
                            }
                            FDown.listener(downUrl, listener)
                        }

                        onItemViewDetachedToWindow = { itemHolder ->
                            FDown.removeListener(downUrl, listener)
                        }

                        onItemViewAttachedToWindow = { itemHolder ->
                            FDown.listener(downUrl, listener)
                        }
                    }
                }
            }
        }
    }
}