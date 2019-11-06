package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.http.Func
import com.angcyo.http.HttpSubscriber
import com.angcyo.http.Rx
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.dpi
import com.angcyo.uiview.less.kotlin.renderItem
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.adapter.DslAdapterItem
import com.angcyo.uiview.less.utils.NetStateChangeObserver
import com.angcyo.uiview.less.utils.NetworkType
import com.angcyo.uiview.less.utils.RNetwork

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/03/09
 */
class RxJavaDemo : AppBaseDslRecyclerFragment() {
    val observer: NetStateChangeObserver by lazy {
        object : NetStateChangeObserver {
            override fun onNetDisconnected() {
                L.e("RxJavaDemo:断网")

                renderDslAdapter {
                    renderDemoItem("RxJavaDemo:断网")
                }
            }

            override fun onNetConnected(networkType: NetworkType) {
                L.w("RxJavaDemo:网络->$networkType")

                renderDslAdapter {
                    renderDemoItem("RxJavaDemo:网络->$networkType")
                }
            }
        }
    }

    override fun onFragmentShow(bundle: Bundle?) {
        super.onFragmentShow(bundle)
        RNetwork.registerObserver(observer)
    }

    override fun onFragmentHide() {
        super.onFragmentHide()
        RNetwork.unregisterObserver(observer)
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        renderDslAdapter {
            renderDemoItem("operators") {
                onItemBindOverride = { itemHolder, itemPosition, _ ->
                    itemHolder.item(R.id.base_item_info_layout)?.apply {
                        setLeftDrawableRes(R.drawable.ic_logo_little)
                        setItemText("${itemPosition + 1}. operators")
                    }

                    //http://reactivex.io/documentation/operators.html
                    itemHolder.clickItem {
                        Rx.merge<List<Int>>(
                            Rx.just(1).map {
                                listOf(it)
                            },
                            Rx.just(2).map {
                                listOf(it)
                            },
                            Rx.just(5).map {
                                listOf(it)
                            },
                            Rx.just(7).map {
                                listOf(it)
                            }
                        )
                            .map {
                                it
                            }
                            .scan { t1: List<Int>?, t2: List<Int>? ->
                                val result = mutableListOf<Int>()
                                t1?.let {
                                    result.addAll(it)
                                }
                                t2?.let {
                                    result.addAll(it)
                                }
                                result
                            }
                            .subscribe(object : HttpSubscriber<List<Int>>() {
                                override fun onEnd(data: List<Int>?, error: Throwable?) {
                                    super.onEnd(data, error)
                                    L.i(data)
                                }
                            })
                    }
                }
            }

            renderDemoItem("Rx Base Test") {
                onItemBindOverride = { itemHolder, itemPosition, _ ->
                    itemHolder.item(R.id.base_item_info_layout)?.apply {
                        setLeftDrawableRes(R.drawable.ic_logo_little)
                        setItemText("${itemPosition + 1}. Rx Base Test")
                    }

                    //http://reactivex.io/documentation/operators.html
                    itemHolder.clickItem {
                        Rx.back {
                            L.i("onBack")
                        }

                        Rx.main {
                            L.i("onMain")
                        }

                        Rx.create(Func {
                            L.i("onCreate")
                        }).subscribe()
                    }
                }
            }
        }
    }

    public fun DslAdapter.renderDemoItem(
        text: CharSequence? = null,
        topInsert: Int = 1 * dpi,
        init: DslAdapterItem.() -> Unit = {}
    ) {
        renderItem {
            itemTopInsert = topInsert
            itemLayoutId = R.layout.base_item_info_layout

            onItemBindOverride = { itemHolder, itemPosition, _ ->
                itemHolder.item(R.id.base_item_info_layout)?.apply {
                    setLeftDrawableRes(R.drawable.ic_logo_little)
                    setItemText("${itemPosition + 1}. $text")
                }
            }

            this.init()
        }
    }
}