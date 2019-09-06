package com.angcyo.uikitdemo.component

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

class RDexClassLoader(
    dexPath: String,
    optimizedDirectory: String,
    librarySearchPath: String,
    parent: ClassLoader
) : DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent) {

    companion object {
        const val R_DEX_PATH = "r_dex"

        /**
         * 构建 [RDexClassLoader]
         * @param dexPath 多个路径, 请使用 [java.io.File.pathSeparator] ":" 分隔
         * */
        fun create(
            context: Context,
            dexPath: String,
            parent: ClassLoader = context.classLoader
        ): RDexClassLoader {
            val loader: RDexClassLoader

            val outDir = context.getDir(R_DEX_PATH, 0)

            val soDir = with(context) {
                packageManager
                    .getApplicationInfo(packageName, 0)
                    .nativeLibraryDir
            }

            loader = RDexClassLoader(dexPath, outDir.absolutePath, soDir, parent)

            L.i("dex load->$dexPath\n$outDir\n$soDir")
            return loader
        }
    }

    /**创建对象*/
    fun <T> createObject(clsName: String): T? {
        val clazz = try {
            Class.forName(clsName, true, this)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return clazz?.newInstance() as? T
    }

    /**获取成员变量*/
    fun <T> readField(obj: Any, fieldName: String): T? {
        return ReflectUtils.readField(obj.javaClass, obj, fieldName) as? T
    }

    /**设置成员变量*/
    fun writeField(obj: Any, fieldName: String, value: Any? = null) {
        ReflectUtils.writeField(obj.javaClass, obj, fieldName, value)
    }

    fun <T> invokeWith(clsName: String, doIt: T.() -> Unit) {
        createObject<T>(clsName)?.doIt()
    }

    /**调用成员方法*/
    fun <T> invokeMethod(
        obj: Any, methodName: String,
        methodParamTypes: Array<Class<*>>? = null, vararg args: Any
    ): T? {
        return ReflectUtils.invokeMethod(
            this,
            obj.javaClass.name,
            methodName,
            obj,
            methodParamTypes,
            *args
        ) as? T
    }
}