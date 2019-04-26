package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.camera.TakePictureFragment
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder

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
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        L.i("...$requestCode $permissions $grantResults")
    }
}