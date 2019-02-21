package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseRecyclerFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.less.recycler.widget.IShowState
import com.angcyo.uiview.less.recycler.widget.ItemShowStateLayout

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/20
 */

class AdapterStatusDemo : BaseRecyclerFragment<String>() {

    override fun getContentLayoutId(): Int {
        return R.layout.fragment_adapter_status
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        val itemShowStateLayout: ItemShowStateLayout = viewHolder.v(R.id.show_state_layout)
        viewHolder.click(R.id.empty_button) {
            itemShowStateLayout.showState = IShowState.EMPTY
            baseAdapter.setShowState(IShowState.EMPTY)
        }
        viewHolder.click(R.id.load_button) {
            itemShowStateLayout.showState = IShowState.LOADING
            baseAdapter.setShowState(IShowState.LOADING)
        }
        viewHolder.click(R.id.error_button) {
            itemShowStateLayout.showState = IShowState.ERROR
            baseAdapter.setShowState(IShowState.ERROR)
        }
        viewHolder.click(R.id.nonet_button) {
            itemShowStateLayout.showState = IShowState.NONET
            baseAdapter.setShowState(IShowState.NONET)
        }
    }

    override fun onCreateAdapter(datas: MutableList<String>?): RBaseAdapter<String> {
        return super.onCreateAdapter(datas)
    }

    override fun onBaseLoadData() {
        //super.onBaseLoadData()
        baseViewHolder.postDelay(2000) {
            onBaseLoadEnd(mutableListOf("1", "2", "3", "4", "5"), 5)
        }
    }
}