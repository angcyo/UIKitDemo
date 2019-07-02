package com.angcyo.uikitdemo.ui.demo

import androidx.transition.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import android.widget.RadioGroup
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uiview.less.kotlin.setWidthHeight
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

                holder.click(R.id.button5) {
                    holder.group(R.id.frame_layout).apply {
                        L.i("tx:$translationX ty:$translationY l:$left t:$top sx:$scrollX sy:$scrollY")
                    }

                    holder.group(R.id.frame_layout).apply {
//                        translationX = 100f
//                        translationY = 100f

                        //ViewCompat.offsetLeftAndRight(this, 200)
                    }

                    TransitionManager.beginDelayedTransition(holder.group(R.id.frame_layout), TransitionSet().apply {
                        duration = 3000
                        addTransition(ChangeBounds())
                        addTransition(ChangeTransform())
                        addTransition(ChangeImageTransform())
                        interpolator = FastOutSlowInInterpolator()
                    })

                    holder.group(R.id.frame_layout).apply {
                        setWidthHeight(-1, -1)
                        translationX = 0f
                        translationY = 0f
                        //ViewCompat.offsetLeftAndRight(this, 100)
                    }
                }
            }

        })
    }

}