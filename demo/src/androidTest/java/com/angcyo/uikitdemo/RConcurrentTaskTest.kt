package com.angcyo.uikitdemo

import android.os.SystemClock
import com.angcyo.lib.L
import com.angcyo.uiview.less.kotlin.nowTimeString
import com.angcyo.uiview.less.utils.RConcurrentTask
import org.junit.Test
import java.util.concurrent.ConcurrentLinkedQueue

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/02
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RConcurrentTaskTest {
    @Test
    fun testTask() {
        val taskQueue = ConcurrentLinkedQueue<Runnable>()

        val result = Array(10) {
            ""
        }
        taskQueue.add(TaskRunnable(result, 0))
        taskQueue.add(TaskRunnable(result, 1))
        taskQueue.add(TaskRunnable(result, 2))
        taskQueue.add(TaskRunnable(result, 3))
        taskQueue.add(TaskRunnable(result, 4))
        taskQueue.add(TaskRunnable(result, 5))
        taskQueue.add(TaskRunnable(result, 6))
        taskQueue.add(TaskRunnable(result, 7))
        taskQueue.add(TaskRunnable(result, 8))
        taskQueue.add(TaskRunnable(result, 9))

        RConcurrentTask(taskQueue, 3) {
            L.i("end...")
            result.forEachIndexed { index, s ->
                L.i("$index $s")
            }
        }

        SystemClock.sleep(40_000)
    }
}

class TaskRunnable(val array: Array<String>, val index: Int) : Runnable {
    override fun run() {
        L.i("任务$index 执行中...")
        array[index] = "${Thread.currentThread().name} task$index ${nowTimeString()}"
        SystemClock.sleep(1000)
    }
}