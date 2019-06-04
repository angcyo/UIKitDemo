package com.angcyo.uikitdemo.ui.demo

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import com.angcyo.camera.RecordVideoCallback
import com.angcyo.camera.RecordVideoFragment
import com.angcyo.camera.TakePictureFragment
import com.angcyo.lib.L
import com.angcyo.rcode.CodeScanFragment
import com.angcyo.tesstwo.IDCardScanFragment
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.kotlin.getViewRect
import com.angcyo.uiview.less.kotlin.load
import com.angcyo.uiview.less.media.play.TextureVideoView
import com.angcyo.uiview.less.picture.BasePhotoTransitionFragment
import com.angcyo.uiview.less.picture.BaseTransitionFragment
import com.angcyo.uiview.less.picture.RPager
import com.angcyo.uiview.less.picture.transition.TransitionConfig
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/26
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CameraDemo : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.fragment_camera_demo
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.click(R.id.take_picture_confirm) {
            TakePictureFragment.show(parentFragmentManager(), true, null) { bitmap, _ ->
                viewHolder.giv(R.id.image_view).setImageBitmap(bitmap)
            }
        }

        viewHolder.click(R.id.take_picture_no_confirm) {
            TakePictureFragment.show(parentFragmentManager(), false, null) { bitmap, _ ->
                viewHolder.giv(R.id.image_view).setImageBitmap(bitmap)
            }
        }

        viewHolder.click(R.id.id_card_scan) {
            IDCardScanFragment.show(parentFragmentManager()) { cardBmp, numBmp, no ->
                viewHolder.giv(R.id.image_view).setImageBitmap(cardBmp)
                viewHolder.giv(R.id.image_view2).setImageBitmap(numBmp)
                viewHolder.tv(R.id.text_view).text = no
            }
        }

        viewHolder.click(R.id.transition_button) {
            FragmentHelper.build(parentFragmentManager())
                .noAnim()
                .showFragment(BasePhotoTransitionFragment())
                .setArgs(BaseTransitionFragment.KEY_TRANSITION_FROM_RECT, it.getViewRect())
                .doIt()
        }

        viewHolder.click(R.id.scan_button) {
            FragmentHelper.build(parentFragmentManager())
                .defaultEnterAnim()
                .showFragment(CodeScanFragment().apply {
                    onScanResult = {
                        viewHolder.tv(R.id.text_view).text = it
                    }
                })
                .doIt()
        }

        viewHolder.click(R.id.image_view) {
            FragmentHelper.build(parentFragmentManager())
                .noAnim()
                .showFragment(BasePhotoTransitionFragment().apply {
                    config {
                        configPreview = { _, preview, _ ->
                            TransitionConfig.configPreviewFromImageView(preview, it as ImageView)
                        }
                    }
                })
                .setArgs(BaseTransitionFragment.KEY_TRANSITION_FROM_RECT, it.getViewRect())
                .doIt()
        }

        viewHolder.imgV(R.id.image_view2)
            .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3213427760,1870503692&fm=26&gp=0.jpg")

        viewHolder.click(R.id.image_view2) { view ->
            //            FragmentHelper.build(parentFragmentManager())
//                .noAnim()
//                .showFragment(BasePhotoTransitionFragment().apply {
//                    config {
//                        configPreview = { _, preview, _ ->
//                            TransitionConfig.configPreviewFromImageView(preview, it as ImageView)
//                        }
//                    }
//                })
//                .setArgs(BaseTransitionFragment.KEY_TRANSITION_FROM_RECT, it.getViewRect())
//                .doIt()

            RPager.pager(parentFragmentManager()) {
                onGetTargetView = {
                    view
                }
            }
        }

        viewHolder.click(R.id.record_video) {
            RecordVideoFragment.show(parentFragmentManager(), object : RecordVideoCallback() {
                override fun onTakePhoto(bitmap: Bitmap, outputFile: File) {
                    super.onTakePhoto(bitmap, outputFile)
                    viewHolder.tv(R.id.text_view).text = outputFile.absolutePath
                    viewHolder.giv(R.id.image_view).url = outputFile.absolutePath
                }

                override fun onTakeVideo(videoPath: String) {
                    super.onTakeVideo(videoPath)
                    viewHolder.tv(R.id.text_view).text = videoPath
                    viewHolder.v<TextureVideoView>(R.id.video_view).apply {
                        setVideoPath(videoPath)
                        start()
                    }
                }
            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        L.i("...$requestCode $permissions $grantResults")
    }
}