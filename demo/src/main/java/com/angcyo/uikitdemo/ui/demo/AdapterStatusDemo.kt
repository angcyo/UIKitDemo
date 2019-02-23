package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.util.SparseIntArray
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseRecyclerFragment
import com.angcyo.uiview.less.iview.AffectUI
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

class AdapterStatusDemo : AppBaseRecyclerFragment<String>() {

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

        affectUI.showAffect(AffectUI.AFFECT_LOADING)
    }

    override fun onCreateAdapter(datas: MutableList<String>?): RBaseAdapter<String> {
        return object : RBaseAdapter<String>() {
            override fun registerLayouts(layouts: SparseIntArray) {
                super.registerLayouts(layouts)
                layouts.put(1, android.R.layout.simple_list_item_1)
                layouts.put(2, android.R.layout.simple_list_item_2)
                layouts.put(3 or RBaseAdapter.ITEM_SINGLE_LINE, R.layout.item_single_text)
            }

            override fun getItemType(position: Int, data: String?): Int {
                return when (position % 4) {
                    1 -> 1
                    3 -> 1
                    2 -> 2
                    else -> 3 or RBaseAdapter.ITEM_SINGLE_LINE
                }
            }

            override fun onBindView(holder: RBaseViewHolder, position: Int, bean: String?) {
                when (holder.itemViewType) {
                    1 -> holder.tv(android.R.id.text1).text = "$position"
                    3 -> holder.tv(android.R.id.text1).text = "$position"
                    2 -> {
                        holder.tv(android.R.id.text1).text = "text1:$position"
                        holder.tv(android.R.id.text2).text = "text2:$position"
                    }
                    else -> holder.tv(R.id.text_view).text = "single text:$position"
                }
            }
        }
    }

    override fun onBaseLoadData() {
        //super.onBaseLoadData()
        if (currentPageIndex == 3) {
            baseViewHolder.postDelay(2000) {
                onBaseLoadEnd(emptyList(), 5)
            }
        } else {
            baseViewHolder.postDelay(2000) {
                onBaseLoadEnd(mutableListOf("1", "2", "3", "4", "5"), 5)
            }
        }
    }
}