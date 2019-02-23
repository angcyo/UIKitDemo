package com.angcyo.uikitdemo.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import com.angcyo.lib.L
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseTitleFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/23
 */
class TransitionFragment : AppBaseTitleFragment() {
    override fun getContentLayoutId(): Int {
        return R.layout.fragment_transition
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition = TransitionInflater.from(context)
//            .inflateTransition(android.R.transition.move)

    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        ViewCompat.setTransitionName(viewHolder.view(R.id.image_view), "image")
        ViewCompat.setTransitionName(viewHolder.view(R.id.button), "button")

//        val transitionSet = TransitionSet()
//        transitionSet.addTransition(ChangeBounds())
//        transitionSet.addTransition(ChangeTransform())
//        transitionSet.addTarget(viewHolder.view(R.id.image_view))
////        //enterTransition = Slide(Gravity.RIGHT)
////        //enterTransition = ChangeBounds()
//        sharedElementEnterTransition = transitionSet

//        startPostponedEnterTransition()
    }

    override fun onPostCreateView(container: ViewGroup?, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onPostCreateView(container, arguments, savedInstanceState)
//        ViewCompat.setTransitionName(baseViewHolder.view(R.id.image_view), "image")
//        ViewCompat.setTransitionName(baseViewHolder.view(R.id.button), "button")
    }

    override fun onTitleBackClick(view: View?) {
        super.onTitleBackClick(view)
    }

    override fun onBackPressed(activity: Activity): Boolean {
        L.w("shared: $sharedElementEnterTransition")
        L.w("enter: $enterTransition")
        L.w("exit: $exitTransition")
        return super.onBackPressed(activity)
    }
}