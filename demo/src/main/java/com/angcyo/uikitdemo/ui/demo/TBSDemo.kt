package com.angcyo.uikitdemo.ui.demo

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import com.angcyo.rtbs.X5WebView
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.recycler.RBaseViewHolder

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class TBSDemo : BaseTitleFragment() {

    var webView: X5WebView? = null

    val DEFAULT_URL = "http://www.baidu.com"

    override fun getContentLayoutId(): Int {
        return R.layout.item_tbs_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.group(R.id.layout).apply {
            if (childCount <= 2) {
                webView = X5WebView(mAttachContext)
                addView(webView, LinearLayout.LayoutParams(-1, 0, 1f))
                //addView(webView, LinearLayout.LayoutParams(-1, 0, 1f))

                webView?.loadUrl(DEFAULT_URL)

                viewHolder.exV(R.id.url_edit).setInputText(DEFAULT_URL)
            }
        }

        viewHolder.click(R.id.go) {
            webView?.loadUrl(viewHolder.exV(R.id.url_edit).string())
        }
    }


    override fun onFragmentShow(bundle: Bundle?) {
        super.onFragmentShow(bundle)
        webView?.onResume()
        webView?.resumeTimers()
        // mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    override fun onFragmentHide() {
        super.onFragmentHide()
        webView?.onPause()
        webView?.pauseTimers()
        //mActivity.getWindow().setSoftInputMode(mSoftInputMode);
    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.destroy()
    }

    override fun onBackPressed(activity: Activity): Boolean {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
//            if (webView?.canGoBack() == true) {
//                leftControl().selector(com.angcyo.rtbs.R.id.base_title_close_view).visible()
//            } else {
//                leftControl().selector(com.angcyo.rtbs.R.id.base_title_close_view).gone()
//            }
            return false
        }
        return true
    }
}