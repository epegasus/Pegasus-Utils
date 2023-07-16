package dev.pegasus.utils.convertors

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.File
import java.io.IOException

/**
 * @Author: SOHAIB AHMED
 * @Date: 05,June,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class GsonFileTypeAdapter : TypeAdapter<File>() {

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: File) {
        writer.value(value.absolutePath)
    }

    @Throws(IOException::class)
    override fun read(reader: JsonReader): File {
        val path = reader.nextString()
        return File(path)
    }

    companion object {
        fun register(builder: GsonBuilder) {
            builder.registerTypeAdapter(File::class.java, GsonFileTypeAdapter())
        }
    }
}