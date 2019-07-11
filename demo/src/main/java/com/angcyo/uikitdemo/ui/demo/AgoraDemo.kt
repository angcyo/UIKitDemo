package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.agora.RAgora
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/07/11
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class AgoraDemo : BaseFragment() {

    lateinit var agora: RAgora

    override fun getLayoutId(): Int {
        return R.layout.demo_agora_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        agora = RAgora(requireActivity()).apply {
            appid = "01b28f61d1ea4ce7849f4a691b671df6"

            onUserChange = { uid, isLeave ->
                baseViewHolder.tv(R.id.tip_text_view).append("\n")
                baseViewHolder.tv(R.id.tip_text_view).append(if (isLeave) "$uid 已离开..." else "$uid 正在加入...")
            }

            onFirstRemoteDecoded = { uid, isVideo ->
                baseViewHolder.tv(R.id.tip_text_view).append("\n$uid ${if (isVideo) "视频" else "音频"} 第一帧解码.")

                if (isVideo) {
                    showRemoteVideo(baseViewHolder.group(R.id.remote_video_container), uid)
                }
            }

            onUserEnableVideo = { uid, isLocal, enable ->
                baseViewHolder.tv(R.id.tip_text_view)
                    .append("\n$uid ${if (isLocal) "本地视频" else "远程视频"} ${if (enable) "启用" else "关闭"}.")
            }

            onUserMute = { uid, isVideo, mute ->
                baseViewHolder.tv(R.id.tip_text_view)
                    .append("\n$uid ${if (isVideo) "视频" else "音频"} ${if (mute) " 关闭流" else " 开启流"}.")
            }
        }
    }

    override fun initBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.initBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.click(R.id.enable_video_view) {
            if (it.isSelected) {
                //关闭视频流
                agora.enableVideo(false)
                agora.enableLocalVideoStream(false)
            } else {
                agora.enableVideo(true)
                agora.enableLocalVideoStream(true)
                agora.showLocalVideo(viewHolder.group(R.id.local_video_container))
            }
            it.isSelected = !it.isSelected
        }

        viewHolder.click(R.id.mute_voice_view) {
            if (it.isSelected) {
                agora.enableLocalAudioStream(true)
            } else {
                agora.enableLocalAudioStream(false)
            }
            it.isSelected = !it.isSelected
        }

        viewHolder.click(R.id.switch_camera_view) {
            agora.switchCamera()
        }
    }

    override fun onFragmentFirstShow(bundle: Bundle?) {
        super.onFragmentFirstShow(bundle)
        agora.joinChannel(
            "00601b28f61d1ea4ce7849f4a691b671df6IAAUcxHh0KqJedoWb5HM+7owPSb+X8L4uBvBKRmQ+P+SpcWw+JsAAAAAEADsl4C6LE0oXQEAAQAtTShd",
            "angcyo"
        )
    }

    override fun onFragmentHide() {
        super.onFragmentHide()
    }

    override fun onDestroy() {
        super.onDestroy()
        agora.destroy()
    }
}