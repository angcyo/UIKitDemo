package com.angcyo.uikitdemo.component.loader

import android.content.Context
import androidx.annotation.UiThread
import com.angcyo.http.Rx
import com.angcyo.lib.L
import com.angcyo.uiview.less.utils.Root
import rx.Subscription
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 外部dex, 加载管理类
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/09
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object RDex {

    var configFactory: IDexConfigFactory = DefaultDexConfigFactory()
    var dexParse: IDexParse = DefaultDexParse()

    private val observers = mutableSetOf<IDexObserver>()

    lateinit var dexClassLoader: RDexClassLoader
    lateinit var appClassLoader: ClassLoader

    private var parseSubscription: Subscription? = null
    private var parseStateAtomicBoolean: AtomicBoolean? = null

    /**
     * 初始化
     * @param folder 外部dex所在的文件夹, 多个路径使用 [java.io.File.pathSeparator]
     * */
    fun init(
        context: Context,
        folder: String = Root.getAppExternalFolder("dex")
    ) {
        if (parseStateAtomicBoolean == null) {
            parseStateAtomicBoolean = AtomicBoolean(true)
        } else {
            parseStateAtomicBoolean?.set(true)
        }

        appClassLoader = context.classLoader
        parseSubscription?.unsubscribe()

        Rx.onMain {
            observers.forEach {
                it.onParseConfigStart(this)
            }
        }

        parseSubscription = Rx.back {
            loadInner(folder)
            dexParse.onParseConfigEnd()

            dexClassLoader = RDexClassLoader.create(context, dexParse.getAllDexPath(), null)
            parseSubscription = null

            parseStateAtomicBoolean?.set(false)
            Rx.onMain {
                observers.forEach {
                    it.onParseConfigEnd(this)
                }
            }
        }
    }

    fun isParseEnd(): Boolean =
        parseStateAtomicBoolean != null && parseStateAtomicBoolean?.get() == false

    private fun loadInner(folder: String, depth: Int = 0 /*路径深度*/) {
        val folderFile = File(folder)
        if (folderFile.isDirectory && folderFile.canRead()) {
            configFactory.loadConfig(folder)?.apply {
                dexParse.parseConfig(folderFile, this)
            }
            folderFile.listFiles().forEach {
                if (depth < 2 && it.isDirectory && it.canRead()) {
                    loadInner(it.absolutePath, depth + 1)
                }
            }
        }
    }

    fun observer(observer: IDexObserver) {
        observers.add(observer)

        if (isParseEnd()) {
            Rx.onMain {
                observers.forEach {
                    it.onParseConfigEnd(this)
                }
            }
        }
    }

    fun unObserver(observer: IDexObserver) {
        observers.remove(observer)
    }

    /**
     * 创建对象
     * @param clsName 需要创建对象类名, 需要提供一个无参数的构造方法
     * @param onlyHost 是否只加载宿主中的类
     * */
    fun <T> createObject(clsName: String, onlyHost: Boolean = false): T? {
        return loadClass(clsName, onlyHost)?.newInstance() as? T
    }

    fun <T> invokeWith(clsName: String, onlyHost: Boolean = false, doIt: T.() -> Unit): T? {
        return createObject<T>(clsName, onlyHost)?.apply {
            this.doIt()
        }
    }

    fun loadClass(clsName: String, onlyHost: Boolean = false): Class<*>? {
        val clazz = try {
            when {
                parseSubscription != null -> {
                    L.e("正在解析, 请稍等...")
                    null
                }
                onlyHost || !dexParse.canLoadClass(clsName) -> Class.forName(
                    clsName,
                    true,
                    appClassLoader
                )
                else -> Class.forName(clsName, true, dexClassLoader)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return clazz
    }
}

interface IDexObserver {
    @UiThread
    fun onParseConfigStart(rDex: RDex) {

    }

    @UiThread
    fun onParseConfigEnd(rDex: RDex) {

    }
}

public fun String?.loadClass(onlyHost: Boolean = false): Class<*>? =
    if (this == null) null else RDex.loadClass(this, onlyHost)

public fun <T> String?.createObject(onlyHost: Boolean = false): T? =
    if (this == null) null else RDex.createObject(this, onlyHost)

public fun <T> String?.invokeWith(onlyHost: Boolean = false, doIt: T.() -> Unit): T? =
    RDex.invokeWith(this ?: "", onlyHost, doIt)

