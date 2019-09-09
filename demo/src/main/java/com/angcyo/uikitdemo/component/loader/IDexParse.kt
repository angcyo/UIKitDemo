package com.angcyo.uikitdemo.component.loader

import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
interface IDexParse {
    fun parseConfig(folderFile: File, dexConfig: DexConfig)

    fun onParseConfigEnd()

    /**
     * 返回所有有效的dex文件路径, 多个路径使用[java.io.File.pathSeparator]分割
     * */
    fun getAllDexPath(): String

    /**
     * 是否允许加载类[clz]
     * */
    fun canLoadClass(clz: String): Boolean
}