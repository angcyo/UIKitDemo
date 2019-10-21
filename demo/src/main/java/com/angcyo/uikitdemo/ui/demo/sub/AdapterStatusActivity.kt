package com.angcyo.uikitdemo.ui.demo.sub

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.来点数据
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.dslitem.DslAdapterStatusItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/16
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class AdapterStatusActivity : AppBaseDslRecyclerFragment() {

    override fun getContentLayoutId(): Int {
        return R.layout.activity_adatper_startus
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
        }

        initAdapterStatus()
    }

    private fun initAdapterStatus() {
        baseViewHolder.click(R.id.normal) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_NONE)
        }
        baseViewHolder.click(R.id.empty) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_EMPTY)
        }
        baseViewHolder.click(R.id.loading) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_LOADING)
        }
        baseViewHolder.click(R.id.error) {
            baseDslAdapter.setAdapterStatus(DslAdapterStatusItem.ADAPTER_STATUS_ERROR)
        }
    }

    override fun onBaseLoadData() {
        super.onBaseLoadData()
        baseDslAdapter.clearItems()
        来点数据()
    }
}
