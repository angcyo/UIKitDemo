package com.angcyo.uikitdemo.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.demo.AdapterStatusDemo
import com.angcyo.uikitdemo.ui.demo.TabLayoutDemo
import com.angcyo.uikitdemo.ui.demo.WidgetDemo
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.Root
import com.angcyo.uiview.less.utils.Tip
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/20
 */
class MainFragment : BaseItemFragment() {

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : MainItem(Type.TOP) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "AdapterStatusDemo", posInData, AdapterStatusDemo::class.java)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "TabLayoutDemo", posInData, TabLayoutDemo::class.java)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "WidgetDemo", posInData, WidgetDemo::class.java)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {

            override fun getItemLayoutId(): Int {
                return R.layout.item_last
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.tv(R.id.text_view).text = Root.device_info(mAttachContext)
                holder.click(R.id.text_view) {
                    RUtils.copyText(holder.tv(R.id.text_view).text)
                    Tip.ok("已复制")
                }
            }
        })
    }

    open inner class MainItem : SingleItem {
        constructor() : super()
        constructor(type: Type?, tag: String?) : super(type, tag)
        constructor(tag: String?) : super(tag)
        constructor(type: Type?) : super(type)
        constructor(type: Type?, lineColor: Int) : super(type, lineColor)
        constructor(context: Context?) : super(context)
        constructor(topOffset: Int) : super(topOffset)
        constructor(leftOffset: Int, topOffset: Int) : super(leftOffset, topOffset)
        constructor(leftOffset: Int, topOffset: Int, lineColor: Int) : super(leftOffset, topOffset, lineColor)

        override fun getItemLayoutId(): Int {
            return super.getItemLayoutId()
        }

        override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
        }

        fun initItem(holder: RBaseViewHolder, text: String, position: Int, fragment: Class<out Fragment>? = null) {
            holder.item(R.id.base_item_info_layout).apply {
                setLeftDrawableRes(R.drawable.ic_logo_little)
                setItemText("${position + 1}. $text")

                holder.click(this) {
                    fragment?.let {
                        FragmentHelper.build(parentFragmentManager())
                            .showFragment(fragment)
                            .defaultEnterAnim()
                            .doIt()
                    }
                }
            }
        }
    }
}

