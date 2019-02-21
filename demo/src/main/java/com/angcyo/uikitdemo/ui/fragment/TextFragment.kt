package com.angcyo.uikitdemo.ui.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.ViewGroup
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseFragment
import com.angcyo.uiview.less.utils.RUtils
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/02/21
 */
class TextFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_text
    }

    companion object {
        val RANDOM = Random(SystemClock.elapsedRealtime())

        fun get(text: String): TextFragment {
            return TextFragment().apply {
                arguments = Bundle().apply {
                    putString("text", text)
                }
            }
        }
    }

    override fun onPostCreateView(container: ViewGroup?, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onPostCreateView(container, arguments, savedInstanceState)
        baseViewHolder.itemView.setBackgroundColor(RUtils.randomColor(RANDOM))
        baseViewHolder.tv(R.id.text_view).text = arguments?.getString("text") ?: ""
    }
}