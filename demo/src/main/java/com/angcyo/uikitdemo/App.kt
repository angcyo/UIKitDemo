package com.angcyo.uikitdemo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.angcyo.lib.L
import com.angcyo.opencv.CardOcr
import com.angcyo.rtbs.RTbs
import com.angcyo.uiview.less.base.BaseDslRecyclerFragment
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.base.BaseUI
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.getColor
import com.angcyo.uiview.less.kotlin.random
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.utils.RNetwork
import com.angcyo.uiview.less.utils.RUtils.randomColor
import com.angcyo.uiview.less.utils.TopToast
import me.weishu.reflection.Reflection

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/20
 */
class App : PluginHostApplication() {

    companion object {
        fun isApk() = BuildConfig.FLAVOR == "apk"
    }

    override fun onBaseInit() {
        super.onBaseInit()

        L.init(!isApk(), "angcyo_" + hashCode())

        BaseUI.uiFragment = object : BaseUI.DefaultUIFragment() {

            override fun initFragment(titleFragment: BaseTitleFragment) {
                super.initFragment(titleFragment)
                titleFragment.viewResConfig.titleTextColor = Color.WHITE
                titleFragment.viewResConfig.fragmentBackgroundColor = getColor(R.color.line_color)
                titleFragment.viewResConfig.fragmentBackgroundDrawable =
                    ColorDrawable(titleFragment.viewResConfig.fragmentBackgroundColor)
            }
        }

        RTbs.init(this, BuildConfig.SHOW_DEBUG)

        RNetwork.init(this)

        if (!isGenymotion()) {
            CardOcr.init(this)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Reflection.unseal(base)
    }
}

/**填充色块*/
fun ViewGroup.appendColorItem(size: Int = 100) {
    for (i in 0 until size) {
        addView(View(context).apply {
            setBackgroundColor(randomColor(random))
        }, -1, 100 * dpi)
    }
}

fun BaseDslRecyclerFragment.来点数据(groupCount: Int = 5, subCount: Int = 5) {
    renderDslAdapter {
        fun renderText() {
            renderItem {
                itemSpanCount = 4

                itemIsGroupHead = true

                itemLayoutId = R.layout.item_text

                itemBind = { itemHolder, itemPosition, adapterItem ->
                    L.d("bind...$itemPosition")
                    itemHolder.tv(R.id.text_view).text = "位置$itemPosition"
                    itemHolder.cV(R.id.check_box).isChecked = !adapterItem.itemGroupExtend

                    itemHolder.clickItem {
                        TopToast.show("点击位置:$itemPosition".apply {
                            L.i(it)
                        }, -1)

                        itemDslAdapter?.foldItem(adapterItem, adapterItem.itemGroupExtend)
                    }
                }
            }
        }

        fun renderImage() {
            renderItem {
                itemTopInsert = 10 * dpi
                itemBottomInsert = 5 * dpi
                onlyDrawOffsetArea = false
                eachDrawItemDecoration = { _, top, _, bottom ->
                    if (top > 0) {
                        itemDecorationColor = Color.BLUE
                    } else if (bottom > 0) {
                        itemDecorationColor = Color.GREEN
                    }
                }
                itemLayoutId = R.layout.item_image_little

                itemBind = { itemHolder, itemPosition, adapterItem ->
                    L.v("bind...$itemPosition")
                    itemHolder.tv(R.id.text_view).text = "位置$itemPosition"

                    itemHolder.clickItem {
                        TopToast.show("点击位置:$itemPosition".apply {
                            L.i(it)
                        }, -1)
                    }
                }
            }
        }

        renderImage()

        for (i in 0 until groupCount) {
            renderText()
            for (j in 0 until subCount) {
                renderImage()
            }
        }
    }
}

fun isGenymotion(): Boolean =
    TextUtils.equals("genymotion", Build.MANUFACTURER.toLowerCase()) ||
            Build.DEVICE?.startsWith("vbox") == true