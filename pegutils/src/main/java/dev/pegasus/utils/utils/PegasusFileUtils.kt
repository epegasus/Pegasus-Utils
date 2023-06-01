package dev.pegasus.utils.utils

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
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
        if (isFileValid(filePath)) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.setDataAndType(fileUri, context.contentResolver.getType(fileUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        } else {
            Log.e(TAG, "sharePicture: else: $filePath -> Path not Exist")
            context.showToast(context.getResString(R.string.file_not_found))
        }
    }

    fun getUriFromFilePathWithFileProvider(context: Context, filePath: String): Uri {
        val authority = "${context.packageName}.fileprovider"
        return FileProvider.getUriForFile(context, authority, File(filePath))
    }

    fun getImageDeleteUri(context: Context, path: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + " = ?",
            arrayOf(path),
            null
        )
        val uri = if (cursor != null && cursor.moveToFirst())
            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)))
        else null
        cursor?.close()
        return uri
    }

    fun getVideoDeleteUri(context: Context, path: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Video.Media._ID),
            MediaStore.Video.Media.DATA + " = ?",
            arrayOf(path),
            null
        )
        val uri = if (cursor != null && cursor.moveToFirst())
            ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)))
        else null
        cursor?.close()
        return uri
    }

}