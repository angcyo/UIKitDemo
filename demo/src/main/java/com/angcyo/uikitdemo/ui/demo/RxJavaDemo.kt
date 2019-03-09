package com.angcyo.uikitdemo.ui.demo

import com.angcyo.http.HttpSubscriber
import com.angcyo.http.Rx
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseItemFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/03/09
 */
class RxJavaDemo : BaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                holder.item(R.id.base_item_info_layout).setItemText("operators")

                //http://reactivex.io/documentation/operators.html
                holder.clickItem {
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

        })
    }

}