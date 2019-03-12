package com.angcyo.uikitdemo.ui.demo

import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/03/12
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class PhotoViewDemo : BaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        fun show(holder: RBaseViewHolder, url: String) {
            (holder.imgV(R.id.photo_view) as PhotoView).apply {
                Glide.with(this)
                    .load(url)
                    .apply(RequestOptions.noAnimation())
                    .into(this)

                setOnScaleChangeListener { scaleFactor, focusX, focusY ->
                    val builder = StringBuilder().append("scale:").append(scale).appendln()
                    builder.append("display:").append(displayRect).appendln()
                    builder.append("scaleFactor:").append(scaleFactor)
                        .append(" focusX:").append(focusX)
                        .append(" focusY:").append(focusY).appendln()
                    holder.tv(R.id.text_view).text = builder
                }
            }
        }

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                show(holder, "https://up.enterdesk.com/edpic/3b/d3/8b/3bd38b061de3d8e67973349d5d38fef7.jpg")
            }
        })

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view2
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                show(holder, "http://a.hiphotos.baidu.com/image/pic/item/e850352ac65c103873a1cd82b9119313b17e8996.jpg")
            }
        })
    }
}