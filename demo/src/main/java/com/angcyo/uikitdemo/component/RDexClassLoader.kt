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

    fun createObject(clsName: String): Any? {
        val clazz = try {
            Class.forName(clsName, true, this)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return clazz?.newInstance()
    }

    fun invokeWith(clsName: String, doIt: Any.() -> Unit) {
        createObject(clsName)?.doIt()
    }

    fun invokeMethod(
        obj: Any, methodName: String,
        methodParamTypes: Array<Class<*>>? = null, vararg args: Any
    ): Any? {
        return ReflectUtils.invokeMethod(
            this,
            obj.javaClass.name,
            methodName,
            obj,
            methodParamTypes,
            *args
        )
    }
}