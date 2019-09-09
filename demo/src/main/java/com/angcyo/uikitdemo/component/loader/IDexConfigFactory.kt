package com.angcyo.uikitdemo.component.loader

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
interface IDexConfigFactory {

    /**
     * 从指定的文件夹中, 装在配置信息
     * @param dexFolder dex所在的文件夹
     * */
    fun loadConfig(dexFolder: String): DexConfig?
}