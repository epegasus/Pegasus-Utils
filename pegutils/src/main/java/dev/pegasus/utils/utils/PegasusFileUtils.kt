package dev.pegasus.utils.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @Author: SOHAIB AHMED
 * @Date: 08,February,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

object PegasusFileUtils {

    /* ---------------------- Check if External storage (e.g. SD card) is available ---------------------- */

    fun isExternalStorageWritable(callback: (isAvailable: Boolean, isWriteable: Boolean, message: String) -> Unit) {
        when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED -> {
                callback.invoke(true, true, "External storage is available for read and write access")
            }

            Environment.MEDIA_MOUNTED_READ_ONLY -> {
                callback.invoke(true, false, "External storage is available for read-only access")
            }

            else -> {
                callback.invoke(false, false, "External storage is not available")
            }
        }
    }


    fun getFilePathFromUri(context: Context, uri: Uri, index: Int): String =
        if (uri.path?.contains("file://") == true) uri.path!!
        else getFileFromContentUri(context, uri, index).path

    private fun getFileFromContentUri(context: Context, contentUri: Uri, index: Int): File {
        context.let {
            // Preparing Temp file name
            val fileExtension = getFileExtension(it, contentUri) ?: ""
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val fileName = "temp_file_$timeStamp _$index.$fileExtension"
            // Creating Temp file
            val tempFile = File(it.cacheDir, fileName)
            tempFile.createNewFile()
            // Initialize streams
            var oStream: FileOutputStream? = null
            var inputStream: InputStream? = null

            try {
                oStream = FileOutputStream(tempFile)
                inputStream = it.contentResolver.openInputStream(contentUri)

                inputStream?.let { copy(inputStream, oStream) }
                oStream.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                // Close streams
                inputStream?.close()
                oStream?.close()
            }
            return tempFile
        }
    }

    private fun getFileExtension(context: Context, uri: Uri): String? =
        if (uri.scheme == ContentResolver.SCHEME_CONTENT)
            MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
        else uri.path?.let { MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(it)).toString()) }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}