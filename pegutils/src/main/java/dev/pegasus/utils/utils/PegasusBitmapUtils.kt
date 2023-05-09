package dev.pegasus.utils.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import kotlin.math.roundToInt

/**
 * @Author: SOHAIB AHMED
 * @Date: 07,February,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

object PegasusBitmapUtils {

    private const val TAG_UTILS = "PegasusBitmapUtils"

    private val maxBitmapWidth by lazy { 1080.0f }
    private val maxBitmapHeight by lazy { 1920.0f }


    private fun getCompressedBitmap(bitmap: Bitmap, bitmapCallback: BitmapCallback) = CoroutineScope(Dispatchers.Default).launch {
        try {
            val width = bitmap.width
            val height = bitmap.height
            val scaleWidth = maxBitmapWidth / width
            val scaleHeight = maxBitmapHeight / height
            val scaleFactor = scaleWidth.coerceAtMost(scaleHeight)

            val scale = Matrix()
            scale.postScale(scaleFactor, scaleFactor)

            val newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, scale, false)
            withContext(Dispatchers.Main) { bitmapCallback.onSuccess(newBitmap) }

        } catch (ex: OutOfMemoryError) {
            Log.e(TAG_UTILS, ex.toString())
            withContext(Dispatchers.Main) { bitmapCallback.onFailure(ex.message.toString()) }
        } catch (ex: RuntimeException) {
            Log.e(TAG_UTILS, ex.toString())
            withContext(Dispatchers.Main) { bitmapCallback.onFailure(ex.message.toString()) }
        } catch (ex: Exception) {
            Log.e(TAG_UTILS, ex.toString())
            withContext(Dispatchers.Main) { bitmapCallback.onFailure(ex.message.toString()) }
        }
    }

    /**
     * @param drawable:         Drawable object to be compressed.
     * @param isCompress:       Need to compress or not.
     * @param bitmapCallback:   Callback to be invoked when compressing.
     *      -> onProcessing:    Called when start to compress.
     *      -> onSuccess:       Called when success to compress.
     *      -> onError:         Called when error while compressing.
     */

    fun getBitmapFromImageDrawable(drawable: Drawable, isCompress: Boolean = true, bitmapCallback: BitmapCallback) = CoroutineScope(Dispatchers.Default).launch {
        withContext(Dispatchers.Main) {
            bitmapCallback.onProcessing()
        }
        if (drawable is BitmapDrawable) {
            if (isCompress) {
                getCompressedBitmap(drawable.bitmap, bitmapCallback)
            } else {
                withContext(Dispatchers.Main) {
                    try {
                        bitmapCallback.onSuccess(drawable.bitmap)
                    } catch (ex: RuntimeException) {
                        val errorMessage = ex.message.toString()
                        Log.e(TAG_UTILS, errorMessage)
                        bitmapCallback.onFailure(errorMessage)
                    }
                }
            }
            return@launch
        }

        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight

        if (width <= 0 || height <= 0) {
            val errorMessage = "getBitmapFromDrawable: width or height must > 0 -> Width = $width, Height = $height"
            Log.e(TAG_UTILS, errorMessage)
            withContext(Dispatchers.Main) {
                bitmapCallback.onFailure(errorMessage)
            }
            return@launch
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        if (isCompress) {
            getCompressedBitmap(bitmap, bitmapCallback)
            return@launch
        }
        withContext(Dispatchers.Main) {
            bitmapCallback.onSuccess(bitmap)
        }
    }


    /**
     * @param context:          Context of application
     * @param resId:            Resource Id to be compressed. (e.g. R.drawable.image)
     * @param isCompress:       Need to compress or not.
     * @param bitmapCallback:   Callback to be invoked when compressing.
     *      -> onProcessing:    Called when start to compress.
     *      -> onSuccess:       Called when success to compress.
     *      -> onError:         Called when error while compressing.
     */

    fun getBitmapFromImageResource(context: Context, @DrawableRes resId: Int, isCompress: Boolean = true, bitmapCallback: BitmapCallback) = CoroutineScope(Dispatchers.Default).launch {
        withContext(Dispatchers.Main) {
            bitmapCallback.onProcessing()
        }
        try {
            val drawable: Drawable? = ContextCompat.getDrawable(context, resId)
            drawable?.let {
                getBitmapFromImageDrawable(it, isCompress, bitmapCallback)
            } ?: run {
                val errorMessage = "getBitmapFromImageResource: drawable can't be null"
                throw NullPointerException(errorMessage)
            }
        } catch (ex: NullPointerException) {
            val errorMessage = ex.message.toString()
            Log.e(TAG_UTILS, errorMessage)
            withContext(Dispatchers.Main) {
                bitmapCallback.onFailure(errorMessage)
            }
        }
    }

    /**
     * @param filePath:         FilePath of the image.
     * @param isCompress:       Need to compress or not.
     * @param bitmapCallback:   Callback to be invoked when compressing.
     *      -> onProcessing:    Called when start to compress.
     *      -> onSuccess:       Called when success to compress.
     *      -> onError:         Called when error while compressing.
     */

    fun getBitmapFromFilePath(filePath: String, isCompress: Boolean = true, bitmapCallback: BitmapCallback) = CoroutineScope(Dispatchers.IO).launch {
        withContext(Dispatchers.Main) {
            bitmapCallback.onProcessing()
        }
        try {
            if (!File(filePath).exists()) {
                throw FileNotFoundException("File not found: $filePath")
            }

            val exif = ExifInterface(filePath)
            val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
            if (!isCompress) {
                val bitmap = BitmapFactory.decodeFile(filePath)
                val newBitmap = getBitmapExifRotated(rotation, bitmap)
                withContext(Dispatchers.Main) { bitmapCallback.onSuccess(newBitmap) }
                return@launch
            }

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            BitmapFactory.decodeFile(filePath, options)

            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > maxBitmapHeight || width > maxBitmapWidth) {
                val heightRatio = (height.toFloat() / maxBitmapHeight).roundToInt()
                val widthRatio = (width.toFloat() / maxBitmapWidth).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }

            options.apply {
                this.inJustDecodeBounds = false
                this.inSampleSize = inSampleSize
            }

            val bitmap = BitmapFactory.decodeFile(filePath, options)
            val newBitmap = getBitmapExifRotated(rotation, bitmap)
            withContext(Dispatchers.Main) { bitmapCallback.onSuccess(newBitmap) }

        } catch (ex: OutOfMemoryError) {
            val errorMessage = ex.message.toString()
            Log.e(TAG_UTILS, errorMessage)
            withContext(Dispatchers.Main) { bitmapCallback.onFailure(errorMessage) }
        } catch (ex: FileNotFoundException) {
            val errorMessage = ex.message.toString()
            Log.e(TAG_UTILS, errorMessage)
            withContext(Dispatchers.Main) { bitmapCallback.onFailure(errorMessage) }
        }
    }

    private fun getBitmapExifRotated(rotation: Int, bitmap: Bitmap): Bitmap {
        return if (rotation != 0) {
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, Matrix().apply { postRotate(rotation.toFloat()) }, true)
        } else {
            bitmap
        }
    }

    interface BitmapCallback {
        fun onProcessing() {}
        fun onSuccess(bitmap: Bitmap)
        fun onFailure(errorMessage: String)
    }
}