package dev.pegasus.utils.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import dev.pegasus.utils.R
import dev.pegasus.utils.extensions.ui.getResString
import dev.pegasus.utils.extensions.ui.showToast
import dev.pegasus.utils.utils.PegasusHelperUtils.TAG
import java.io.ByteArrayOutputStream
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

    data class StorageState(val isAvailable: Boolean, val isWritable: Boolean, val message: String)

    /* ---------------------- Check if External storage (e.g. SD card) is available ---------------------- */

    fun isExternalStorageWritable(): StorageState {
        return when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED -> StorageState(isAvailable = true, isWritable = true, message = "External storage is available for read and write access")
            Environment.MEDIA_MOUNTED_READ_ONLY -> StorageState(isAvailable = true, isWritable = false, message = "External storage is available for read-only access")
            else -> StorageState(isAvailable = false, isWritable = false, message = "External storage is not available")
        }
    }

    fun getFilePathFromUri(context: Context, uri: Uri, index: Int): String {
        return if (uri.path?.contains("file://") == true) {
            uri.path ?: ""
        } else {
            getFileFromContentUri(context, uri, index).path ?: ""
        }
    }

    private fun getFileFromContentUri(context: Context, contentUri: Uri, index: Int): File {
        val fileExtension = getFileExtension(context, contentUri) ?: "tmp"
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "temp_file_${timeStamp}_$index.$fileExtension"

        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    copy(inputStream, outputStream)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getFileFromContentUri failed", e)
        }

        return tempFile
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            uri.path?.let { path ->
                MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(path)).toString())
            }
        }
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }

    fun isFileValid(filePath: String): Boolean {
        val file = File(filePath)
        return try {
            file.exists()
        } catch (ex: SecurityException) {
            Log.e(TAG, "isFileValid: FilePath: $filePath", ex)
            false
        }
    }

    fun shareFile(context: Context, filePath: String, fileUri: Uri) {
        if (!isFileValid(filePath)) {
            Log.e(TAG, "File does not exist: $filePath")
            context.showToast(context.getResString(R.string.file_not_found))
            return
        }

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            setDataAndType(fileUri, context.contentResolver.getType(fileUri))
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Choose an app"))
    }

    fun getUriFromFilePathWithFileProvider(context: Context, filePath: String): Uri {
        val authority = "${context.packageName}.fileprovider"
        return FileProvider.getUriForFile(context, authority, File(filePath))
    }

    fun getImageDeleteUri(context: Context, path: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            "${MediaStore.Images.Media.DATA} = ?",
            arrayOf(path),
            null
        )

        val uri = cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            } else null
        }

        return uri
    }

    fun getVideoDeleteUri(context: Context, path: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media._ID),
            "${MediaStore.Video.Media.DATA} = ?",
            arrayOf(path),
            null
        )

        val uri = cursor?.use {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            } else null
        }

        return uri
    }
}