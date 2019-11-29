package com.angcyo.uikitdemo.ui

import android.os.Bundle
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.java.Java
import com.angcyo.uikitdemo.kotlin.Kotlin
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uikitdemo.ui.demo.DialogDemo
import com.angcyo.uiview.less.ContainerActivity
import com.angcyo.uiview.less.base.helper.ActivityHelper
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.component.FileSelectorFragment
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.recycler.dslitem.dslTextInfoItem
import com.angcyo.uiview.less.utils.RUtils
import com.angcyo.uiview.less.utils.Root
import com.angcyo.uiview.less.utils.Tip

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class MainDslFragment : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            renderMainItem("AdapterStatusDemo", 10 * dpi)
            renderMainItem("TabLayoutDemo")
            renderMainItem("WidgetDemo Screenshot Grayscale")
            renderMainItem("SpanDemo")
            renderMainItem("TransitionDemo")
            renderMainItem("SceneDemo")
            renderMainItem("MPChartDemo")
            renderMainItem("MPChartDemo2")
            renderMainItem("MPChartDemo3")
            renderMainItem("RxJavaDemo Observable")
            renderMainItem("PhotoViewDemo")
            renderMainItem("FozuLayoutDemo")
            renderMainItem("RecordPlayerDemo")
            renderMainItem("TBSDemo")
            renderMainItem("CameraDemo QRCode RNetwork")
            renderMainItem("RePluginDemo")
            renderMainItem("ClassLoaderDemo")
            renderMainItem("WaveDemo")
            renderMainItem("RecyclerViewDemo")
            renderMainItem("TouchDemo")
            renderMainItem("DialogDemo")
            renderMainItem("PictureSelectorDemo")
            renderMainItem("OpencvOCRDemo OCR Gravity")
            renderMainItem("AutoCompleteDemo")
            renderMainItem("FileDownDemo")
            renderMainItem("CustomViewDemo1")
            renderMainItem("CustomViewDemo2")
            renderMainItem("SoftInputDemo")
            renderMainItem("RegularPatternDemo")
            renderMainItem("WebSocketDemo ScrollHelper")
            renderMainItem("MqttDemo ScrollHelper")
            renderMainItem("CoordinatorLayoutDemo ++")
            renderMainItem("CoordinatorLayoutDemo2")
            renderMainItem("CalendarLayoutDemo")
            renderMainItem("CalendarViewDemo")
            renderMainItem("LayoutManagerDemo")
            renderMainItem("AgoraDemo")
            renderMainItem("ViewPager2Demo")
            renderMainItem("ScanOCRDemo")
            renderMainItem("NfcDemo")
            renderMainItem("BaseBehaviorDslDemo")
            renderMainItem("BaseCollapsingBehaviorDslDemo")
            renderMainItem("DslAdapterDemo")
            renderMainItem("DslTabLayoutDemo")
            renderMainItem("LinkageBehaviorDemo")
            renderMainItem("RefreshBehaviorDemo")

//            dslTextInfoItem {
//                itemInfoText = "测试"
//                itemClickListener = {
//                    ActivityHelper.build(mAttachContext)
//                        .defaultEnterAnim()
//                        .setClass(ContainerActivity::class.java)
//                        .setTargetFragment(DialogDemo::class.java)
//                        .start()
//                }
//            }

            //last item
            renderMainItem {
                itemLayoutId = R.layout.item_last
                itemTag = "Last"
                onItemBindOverride = { itemHolder, _, _ ->
                    itemHolder.tv(R.id.text_view).text =
                        StringBuilder().append(RUtils.getIP(mAttachContext))
                            .append(' ').append(RUtils.getMobileIP()).appendln()
                            .append(Root.device_info(mAttachContext))

                    itemHolder.click(R.id.text_view) {
                        RUtils.copyText(itemHolder.tv(R.id.text_view).text)
                        Tip.ok("已复制")

                        FileSelectorFragment.show(parentFragmentManager()) {
                            targetPath = Root.getAppExternalFolder()
                            showFileMd5 = true
                            showFileMenu = true
                            showHideFile = true
                        }
                    }
                }
            }
        }
    }

    public fun DslAdapter.renderMainItem(
        text: CharSequence? = null,
        topInsert: Int = 1 * dpi,
        fragment: Class<out androidx.fragment.app.Fragment>? = null,
        init: DslAdapterItem.() -> Unit = {}
    ) {
        renderItem {
            itemTopInsert = topInsert
            itemLayoutId = R.layout.base_item_info_layout

            onItemBindOverride = { itemHolder, itemPosition, _ ->
                itemHolder.item(R.id.base_item_info_layout)?.apply {
                    setLeftDrawableRes(R.drawable.ic_logo_little)
                    setItemText("${itemPosition + 1}. $text")

                    itemHolder.click(this) {
                        var cls: Class<out androidx.fragment.app.Fragment>? = fragment
                        val className = "com.angcyo.uikitdemo.ui.demo.${text?.split(" ")?.get(0)}"
                        try {
                            if (fragment == null) {
                                cls =
                                    Class.forName(className) as? Class<out androidx.fragment.app.Fragment>
                            }
                        } catch (e: Exception) {
                            Tip.tip("未找到类:\n$className")
                        }

                        cls?.let {
                            FragmentHelper.build(parentFragmentManager())
                                .showFragment(it)
                                .defaultEnterAnim()
                                .doIt()
                        }
                    }
                }
            }

            this.init()
        }
    }

    override fun onFragmentShow(bundle: Bundle?) {
        super.onFragmentShow(bundle)
        L.i(RUtils.getIP(mAttachContext))
        L.i(RUtils.getMobileIP())
        notifyItemChangedByTag("Last")

        Java.main()
        Kotlin.main()
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        if (isInPictureInPictureMode) {
            hideTitleBar()
        } else {
            showTitleBar()
        }
    }
}
