package com.angcyo.uikitdemo.component.dsl

import com.angcyo.uiview.less.recycler.adapter.DslAdapter
import com.angcyo.uiview.less.recycler.dslitem.dslCustomItem

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

/**语音播放展示item*/
public fun DslAdapter.dslRecordVoiceItem(config: DslRecordVoiceItem.() -> Unit = {}) {
    dslCustomItem(DslRecordVoiceItem(), config)
}