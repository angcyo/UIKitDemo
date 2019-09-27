package com.angcyo.uikitdemo.component

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA
import android.nfc.tech.NfcF
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.angcyo.lib.L
import com.angcyo.uiview.less.kotlin.app

/**
 *
 * Email:angcyo@126.com
 *
 *  <uses-permission android:name="android.permission.NFC" />
 *
 * @author angcyo
 * @date 2019/09/26
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object RNfc {

    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(app())
    }

    /**
     * 返回NFC是否可用, 不可用, 弹出设置界面
     * */
    fun checkNfc(activity: Activity): Boolean {
        return if (isNfcEnable()) {
            true
        } else {
            startNfcSetting(activity)
            false
        }
    }

    fun startNfcSetting(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
        } else {
            activity.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }
    }

    /**
     * 是否支持NFC
     * */
    fun isNfcSupport(): Boolean {
        return nfcAdapter != null
    }

    /**是否激活了NFC*/
    fun isNfcEnable(): Boolean {
        return isNfcSupport() && nfcAdapter?.isEnabled == true
    }

    /**获取NFC标签*/
    fun getNfcTag(context: Context?, intent: Intent?): Tag? {
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent?.action) {
            return getNfcTag(context, intent.extras)
        }
        return null
    }

    fun getNfcTag(context: Context?, bundle: Bundle?): Tag? {
        if (context == null || bundle == null) {
            return null
        }
        if (bundle.containsKey(NfcAdapter.EXTRA_TAG)) {
            return bundle.getParcelable(NfcAdapter.EXTRA_TAG)
        }
        return null
    }

    /**启动NFC前台派发, 当发现标签的时候, 会触发[Intent]*/
    fun enableNfcForegroundDispatch(activity: Activity, pendingIntent: PendingIntent) {
        if (isNfcEnable()) {
            nfcAdapter?.enableForegroundDispatch(
                activity,
                pendingIntent,
                null,
                arrayOf(
                    arrayOf(NfcA::class.java.name),
                    arrayOf(NfcF::class.java.name),
                    arrayOf(MifareClassic::class.java.name)
                )
            )
        }
    }

    /**启动目标[Activity]*/
    fun enableNfcForegroundDispatch(activity: Activity, targetActivity: Class<*>? = null) {
        if (isNfcEnable()) {
            val intent = Intent(activity, targetActivity ?: activity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
            val pendingIntent = PendingIntent.getActivity(
                activity, 0, intent, 0
            )
            enableNfcForegroundDispatch(activity, pendingIntent)
        }
    }

    /**关闭派发*/
    fun disableNfcForegroundDispatch(activity: Activity) {
        if (isNfcEnable()) {
            nfcAdapter?.disableForegroundDispatch(activity)
        }
    }

    internal fun byte2HexString(bytes: ByteArray?): String {
        val ret = StringBuilder()
        if (bytes != null) {
            for (b in bytes) {
                ret.append(String.format("%02X", b.toInt() and 0xFF))
            }
        }
        return ret.toString()
    }
}

fun ByteArray?.toHexString(): String = RNfc.byte2HexString(this)

fun Tag?.toLog(): String {
    val tag = this
    return buildString {
        if (tag == null) {
            append("null")
        } else {
            append("NFC标签信息:uid:")
            appendln(tag.id.toHexString())
            appendln(tag.id.toHexString().toLong(16))
            tag.techList.forEachIndexed { index, s ->
                if (index != tag.techList.size - 1) {
                    appendln(s)
                } else {
                    append(s)
                }
            }
        }
        L.i(this)
    }
}