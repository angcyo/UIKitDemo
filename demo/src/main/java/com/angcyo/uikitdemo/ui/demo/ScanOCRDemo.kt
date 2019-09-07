package com.angcyo.uikitdemo.ui.demo

import android.os.Bundle
import android.widget.RadioGroup
import com.angcyo.http.Rx
import com.angcyo.lib.L
import com.angcyo.rcode.CodeScanFragment
import com.angcyo.rcode.scan.FrameDataDecode
import com.angcyo.tesstwo.NetOcr
import com.angcyo.uikitdemo.R
import com.angcyo.uiview.less.kotlin.*
import com.angcyo.uiview.less.recycler.RBaseViewHolder
import com.angcyo.uiview.less.utils.RDialog
import com.google.gson.JsonObject

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/07
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class ScanOCRDemo : CodeScanFragment() {
    override fun getLayoutId(): Int {
        return R.layout.demo_scan_ocr_layout
    }

    private val KEY_DATA_SAVE = "key_data_save"

    private val netOcr = NetOcr()
    private var netOcrType = 1 //1:百度 2:阿里云
    private val SQL =
        "UPDATE asset set Barcode = '%1' where CorporationId = 100097 and `Name` = '%2'"

    private var lastData: ByteArray? = null
    override fun initBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.initBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.click(R.id.save_button) {
            val baiduApiKey =
                viewHolder.tv(R.id.baidu_api_key).string().orDefault(NetOcr.BAIDU_API_KEY)
            val baiduSecretKey =
                viewHolder.tv(R.id.baidu_secret_key).string().orDefault(NetOcr.BAIDU_SECRET_KEY)
            val appCode = viewHolder.tv(R.id.ali_app_code).string().orDefault(NetOcr.ALI_APPCODE)

            if (baiduApiKey != NetOcr.BAIDU_API_KEY || baiduSecretKey != NetOcr.BAIDU_SECRET_KEY) {
                netOcr.resetBaiduApi(baiduApiKey.toString(), baiduSecretKey.toString())
            }

            NetOcr.ALI_APPCODE = "$appCode"

            if (codeResult.isNotEmpty() && ocrResult.isNotEmpty()) {
                KEY_DATA_SAVE.hawkAppend("${baseViewHolder.tv(R.id.sql_edit).text};\n")
            }
        }

        viewHolder.click(R.id.next_button) {
            showHandleOcr("")
            showHandleCode("")
            scanAgain()
        }

        viewHolder.click(R.id.clear_button) {
            KEY_DATA_SAVE.hawkDelete()
        }

        viewHolder.click(R.id.copy_button) {
            KEY_DATA_SAVE.hawkGet()?.apply {
                copy()
                share(mAttachContext)
            }
        }

        viewHolder.click(R.id.result_text_view2) {
            lastData?.run { FrameDataDecode.iFrameDataDecode.onFrameDataDecode(this) }
        }

        viewHolder.v<RadioGroup>(R.id.group).setOnCheckedChangeListener { _, checkedId ->
            netOcrType = if (checkedId == R.id.ali_api) 2 else 1
        }

        //netOcr.ocrBaidu()
        FrameDataDecode.iFrameDataDecode = FrameDataDecode.IFrameDataDecode { data ->
            lastData = data
            RDialog.flow(mAttachContext)
            if (netOcrType == 1) {
                netOcr.ocrBaiduBasic(data) {
                    it.fromJson(JsonObject::class.java)?.getArray("words_result")?.forEach {
                        it.getString("words")?.replace(" ", "")?.apply {
                            if (this.length >= 9 && this.pattern("^[a-zA-Z0-9]+\$")) {
                                L.w("识别到:$this")
                                Rx.onMain {
                                    showHandleOcr(this, true)
                                }
                            }
                        }
                    }
                    RDialog.hide()
                    null
                }
            } else {
                netOcr.ocrAliyun(data) {
                    it.fromJson(JsonObject::class.java)?.getArray("prism_wordsInfo")?.forEach {
                        it.getString("word")?.replace(" ", "")?.apply {
                            if (this.length >= 9 && this.pattern("^[a-zA-Z0-9]+\$")) {
                                L.w("识别到:$this")
                                Rx.onMain {
                                    showHandleOcr(this, true)
                                }
                            }
                        }
                    }
                    RDialog.hide()
                    null
                }
            }
        }
    }

    private var codeResult: String = ""
    private var ocrResult: String = ""

    override fun onHandleDecode(data: String) {
        //super.onHandleDecode(data)
        try {
            baseViewHolder.tv(R.id.code_view).text = data
            showHandleCode(data.split("=")[1])
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showHandleCode(data: String) {
        codeResult = data
        baseViewHolder.tv(R.id.result_text_view1).text = "扫码结果:${data}"
    }

    private fun showHandleOcr(data: String, replaceSql: Boolean = false) {
        ocrResult = data
        baseViewHolder.tv(R.id.result_text_view2).text = "OCR结果:${data}"

        if (replaceSql) {
            baseViewHolder.tv(R.id.sql_edit).text =
                SQL.replace("%1", codeResult).replace("%2", ocrResult)
        }
    }
}