package com.angcyo.uikitdemo.ui.demo

import android.animation.Animator
import android.content.Context
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Parcelable
import android.support.transition.*
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseItemFragment
import com.angcyo.uikitdemo.ui.fragment.TransitionFragment
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
class TransitionDemo : AppBaseItemFragment() {
    override fun onCreateItems(singleItems: ArrayList<SingleItem>) {
        L.w("demo shared: $sharedElementEnterTransition")
        L.w("demo exit: $exitTransition")
        L.w("demo exit: $exitTransition")

        singleItems.add(object : SingleItem() {
            override fun getItemLayoutId(): Int {
                return R.layout.demo_transition
            }

            override fun onBindView(holder: RBaseViewHolder, posInData: Int, itemDataBean: Item?) {
                ViewCompat.setTransitionName(holder.view(R.id.image_view), "image")
                ViewCompat.setTransitionName(holder.view(R.id.button), "button")

                holder.click(R.id.image_view) {
                    holder.clickView(R.id.button)
                }

                holder.click(R.id.button) {

                    parentFragmentManager()?.beginTransaction()?.apply {
                        addSharedElement(holder.view(R.id.image_view), "image")
//                        addSharedElement(holder.view(R.id.button), "button")
                        //setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        //setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        val transitionSet = TransitionSet()
//                        transitionSet.addTransition(ChangeBounds())
//                        transitionSet.addTransition(ChangeTransform())
//                        transitionSet.addTarget(viewHolder.view(R.id.image_view))
//                        enterTransition = Slide(Gravity.RIGHT)
//                        enterTransition = ChangeBounds()
//                        sharedElementEnterTransition = transitionSet

//                        exitTransition = Slide(Gravity.LEFT)
                        enterTransition = Slide(Gravity.RIGHT)

                        //只能使用replace方法
                        replace(R.id.fragment_layout, TransitionFragment().apply {
                            setEnterSharedElementCallback(object : SharedElementCallback() {
                                override fun onRejectSharedElements(rejectedSharedElements: MutableList<View>?) {
                                    super.onRejectSharedElements(rejectedSharedElements)
                                }

                                override fun onSharedElementEnd(
                                    sharedElementNames: MutableList<String>?,
                                    sharedElements: MutableList<View>?,
                                    sharedElementSnapshots: MutableList<View>?
                                ) {
                                    super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                                }

                                override fun onCaptureSharedElementSnapshot(
                                    sharedElement: View?,
                                    viewToGlobalMatrix: Matrix?,
                                    screenBounds: RectF?
                                ): Parcelable {
                                    return super.onCaptureSharedElementSnapshot(
                                        sharedElement,
                                        viewToGlobalMatrix,
                                        screenBounds
                                    )
                                }

                                override fun onSharedElementsArrived(
                                    sharedElementNames: MutableList<String>?,
                                    sharedElements: MutableList<View>?,
                                    listener: OnSharedElementsReadyListener?
                                ) {
                                    super.onSharedElementsArrived(sharedElementNames, sharedElements, listener)
                                }

                                override fun onMapSharedElements(
                                    names: MutableList<String>?,
                                    sharedElements: MutableMap<String, View>?
                                ) {
                                    super.onMapSharedElements(names, sharedElements)
                                }

                                override fun onCreateSnapshotView(context: Context?, snapshot: Parcelable?): View {
                                    return super.onCreateSnapshotView(context, snapshot)
                                }

                                override fun onSharedElementStart(
                                    sharedElementNames: MutableList<String>?,
                                    sharedElements: MutableList<View>?,
                                    sharedElementSnapshots: MutableList<View>?
                                ) {
                                    super.onSharedElementStart(
                                        sharedElementNames,
                                        sharedElements,
                                        sharedElementSnapshots
                                    )
                                }
                            })


                            //                            sharedElementEnterTransition = TransitionInflater.from(this@TransitionDemo.mAttachContext)
//                                .inflateTransition(android.R.transition.move)

                            /**
                             *
                             * TransitionSet@2019c28:
                            ChangeBounds@5cf027:
                            ChangeTransform@de852d4:
                            ChangeClipBounds@9200c7d:
                            ChangeImageTransform@c219772:
                             * */
                            val inflateTransition = TransitionInflater.from(this@TransitionDemo.mAttachContext)
                                .inflateTransition(android.R.transition.move)

                            sharedElementEnterTransition = object : Transition() {

                                val changeBounds = ChangeBounds()

                                override fun captureStartValues(startValues: TransitionValues) {
                                    val view = holder.view(R.id.image_view)
                                    L.i(startValues)

                                    changeBounds.captureStartValues(startValues)
                                }

                                override fun captureEndValues(endValues: TransitionValues) {
                                    val view = holder.view(R.id.image_view)
                                    L.i(endValues)

                                    changeBounds.captureEndValues(endValues)
                                }

                                override fun createAnimator(
                                    sceneRoot: ViewGroup,
                                    startValues: TransitionValues?,
                                    endValues: TransitionValues?
                                ): Animator? {
                                    return changeBounds.createAnimator(sceneRoot, startValues, endValues)
                                }

                                override fun getTransitionProperties(): Array<String>? {
                                    return changeBounds.getTransitionProperties()
                                }

                            }
                        }, TransitionFragment::class.java.simpleName)
                        commitNow()
                    }
                }
            }
        })
    }
}