package com.angcyo.uikitdemo.component.loader

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
data class DexConfig(
    /**
     * dex 文件名, 多个文件名使用 [java.io.File.pathSeparator] 分割
     * 必填项
     * */
    var dexNames: String? = null,

    /**
     * dex 文件包含的类名, 多个类用 [java.io.File.pathSeparator] 分割
     * 开放的类, 空表示开放所有类
     * */
    var dexClass: String? = null,

    /**
     * 是否启用此dex文件
     * false 则不会初始化dex文件
     * */
    var dexEnable: Boolean = true,

    /**
     * 单独禁用此dex文件中的指定类,多个类用 [java.io.File.pathSeparator]  分割
     * */
    var dexDisableClass: String? = null,

    /**
     * 版本号, 备用
     * */
    var dexVersion: Int = 1,

    /**
     * 备用
     * */
    var dexUUID: String? = null
)