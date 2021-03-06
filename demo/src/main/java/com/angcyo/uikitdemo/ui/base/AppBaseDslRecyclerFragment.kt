package com.angcyo.uikitdemo.ui.base

import android.os.Bundle
import com.angcyo.uiview.less.base.BaseDslRecyclerFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.adapter.RBaseAdapter

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/11
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

open class AppBaseDslRecyclerFragment : BaseDslRecyclerFragment() {

    override fun isFirstNeedLoadData(): Boolean {
        return false
    }

    override fun onAdapterRefresh(baseAdapter: RBaseAdapter<DslAdapterItem>): Boolean {
        if (baseAdapter is DslAdapter) {
            return false
        }
        return super.onAdapterRefresh(baseAdapter)
    }

    override fun onUIDelayLoadData() {
        super.onUIDelayLoadData()
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        enableRefreshAffect()
    }
}