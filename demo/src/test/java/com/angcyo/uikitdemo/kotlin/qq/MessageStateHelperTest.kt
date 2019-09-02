package com.angcyo.uikitdemo.kotlin.qq

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/09/02
 */
class MessageStateHelperTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testState() {
        println("${Thread.currentThread().name} ....test")
        println("....test2")
    }
}