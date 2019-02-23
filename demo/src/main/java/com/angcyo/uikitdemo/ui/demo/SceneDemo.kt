package com.angcyo.uikitdemo.ui.demo

import android.support.transition.ChangeBounds
import android.support.transition.Scene
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.widget.RadioGroup
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.recycler.item.Item
import com.angcyo.uiview.less.recycler.item.SingleItem
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/23
 */
class SceneDemo : AppBaseItemFragment() {

    var transition: Transition = ChangeBounds()

    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.demo_scene
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {

                holder.v<RadioGroup>(R.id.radio_group).setOnCheckedChangeListener { group, checkedId ->
                    val text = holder.tv(checkedId).text
                    transition = if (text == "TextScale") {
                        Class.forName("android.support.design.internal.$text")
                            .newInstance() as Transition
                    } else {
                        Class.forName("android.support.transition.$text")
                            .newInstance() as Transition
                    }
                }

                holder.click(R.id.button1) {
                    TransitionManager.go(
                        Scene.getSceneForLayout(
                            holder.group(R.id.frame_layout),
                            R.layout.scene_layout1,
                            mAttachContext
                        ), transition
                    )
                }

                holder.click(R.id.button2) {
                    TransitionManager.go(
                        Scene.getSceneForLayout(
                            holder.group(R.id.frame_layout),
                            R.layout.scene_layout2,
                            mAttachContext
                        ), transition
                    )
                }

                holder.click(R.id.button3) {
                    TransitionManager.go(
                        Scene.getSceneForLayout(
                            holder.group(R.id.frame_layout),
                            R.layout.scene_layout3,
                            mAttachContext
                        ), transition
                    )
                }

                holder.click(R.id.button4) {
                    TransitionManager.go(
                        Scene.getSceneForLayout(
                            holder.group(R.id.frame_layout),
                            R.layout.scene_layout4,
                            mAttachContext
                        ), transition
                    )
                }
            }

        })
    }

}