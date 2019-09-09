package com.angcyo.uikitdemo.component.loader

import com.angcyo.uiview.less.kotlin.fromJson
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
open class DefaultDexConfigFactory : IDexConfigFactory {
    val configFileName = "config.json"
    val defaultDexFileSuffix = ".r.dex"

    override fun loadConfig(dexFolder: String): DexConfig? {
        var config: DexConfig? = null

        val folderFile = File(dexFolder)
        if (folderFile.isDirectory && folderFile.canRead()) {
            val configFile = File(dexFolder, configFileName)
            if (configFile.isFile && configFile.canRead()) {
                //有配置文件
                config = configFile.readText().fromJson(DexConfig::class.java)
            } else if (!configFile.exists()) {
                //无配置文件
                //加载所有dex文件

                folderFile.listFiles().forEach {
                    if (it.isFile && it.canRead() && it.name?.endsWith(defaultDexFileSuffix) == true) {
                        config = DexConfig(dexNames = it.name)
                    }
                }

            }
        }

        return config
    }
}