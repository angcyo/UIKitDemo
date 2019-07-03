package com.angcyo.uikitdemo.ui.demo

import android.content.Intent
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.picture.RPager
import com.angcyo.uiview.less.picture.RPicture
import com.angcyo.uiview.less.picture.multipleMode
import com.angcyo.uiview.less.picture.ofImage
import com.angcyo.uiview.less.recycler.item.SingleItem
import com.luck.picture.lib.entity.LocalMedia
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/05/18
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class PictureSelectorDemo : AppBaseItemFragment() {

    var lastMedia: List<LocalMedia>? = null

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        dslCreateItem {
            singleItemLayoutId = R.layout.item_picture_selector

            singleItemBind = { holder, posInData, singItem ->
                holder.click(R.id.single_all) {
                    RPicture.build(requireActivity())
                        .singleMode()
                        .ofAll()
                        .doIt()
                }

                holder.click(R.id.single_image) {
                    RPicture.build(requireActivity())
                        .singleMode()
                        .ofImage()
                        .doIt()
                }

                holder.click(R.id.single_video) {
                    RPicture.build(requireActivity())
                        .ofVideo()
                        .singleMode()
                        .doIt()
                }

                holder.click(R.id.single_audio) {
                    RPicture.build(requireActivity())
                        .ofAudio()
                        .singleMode()
                        .doIt()
                }

                //多选
                holder.click(R.id.multi_all) {
                    RPicture.build(requireActivity())
                        .ofAll()
                        .multipleMode()
                        .setSelectionMedia(lastMedia)
                        .config {
                            showFileSize(true)
                            maxFileSize(1024 * 1024 * 10) //10MB
                        }
                        .doIt()
                }

                holder.click(R.id.multi_image) {
                    RPicture.build(requireActivity())
                        .ofImage()
                        .setSelectionMedia(lastMedia)
                        .multipleMode()
                        .doIt()
                }

                holder.click(R.id.multi_video) {
                    RPicture.build(requireActivity())
                        .ofVideo()
                        .multipleMode()
                        .setSelectionMedia(lastMedia)
                        .doIt()
                }

                holder.click(R.id.multi_audio) {
                    RPicture.build(requireActivity())
                        .ofAudio()
                        .multipleMode()
                        .setSelectionMedia(lastMedia)
                        .doIt()
                }

                //多选剪切
                holder.click(R.id.multi_image_crop) {
                    RPicture.selector(requireActivity()) {
                        ofImage()
                        multipleMode()
                        maxSelectNum(3)
                        enableCrop(true)
                    }
                }
            }
        }

        dslCreateItem {
            singleItemLayoutId = R.layout.item_recycler_view

            singleItemBind = { holder, posInData, singItem ->
                holder.itemView.apply {
                    setPadding(0, 0, 0, 100 * dpi)
                    setWidthHeight(-1, -2)
                    setBackgroundColor(getColor(R.color.transparent_dark20))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val list = RPicture.onActivityResult(requestCode, resultCode, data) ?: return

        lastMedia = list

        baseViewHolder.rv(R.id.item_recycler_view)
            .dslAdapter(4) {

                list.forEach { media ->
                    renderItem {
                        itemLayoutId = R.layout.item_image_layout
                        itemBind = { itemHolder, itemPosition, adapterItem ->
                            itemHolder.imageView(R.id.image_view)
                                .load(media.filePath) {

                                }

                            itemHolder.clickItem {
                                RPager.pager(parentFragmentManager()) {
                                    startPagerIndex = itemPosition
                                    pagerCount = list.size

                                    onGetPagerImageUrl = {
                                        list[it].filePath
                                    }

                                    onGetTargetView = { position ->
                                        baseViewHolder.rv(R.id.item_recycler_view).findViewHolder(position)
                                            ?.view(R.id.image_view)
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

}