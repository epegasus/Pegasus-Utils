package dev.pegasus.utils.convertors

import android.net.Uri
import com.google.gson.GsonBuilder
import java.io.File

/**
 * @Author: SOHAIB AHMED
 * @Date: 05,June,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

open class BaseGsonTypeAdapter {

    protected val gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(File::class.java, GsonFileTypeAdapter())
            .registerTypeAdapter(Uri::class.java, GsonUriTypeAdapter())
            .create()
    }
}