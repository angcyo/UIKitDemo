package com.angcyo.uikitdemo.component

import androidx.annotation.VisibleForTesting
import com.google.gson.JsonParseException
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken.*
import com.google.gson.stream.JsonWriter
import java.io.Closeable
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import java.math.BigDecimal
import java.util.regex.Pattern


/**
 * Created by AItsuki at 2019/8/23.
 */
object JsonPathParser {

    @VisibleForTesting
    const val FILE_REGEX = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+"

    /**
     * 读取出所有文件的路径，返回map
     * @return 使用jsonPath作为key, 文件路径作为value。
     */
    fun read(json: String): MutableMap<String, String> {
        val reader = JsonReader(StringReader(json))
        val resultMap = mutableMapOf<String, String>()
        read(reader, resultMap)
        reader.safeClose()
        return resultMap
    }

    /**
     * 将字符串写入到path中（使用Gson遍历的方式）
     * @return 返回修改后的json
     */
    fun write(json: String, pathMap: MutableMap<String, String>): String {
        val reader = JsonReader(StringReader(json))
        val stringWriter = StringWriter()
        val writer = JsonWriter(stringWriter)
        write(reader, writer, pathMap)
        val resultJson = stringWriter.toString()
        reader.safeClose()
        writer.safeClose()
        return resultJson
    }

    private fun read(reader: JsonReader, map: MutableMap<String, String>) {
        while (true) {
            when (reader.peek()) {
                BEGIN_ARRAY -> reader.beginArray()
                END_ARRAY -> reader.endArray()
                BEGIN_OBJECT -> reader.beginObject()
                END_OBJECT -> reader.endObject()
                NAME -> reader.nextName()
                STRING -> {
                    val jsonPath = reader.path
                    val value = reader.nextString()
                    if (value.isFilePath()) {
                        map[jsonPath] = value
                    }
                }
                NUMBER -> reader.nextDouble()
                BOOLEAN -> reader.nextBoolean()
                NULL -> reader.nextNull()
                END_DOCUMENT -> return
                else -> throw JsonParseException("解析json出现了问题，请检查json的合法性。")
            }
        }
    }

    private fun write(reader: JsonReader, writer: JsonWriter, pathMap: MutableMap<String, String>) {
        while (true) {
            when (reader.peek()) {
                BEGIN_ARRAY -> {
                    reader.beginArray()
                    writer.beginArray()
                }
                END_ARRAY -> {
                    reader.endArray()
                    writer.endArray()
                }
                BEGIN_OBJECT -> {
                    reader.beginObject()
                    writer.beginObject()
                }
                END_OBJECT -> {
                    reader.endObject()
                    writer.endObject()
                }
                NAME -> {
                    val name = reader.nextName()
                    writer.name(name)
                }
                STRING -> {
                    val jsonPath = reader.path
                    val s = reader.nextString()
                    if (pathMap.containsKey(jsonPath)) {
                        writer.value(pathMap[jsonPath])
                    } else {
                        writer.value(s)
                    }
                }
                NUMBER -> {
                    val n = reader.nextString()
                    writer.value(BigDecimal(n))
                }
                BOOLEAN -> {
                    val b = reader.nextBoolean()
                    writer.value(b)
                }
                NULL -> {
                    reader.nextNull()
                    writer.nullValue()
                }
                END_DOCUMENT -> return
                else -> throw JsonParseException("解析json出现了问题，请检查json的合法性。")
            }
        }
    }

    /**
     * 判断字符串是否是文件路径。这个正则只能做简单校验是否是文件路径，无法判断文件路径是否合法。
     */
    private fun String.isFilePath(): Boolean = Pattern.matches(FILE_REGEX, this)

    private fun Closeable.safeClose() {
        try {
            this.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}