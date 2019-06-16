package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import com.angcyo.uikitdemo.R
import com.angcyo.uikitdemo.ui.base.AppBaseDslRecyclerFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/06/16
 */
class RegularPatternDemo : AppBaseDslRecyclerFragment() {
    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        baseDslAdapter.apply {
            renderItem {
                itemLayoutId = R.layout.demo_regular_pattern_layout

                itemBind = { itemHolder, _, _ ->
                    itemHolder.auto(
                        R.id.pattern_edit,
                        mutableListOf(
                            PATTERN_MOBILE_SIMPLE,
                            PATTERN_MOBILE_EXACT,
                            PATTERN_TEL,
                            PATTERN_EMAIL,
                            PATTERN_URL
                        ),
                        true
                    )

                    itemHolder.ev(R.id.content_edit).setInputText("13678953476")

                    itemHolder.click(R.id.matcher_button) {
                        val regexList = if (itemHolder.ev(R.id.pattern_edit).isEmpty()) {
                            patternTelAndMobile()
                        } else {
                            mutableSetOf(itemHolder.ev(R.id.pattern_edit).string())
                        }

                        val contentString = itemHolder.ev(R.id.content_edit).string()
                        val pattern = contentString.pattern(regexList, false)

                        itemHolder.tv(R.id.text_view).text = RSpan
                            .get("结果:$pattern")
                            .appendLine().apply {
                                contentString.patternList(regexList.first()).forEach {
                                    append(it)
                                    appendLine()
                                }
                            }
                            .append("--")
                            .create()
                    }
                }
            }
        }
    }
}