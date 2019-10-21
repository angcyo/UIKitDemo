package com.angcyo.uikitdemo.ui.demo.sub

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.DslAdapterStatusItem
import com.angcyo.uiview.less.recycler.dslitem.DslLoadMoreItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/16
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class LoadMoreActivity : AppBaseDslRecyclerFragment() {

    override fun getContentLayoutId(): Int {
        return R.layout.activity_load_more
    }

    override fun enableRefreshAffect() {
        //super.enableRefreshAffect()
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)

        baseViewHolder.postDelay(1000) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)
            来点数据()
            baseDslAdapter.setLoadMoreEnable(true)
        }

        initLoadMore()
    }

    override fun onBaseLoadData() {
        super.onBaseLoadData()
        baseDslAdapter.clearItems()
        来点数据()
    }

    var loadPage = 0
    private fun initLoadMore() {
        baseDslAdapter.dslLoadMoreItem.onLoadMore = {
            it.postDelay(300L) {
                loadPage++
                if (loadPage == 2) {
                    //模拟加载失败
                    baseDslAdapter.setLoadMore(DslLoadMoreItem.ADAPTER_LOAD_ERROR)
                } else if (loadPage > 3) {
                    //模拟没有更多
                    baseDslAdapter.setLoadMore(DslLoadMoreItem.ADAPTER_LOAD_NO_MORE)
                } else {
                    来点数据()

//                    for (i in 0..0) {
//                        baseDslAdapter.dslItem(R.layout.item_text_layout) {
//                            itemBind = { itemHolder, itemPosition, _ ->
//                                itemHolder.v<TextView>(R.id.text_view).text =
//                                    "新增的数据! 文本位置:$itemPosition"
//                            }
//                        }
//                    }

                    baseDslAdapter.setLoadMore(DslLoadMoreItem.ADAPTER_LOAD_NORMAL)
                }
            }
        }
        baseViewHolder.click(R.id.load_more_enable) {
            loadPage = 0
            baseDslAdapter.setLoadMoreEnable(it.isSelected)
            it.isSelected = !it.isSelected
        }
        baseViewHolder.click(R.id.load_more_error) {
            loadPage = 0
            baseDslAdapter.setLoadMore(DslLoadMoreItem.ADAPTER_LOAD_ERROR)
        }
        baseViewHolder.click(R.id.load_more_no) {
            loadPage = 0
            baseDslAdapter.setLoadMore(DslLoadMoreItem.ADAPTER_LOAD_NO_MORE)
        }
    }
}
