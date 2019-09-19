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

    init {
        //默认值
        "pattern_edit".hawkPutList(PATTERN_MOBILE_SIMPLE, false)
        "pattern_edit".hawkPutList(PATTERN_MOBILE_EXACT, false)
        "pattern_edit".hawkPutList(PATTERN_TEL, false)
        "pattern_edit".hawkPutList(PATTERN_EMAIL, false)
        "pattern_edit".hawkPutList(PATTERN_URL, false)
        "pattern_edit".hawkPutList("(?<=compile).*(?=JavaWithJavac)", false)

        "content_edit".hawkPutList("13678953476", false)
        "content_edit".hawkPutList("compile_devDebugJavaWithJavac", false)
    }

    override fun onInitBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)
        baseDslAdapter.apply {
            renderItem {
                itemLayoutId = R.layout.demo_regular_pattern_layout

                itemBind = { itemHolder, _, _ ->
                    itemHolder.auto(R.id.pattern_edit, "pattern_edit".hawkGetList(), true)
                        ?.setInputText("pattern_edit".hawkGetList().first())
                    itemHolder.auto(R.id.content_edit, "content_edit".hawkGetList(), true)
                        ?.setInputText("content_edit".hawkGetList().first())

                    itemHolder.click(R.id.matcher_button) {
                        val regexList = if (itemHolder.ev(R.id.pattern_edit).isEmpty()) {
                            patternTelAndMobile()
                        } else {
                            mutableSetOf(itemHolder.ev(R.id.pattern_edit).string())
                        }

                        val contentString = itemHolder.ev(R.id.content_edit).string()
                        val pattern = contentString.pattern(regexList, false)

                        "pattern_edit".hawkPutList(regexList.first())
                        "content_edit".hawkPutList(contentString)

                        itemHolder.tv(R.id.text_view).text = RSpan
                            .get("匹配结果:$pattern")
                            .appendLine().apply {
                                contentString.patternList(regexList.first()).forEach {
                                    append("matcher.find()->matcher.group()")
                                    appendLine()
                                    append(it)
                                    appendLine()
                                }
                            }
                            .append("--end")
                            .create()
                    }
                }
            }
        }
    }
}