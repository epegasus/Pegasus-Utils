package dev.pegasus.utils.convertors

import android.net.Uri
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * @Author: SOHAIB AHMED
 * @Date: 05,June,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

class GsonUriTypeAdapter : TypeAdapter<Uri>() {

    override fun write(out: JsonWriter, value: Uri?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(value.toString())
        }
    }

    override fun read(`in`: JsonReader): Uri? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }

        val uriString = `in`.nextString()
        return Uri.parse(uriString)
    }
}