package com.angcyo.uikitdemo.ui.demo

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.angcyo.lib.L
import com.angcyo.okdownload.FDown
import com.angcyo.rtbs.X5WebView
import com.angcyo.rtbs.fragment.FilePreviewFragment
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.base.BaseTitleFragment
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.Root
import com.tencent.smtt.sdk.TbsReaderView


/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/04/15
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class TBSDemo : BaseTitleFragment() {

    var webView: X5WebView? = null

    var tbsReaderView: TbsReaderView? = null

    val DEFAULT_URL = "http://www.baidu.com"

    val DEFAULT_FILE_PATH = "/sdcard/word.doc"

    override fun getContentLayoutId(): Int {
        return R.layout.item_tbs_layout
    }

    override fun onInitBaseView(viewHolder: RBaseViewHolder, arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onInitBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.auto(
            R.id.file_edit,
            mutableListOf(
                "http://lc-orltc1ed.cn-n1.lcfile.com/a2b7a1c668e304cb7300/Effective%20Java%20%E4%B8%AD%E6%96%87%E7%AC%AC%E4%BA%8C%E7%89%88.pdf".decode(),
                "http://mfiles.sohu.com/news/shanghetuzzx/14.jpg".decode(),
                "http://mfiles.sohu.com/news/shanghetuzzx/15.jpg".decode(),
                "http://www.duwenzhang.com/wenzhang/aiqingwenzhang/shanggan/20190604/403685.html".decode()
            )
        )

        viewHolder.group(R.id.layout).apply {
            if (childCount <= 2) {
                webView = X5WebView(mAttachContext)

                addView(webView, LinearLayout.LayoutParams(-1, 0, 1f))

                webView?.loadUrl(DEFAULT_URL)

                viewHolder.eV(R.id.url_edit).setInputText(DEFAULT_URL)
                viewHolder.eV(R.id.file_edit).setInputText(DEFAULT_FILE_PATH)

                //openFile(this, DEFAULT_FILE_PATH)
            }
        }

        viewHolder.click(R.id.go) {
            webView?.loadUrl(viewHolder.eV(R.id.url_edit).string())
        }

        viewHolder.click(R.id.open_file) {
            openFile(viewHolder.group(R.id.layout), viewHolder.eV(R.id.file_edit).string())
        }

        viewHolder.click(R.id.mine_type) {
            (it as? TextView)?.text = viewHolder.eV(R.id.file_edit).string().mimeType()
        }

        viewHolder.click(R.id.open_file_new) {
            FilePreviewFragment.show(wtf()) {
                filePath = viewHolder.eV(R.id.file_edit).string()
            }
        }
        viewHolder.click(R.id.open_url_new) {
            FilePreviewFragment.openUrl(wtf()) {
                targetUrl = viewHolder.ev(R.id.url_edit).string()
            }
        }

        FDown.cancel(0)
        FDown.cancel(-1)
        FDown.cancel(-2)
        FDown.cancel("")
        FDown.cancel(null)
    }

    fun openFile(parent: ViewGroup, filePath: String) {
        if (parent.childCount > 3) {
            parent.removeViewAt(3)
        }
        tbsReaderView?.onStop()
        tbsReaderView = TbsReaderView(mAttachContext,
            TbsReaderView.ReaderCallback { p0, p1, p2 -> L.w("文件浏览服务日志输出:$p0 $p1 $p2") })
        parent.addView(tbsReaderView, LinearLayout.LayoutParams(-1, 0, 1f))
        openFile(filePath)
    }

    fun openFile(filePath: String) {
        if (tbsReaderView?.preOpen(filePath.extName(), false) == true) {
            val bundle = Bundle()
            bundle.putString("filePath", filePath)
            bundle.putString("tempPath", Root.sd())
            tbsReaderView?.openFile(bundle)
        } else {
            L.e("不支持文件:$filePath")
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
        tbsReaderView?.onStop()
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