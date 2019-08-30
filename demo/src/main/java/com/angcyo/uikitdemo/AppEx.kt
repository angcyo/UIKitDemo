package com.angcyo.uikitdemo

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019-7-23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object AppEx {
    val PAGE_SIZE = 20
}

public fun isRelease() = BuildConfig.BUILD_TYPE.equals("release", true)
public fun isDebug() = BuildConfig.BUILD_TYPE.equals("debug", true)