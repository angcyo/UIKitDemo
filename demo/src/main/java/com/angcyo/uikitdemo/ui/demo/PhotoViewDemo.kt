package com.angcyo.uikitdemo.ui.demo

import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.picture.RPhotoPager
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.angcyo.uiview.less.widget.GlideImageView
import com.angcyo.uiview.less.widget.group.RNineImageLayout
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
    companion object {
        val urls = listOf(
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3366295473,911032378&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1724436057,3073006466&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1601453024,3094964372&fm=26&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552454340928&di=655882bc55efec1635cfefd5397fe6d0&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fc995d143ad4bd113ece5c17351afa40f4bfb0542.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2896686573,3507295694&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1364237778,861189788&fm=26&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552454383106&di=f16e8eec4f9996a453bd5b2c88f81695&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fblog%2F201507%2F06%2F20150706130957_c4Pe4.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552454407855&di=bf0662a308f60fdd3fd445b3ec79df50&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201604%2F03%2F20160403055413_AzGjV.thumb.100_100_c.jpeg",
            "http://hbimg.b0.upaiyun.com/823d7bd817f69a3b3ce2f9720e2f6adfc11a04365d138-LDS1uE_fw658"
        )
    }

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

            holder.click(R.id.text_view) {
                RPhotoPager.start(
                    parentFragmentManager()!!,
                    listOf(holder.imgV(R.id.photo_view)),
                    mutableListOf(url).apply {
                        addAll(urls)
                    },
                    0
                )
            }
        }

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                //show(holder, "https://up.enterdesk.com/edpic/3b/d3/8b/3bd38b061de3d8e67973349d5d38fef7.jpg")
                show(holder, "http://hbimg.b0.upaiyun.com/823d7bd817f69a3b3ce2f9720e2f6adfc11a04365d138-LDS1uE_fw658")
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
        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view2
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                show(
                    holder,
                    "https://raw.githubusercontent.com/angcyo/res/master/png/QQ%E6%88%AA%E5%9B%BE20190312175542.png"
                )
            }
        })

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view3
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.v<RNineImageLayout>(R.id.nine_image_layout)
                    .apply {
                        setNineImageConfig(object : RNineImageLayout.NineImageConfig {
                            override fun getWidthHeight(imageSize: Int): IntArray {
                                return intArrayOf(150, 240)
                            }

                            override fun displayImage(
                                imageView: GlideImageView,
                                url: String,
                                width: Int,
                                height: Int,
                                imageSize: Int
                            ) {
                                imageView.url = url
                            }

                            override fun onImageItemClick(
                                imageView: GlideImageView,
                                urlList: MutableList<String>,
                                imageList: MutableList<GlideImageView>,
                                index: Int
                            ) {
                                RPhotoPager.start(parentFragmentManager()!!, imageList, urlList, index)
                            }
                        })

                        setImagesList(urls)
                    }
            }
        })

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view3
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.v<RNineImageLayout>(R.id.nine_image_layout)
                    .apply {
                        setNineImageConfig(object : RNineImageLayout.NineImageConfig {
                            override fun getWidthHeight(imageSize: Int): IntArray {
                                return intArrayOf(150, 240)
                            }

                            override fun displayImage(
                                imageView: GlideImageView,
                                url: String,
                                width: Int,
                                height: Int,
                                imageSize: Int
                            ) {
                                imageView.url = url
                            }

                            override fun onImageItemClick(
                                imageView: GlideImageView,
                                urlList: MutableList<String>,
                                imageList: MutableList<GlideImageView>,
                                index: Int
                            ) {
                                RPhotoPager.start(parentFragmentManager()!!, imageList, urlList, index)
                            }
                        })

                        setImagesList(urls)
                    }
            }
        })

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.item_singlt_photo_view3
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.v<RNineImageLayout>(R.id.nine_image_layout)
                    .apply {
                        setNineImageConfig(object : RNineImageLayout.NineImageConfig {
                            override fun getWidthHeight(imageSize: Int): IntArray {
                                return intArrayOf(150, 240)
                            }

                            override fun displayImage(
                                imageView: GlideImageView,
                                url: String,
                                width: Int,
                                height: Int,
                                imageSize: Int
                            ) {
                                imageView.url = url
                            }

                            override fun onImageItemClick(
                                imageView: GlideImageView,
                                urlList: MutableList<String>,
                                imageList: MutableList<GlideImageView>,
                                index: Int
                            ) {
                                RPhotoPager.start(parentFragmentManager()!!, imageList, urlList, index)
                            }
                        })

                        setImagesList(urls)
                    }
            }
        })
    }
}