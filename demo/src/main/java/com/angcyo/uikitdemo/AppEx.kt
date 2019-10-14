package com.angcyo.uikitdemo

import com.angcyo.objectbox.RBox
import io.objectbox.Box

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

public fun <T> boxOf(entityClass: Class<T>, init: Box<T>.() -> Unit = {}): Box<T> {
    val box = RBox.get(BuildConfig.APPLICATION_ID).boxFor(entityClass)
    box.init()
    return box
}