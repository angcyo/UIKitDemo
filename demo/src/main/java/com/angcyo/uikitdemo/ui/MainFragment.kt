package com.angcyo.uikitdemo.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.java.Java
import com.angcyo.uikitdemo.kotlin.Kotlin
import com.angcyo.uikitdemo.ui.demo.RePluginDemo
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.base.helper.FragmentHelper
import com.angcyo.uiview.less.kotlin.getViewRect
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

    override fun getViewHolderInitialCapacity(): Int {
        return 128
    }

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : MainItem(Type.TOP) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "AdapterStatusDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "TabLayoutDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "WidgetDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "TransitionDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "SceneDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "MPChartDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "MPChartDemo2", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "MPChartDemo3", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "RxJavaDemo Observable", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "PhotoViewDemo", posInData)
            }
        })

        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "FozuLayoutDemo", posInData)
            }
        })

        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "RecordPlayerDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "TBSDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "CameraDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "RePluginDemo", posInData)

                holder.click(R.id.base_item_info_layout) {
                    FragmentHelper.build(parentFragmentManager())
                        .showFragment(RePluginDemo().apply {
                            showFromRect = it.getViewRect()
                        })
                        .noAnim()
                        .doIt()
                }
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "WaveDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "RecyclerViewDemo", posInData)
            }
        })
        singleItems.add(object : MainItem(Type.LINE) {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                initItem(holder, "TouchDemo", posInData)
            }
        })

        //last item
        singleItems.add(object : MainItem(Type.LINE, "Last") {

            override fun getItemLayoutId(): Int {
                return R.layout.item_last
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.tv(R.id.text_view).text = StringBuilder().append(RUtils.getIP(mAttachContext))
                    .appendln().append(RUtils.getMobileIP()).appendln().append(Root.device_info(mAttachContext))

                holder.click(R.id.text_view) {
                    RUtils.copyText(holder.tv(R.id.text_view).text)
                    Tip.ok("已复制")
                }
            }
        })
    }

    override fun onFragmentShow(bundle: Bundle?) {
        super.onFragmentShow(bundle)
        L.i(RUtils.getIP(mAttachContext))
        L.i(RUtils.getMobileIP())
        notifyItemChangedByTag("Last")

        Java.main()
        Kotlin.main()
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
                    var cls: Class<out Fragment>? = fragment
                    val className = "com.angcyo.uikitdemo.ui.demo.${text.split(" ")[0]}"
                    try {
                        if (fragment == null) {
                            cls = Class.forName(className) as? Class<out Fragment>
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
    }
}

