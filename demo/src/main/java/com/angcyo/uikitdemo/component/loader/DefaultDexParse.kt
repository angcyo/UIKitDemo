package com.angcyo.uikitdemo.component.loader

import android.text.TextUtils
import androidx.collection.ArrayMap
import com.angcyo.lib.L
import com.angcyo.uiview.less.kotlin.each
import com.angcyo.uiview.less.kotlin.orDefault
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class DefaultDexParse : IDexParse {

    internal val dexConfigMap = ArrayMap<String, DexConfig>()
    internal var dexPath: String? = null
    internal var dexDisableClass: String? = null
    internal var dexClass: String? = null

    override fun parseConfig(folderFile: File, dexConfig: DexConfig) {
        if (dexConfig.dexEnable) {
            dexConfigMap[folderFile.absolutePath] = dexConfig
        }
    }

    override fun onParseConfigEnd() {
        val dexPathBuilder = StringBuilder()
        val dexClassBuilder = StringBuilder()
        val dexDisableClassBuilder = StringBuilder()

        dexConfigMap.each { folderFile, dexConfig ->
            //遍历所有有效的dex文件
            if (dexConfig.dexEnable) {
                //dex文件
                fun appendString(builder: StringBuilder, value: String?) {
                    if (TextUtils.isEmpty(value)) {
                        return
                    }
                    if (builder.isNotEmpty()) {
                        builder.append(File.pathSeparator)
                    }
                    builder.append(value)
                }

                dexConfig.dexNames?.split(File.pathSeparator)?.forEach { name ->
                    if (!TextUtils.isEmpty(name)) {
                        val dexFile = File(folderFile, name)
                        if (dexFile.exists() && dexFile.canRead()) {
                            appendString(dexPathBuilder, dexFile.absolutePath)
                        }
                    }
                }

                appendString(dexClassBuilder, dexConfig.dexClass)
                appendString(dexDisableClassBuilder, dexConfig.dexDisableClass)
            }
        }
        dexDisableClass = dexDisableClassBuilder.toString()
        dexClass = dexClassBuilder.toString()
        dexPath = dexPathBuilder.toString()

        L.i(
            "解析结果->" +
                    "\ndex文件路径:$dexPath" +
                    "\n开放类:${dexClass.orDefault("全部")}" +
                    "\n禁止类:${dexDisableClass.orDefault("暂无")}"
        )
    }

    override fun getAllDexPath(): String {
        return dexPath ?: ""
    }

    override fun canLoadClass(clz: String): Boolean {
        return if (TextUtils.isEmpty(dexClass)) {
            dexDisableClass?.contains(clz) == false
        } else {
            dexClass?.contains(clz) == true && dexDisableClass?.contains(clz) == false
        }
    }
}