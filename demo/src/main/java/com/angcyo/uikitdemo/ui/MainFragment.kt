package com.angcyo.uikitdemo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
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
        contentControl().selector().setBackgroundColor(getColor(R.color.line_color))
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : MainItem(Type.TOP) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "测试", View.OnClickListener {

                })
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "测试", View.OnClickListener {

                })
            }
        })
    }
}

open class MainItem : SingleItem {
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

    fun initItem(holder: RBaseViewHolder, text: String, listener: View.OnClickListener) {
        holder.item(R.id.base_item_info_layout).apply {
            setLeftDrawableRes(R.drawable.ic_logo_little)
            setItemText(text)

            holder.click(this, listener)
        }
    }
}