package com.angcyo.uikitdemo.component

import android.content.ContentResolver
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.SystemClock
import android.provider.MediaStore
import com.angcyo.lib.L
import com.angcyo.uiview.less.kotlin.fileSize
import com.angcyo.uiview.less.kotlin.fileSizeString
import kotlin.math.abs

/**
 * 截图监听
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/08/16
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class RScreenshotObserver {

    companion object {
        private val KEYWORDS = arrayOf(
            "screenshot",
            "screen_shot",
            "screen-shot",
            "screen shot",
            "screencapture",
            "screen_capture",
            "screen-capture",
            "screen capture",
            "screencap",
            "screen_cap",
            "screen-cap",
            "screen cap"
        )

        /** 读取媒体数据库时需要读取的列  */
        private val MEDIA_PROJECTIONS =
            arrayOf(
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.DATE_ADDED
            )
    }

    private var handlerThread: HandlerThread = HandlerThread("RScreenshotObserver")
    private var handler: Handler
    private var mainHandler = Handler(Looper.getMainLooper())

    private var internalObserver: ContentObserver
    private var externalObserver: ContentObserver

    private var isRegister = false

    private var contentResolver: ContentResolver? = null

    /**主线程回调*/
    private var onScreenshotCallback: (String) -> Unit = {}

    init {
        handlerThread.start()

        handler = Handler(handlerThread.looper)

        // 初始化
        internalObserver =
            MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, handler)
        externalObserver =
            MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, handler)
    }

    /**观察*/
    fun observe(contentResolver: ContentResolver, callback: (filePath: String) -> Unit = {}) {
        if (!isRegister) {
            isRegister = true

            // 添加监听
            contentResolver.registerContentObserver(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                false,
                internalObserver
            )
            contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                false,
                externalObserver
            )

            onScreenshotCallback = callback
            this.contentResolver = contentResolver
        }
    }

    /**取消观察*/
    fun unObserve() {
        onScreenshotCallback = {}
        if (isRegister) {
            contentResolver?.unregisterContentObserver(internalObserver)
            contentResolver?.unregisterContentObserver(externalObserver)

            isRegister = false
        }
    }

    /**释放资源*/
    fun release() {
        unObserve()
        handlerThread.quitSafely()
    }

    private inner class MediaContentObserver(val uri: Uri, val handler: Handler) :
        ContentObserver(handler) {
        override fun onChange(selfChange: Boolean) {
            handleChange()
        }

        private fun handleChange() {
            var cursor: Cursor? = null

            try {
                // 数据改变时查询数据库中最后加入的一条数据
                cursor = contentResolver?.query(
                    uri,
                    MEDIA_PROJECTIONS, null, null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " desc limit 1"
                )

                if (cursor == null) {
                    return
                }

                if (!cursor.moveToFirst()) {
                    return
                }

                // 获取各列的索引
                val dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                val dateTakenIndex =
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)
                val dateAddIndex =
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)

                // 获取行数据
                val data =
                    cursor.getString(dataIndex) // /storage/emulated/0/Pictures/Screenshots/Screenshot_20190816-103544.png
                val dateTaken = cursor.getLong(dateTakenIndex) //1565922944918
                val dateAdd = cursor.getLong(dateAddIndex) //1565922944918

                // 处理获取到的第一行数据
                handleMediaRowData(data, dateTaken, dateAdd)
            } catch (e: Exception) {
                e.printStackTrace()

            } finally {
                if (cursor != null && !cursor.isClosed) {
                    cursor.close()
                }
            }
        }

        /**
         * 处理监听到的资源
         */
        private fun handleMediaRowData(data: String, dateTaken: Long, dateAdd: Long) {
            if (!isTimeVaild(dateAdd)) {
                return
            }

            var isScreenshot = false


            var fileSize = 0L
            val startTime = System.currentTimeMillis()
            var endTime = startTime

            while (!isScreenshot && fileSize <= 0 && (endTime - startTime) <= 500) {
                isScreenshot = checkScreenShot(data, dateTaken)

                //2秒之内, 循环检查文件大小
                fileSize = data.fileSize()
                endTime = System.currentTimeMillis()

                if (!isScreenshot) {
                    SystemClock.sleep(30)
                }
            }

            if (isScreenshot) {
                L.d("$data $dateTaken $fileSize ${fileSize.fileSizeString()}")

                if (isRegister) {
                    mainHandler.post {
                        if (isRegister) {
                            onScreenshotCallback(data)
                        }
                    }
                }
            } else {
                L.d("Not screenshot event")
            }
        }

        private fun isTimeVaild(dateAddTime: Long): Boolean {
            return abs(System.currentTimeMillis() / 1000 - dateAddTime) < 1
        }

        /**
         * 判断是否是截屏
         */
        private fun checkScreenShot(data: String, dateTaken: Long): Boolean {
            val dataCase = data.toLowerCase()
            // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
            for (keyWork in KEYWORDS) {
                if (dataCase.contains(keyWork)) {
                    return true
                }
            }
            return false
        }
    }
}