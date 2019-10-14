package com.angcyo.uikitdemo

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/10/14
 */

@Entity
data class PlaceholderEntity(
    @Id var entity_id: Long = 0L
)