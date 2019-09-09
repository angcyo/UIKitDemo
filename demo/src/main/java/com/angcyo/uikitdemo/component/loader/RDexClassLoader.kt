package com.angcyo.uikitdemo.component.loader

import android.content.Context
import com.angcyo.lib.L
import com.qihoo360.replugin.utils.ReflectUtils
import dalvik.system.DexClassLoader

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/06
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class RDexClassLoader internal constructor(
    dexPath: String,
    optimizedDirectory: String,
    librarySearchPath: String,
    parent: ClassLoader?
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {

    private var hostClassLoader: ClassLoader? = null

    companion object {
        const val R_DEX_PATH = "r_dex"

        /**
         * 构建 [RDexClassLoader]
         * @param dexPath 多个路径, 请使用 [java.io.File.pathSeparator] ":" 分隔
         * */
        fun create(
            context: Context,
            dexPath: String,
            parent: ClassLoader? = null
        ): RDexClassLoader {
            val loader: RDexClassLoader

            val outDir = context.getDir(R_DEX_PATH, 0)

            val soDir = with(context) {
                packageManager
                    .getApplicationInfo(packageName, 0)
                    .nativeLibraryDir
            }

            loader = RDexClassLoader(dexPath, outDir.absolutePath, soDir, parent)
            loader.hostClassLoader = context.classLoader

            L.i("DexLoad->\n$dexPath\n$outDir\n$soDir")
            return loader
        }
    }

    override fun findClass(name: String?): Class<*> {
        return super.findClass(name)
    }

    override fun loadClass(name: String?, resolve: Boolean): Class<*> {
        val loadClass = try {
            super.loadClass(name, resolve)
        } catch (e: ClassNotFoundException) {
            hostClassLoader?.loadClass(name) ?: throw e
        }
        return loadClass
    }
}

/**创建对象*/
public fun <T> ClassLoader.createObject(clsName: String): T? {
    val clazz = try {
        Class.forName(clsName, true, this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    return clazz?.newInstance() as? T
}

/**获取成员变量*/
public fun <T> Any.readField(fieldName: String): T? {
    return ReflectUtils.readField(javaClass, this, fieldName) as? T
}

/**设置成员变量*/
public fun Any.writeField(fieldName: String, value: Any? = null) {
    ReflectUtils.writeField(javaClass, this, fieldName, value)
}

public fun <T> ClassLoader.invokeWith(clsName: String, doIt: T.() -> Unit) {
    createObject<T>(clsName)?.doIt()
}

/**调用成员方法*/
public fun <T> Any.invokeMethod(
    methodName: String,
    methodParamTypes: Array<Class<*>>? = null, vararg args: Any
): T? {
    return try {
        ReflectUtils.invokeMethod(
            this.javaClass.classLoader,
            javaClass.name,
            methodName,
            this,
            methodParamTypes,
            *args
        ) as? T
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}