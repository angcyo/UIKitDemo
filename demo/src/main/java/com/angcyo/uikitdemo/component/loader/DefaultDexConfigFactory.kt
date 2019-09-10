package com.angcyo.uikitdemo.component.loader

import com.angcyo.lib.L
import com.angcyo.uiview.less.kotlin.fromJson
import com.angcyo.uiview.less.kotlin.unzip
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class DefaultDexConfigFactory : IDexConfigFactory {

    companion object {
        var DEFAULT_CONFIG_FILE_NAME = "config.json"
        var DEFAULT_DEX_FILE_SUFFIX = ".r.dex"
        var DEFAULT_DEX_ZIP_FILE_SUFFIX = ".r.zip"
    }

    /**配置文件名*/
    val configFileName = DEFAULT_CONFIG_FILE_NAME

    /**在没有配置文件的情况下, 默认此后缀的dex文件*/
    val defaultDexFileSuffix = DEFAULT_DEX_FILE_SUFFIX

    /**优先解压此格式的zip文件*/
    val defaultDexZipFileSuffix = DEFAULT_DEX_ZIP_FILE_SUFFIX

    override fun loadConfig(dexFolder: String): DexConfig? {
        var config: DexConfig? = null

        val folderFile = File(dexFolder)
        if (folderFile.isDirectory && folderFile.canRead()) {

            findZip(folderFile)

            val configFile = File(dexFolder, configFileName)
            if (configFile.isFile && configFile.canRead()) {
                //有配置文件
                config = configFile.readText().fromJson(DexConfig::class.java)
            } else if (!configFile.exists()) {
                //无配置文件
                //加载所有dex文件

                folderFile.listFiles().forEach {
                    if (isDefaultFile(it, defaultDexFileSuffix)) {
                        config = DexConfig(dexNames = it.name)
                    }
                }

            }
        }

        return config
    }

    protected fun isDefaultFile(file: File, suffix: String): Boolean {
        return file.isFile && file.canRead() && file.name?.endsWith(suffix) == true
    }

    /**查找并解压zip包*/
    protected fun findZip(folderFile: File) {
        folderFile.listFiles().forEach {
            if (isDefaultFile(it, defaultDexZipFileSuffix)) {
                L.i("解压zip包:${it.absolutePath}")
                it.unzip()
            }
        }
    }
}