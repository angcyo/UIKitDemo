package com.angcyo.uikitdemo.ui.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.angcyo.uiview.less.widget.behavior.BaseDependsBehavior

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/12/02
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

open class StickHeaderFollowBehavior(
    context: Context? = null,
    attrs: AttributeSet? = null
) : BaseDependsBehavior<View>(context, attrs), IStickHeaderFollowView {

}

interface IStickHeaderFollowView