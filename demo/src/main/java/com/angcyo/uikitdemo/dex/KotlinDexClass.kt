package com.angcyo.uikitdemo.dex

import com.angcyo.lib.L

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/05
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class KotlinDexClass {
    fun kotlinDex(): String {
        L.i("kotlin文件")
        return "kotlin文件, 方法:kotlinDex"
    }

    fun kotlinDex2(num: Int, string: String): String {
        L.i("kotlin文件")
        return "kotlin文件, 方法:kotlinDex2 参数:num:$num string:$string"
    }
}