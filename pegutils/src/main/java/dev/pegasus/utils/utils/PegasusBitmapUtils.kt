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
import androidx.core.graphics.createBitmap
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

/**
 * @Author: SOHAIB AHMED
 * @Date: 07,February,2023
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://stackoverflow.com/users/20440272/sohaib-ahmed
 */

object PegasusBitmapUtils {

    private const val TAG = "PegasusBitmapUtils"
    private const val MAX_WIDTH = 1080f
    private const val MAX_HEIGHT = 1920f

    /**
     * Converts a Drawable to Bitmap.
     * Optionally compresses it.
     */
    fun getBitmapFromDrawable(drawable: Drawable, isCompress: Boolean = true): Bitmap? {
        val bitmap = when (drawable) {
            is BitmapDrawable -> drawable.bitmap
            else -> {
                val width = drawable.intrinsicWidth
                val height = drawable.intrinsicHeight
                if (width <= 0 || height <= 0) {
                    Log.e(TAG, "Drawable has invalid size: $width x $height")
                    return null
                }
                createBitmap(width, height).apply {
                    val canvas = Canvas(this)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                }
            }
        }

        return if (isCompress) compressBitmap(bitmap) else bitmap
    }

    /**
     * Loads a Drawable from resources and returns a Bitmap.
     */
    fun getBitmapFromResource(context: Context, @DrawableRes resId: Int, isCompress: Boolean = true): Bitmap? {
        return try {
            val drawable = ContextCompat.getDrawable(context, resId)
            when (drawable != null) {
                true -> getBitmapFromDrawable(drawable, isCompress)
                false -> {
                    Log.e(TAG, "Drawable not found for resId: $resId")
                    null
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "getBitmapFromResource: ${ex.message}", ex)
            null
        }
    }

    /**
     * Loads and decodes a Bitmap from file path, applies EXIF rotation and compression if needed.
     */
    fun getBitmapFromFilePath(filePath: String, isCompress: Boolean = true): Bitmap? {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                Log.e(TAG, "File not found: $filePath")
                return null
            }

            val exif = ExifInterface(filePath)
            val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }

            val bitmap = when (isCompress) {
                true -> decodeCompressedBitmap(filePath)
                false -> BitmapFactory.decodeFile(filePath)
            }

            if (bitmap == null) {
                Log.e(TAG, "Bitmap could not be decoded from: $filePath")
                null
            } else {
                applyExifRotation(bitmap, rotation)
            }
        } catch (ex: Exception) {
            Log.e(TAG, "getBitmapFromFilePath: ${ex.message}", ex)
            null
        }
    }

    /**
     * Compresses a Bitmap to fit within MAX_WIDTH and MAX_HEIGHT.
     */
    fun compressBitmap(bitmap: Bitmap, maxWidth: Float? = null, maxHeight: Float? = null): Bitmap {
        val mw = maxWidth ?: MAX_WIDTH
        val mh = maxHeight ?: MAX_WIDTH
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = mw / width
        val scaleHeight = mh / height
        val scaleFactor = scaleWidth.coerceAtMost(scaleHeight)

        val matrix = Matrix().apply { postScale(scaleFactor, scaleFactor) }
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
    }

    /**
     * Applies rotation based on EXIF metadata.
     */
    fun applyExifRotation(bitmap: Bitmap, rotation: Int): Bitmap {
        return if (rotation != 0) {
            val matrix = Matrix().apply { postRotate(rotation.toFloat()) }
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } else bitmap
    }

    /**
     * Decodes a compressed bitmap from file path using sample size.
     */
    private fun decodeCompressedBitmap(filePath: String): Bitmap? {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeFile(filePath, options)

        val (height, width) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > MAX_HEIGHT || width > MAX_WIDTH) {
            val heightRatio = (height / MAX_HEIGHT).roundToInt()
            val widthRatio = (width / MAX_WIDTH).roundToInt()
            inSampleSize = maxOf(1, minOf(heightRatio, widthRatio))
        }

        options.inJustDecodeBounds = false
        options.inSampleSize = inSampleSize

        return BitmapFactory.decodeFile(filePath, options)
    }

    fun Bitmap.convertToCacheFile(context: Context, fileName: String): File {
        val file = File(context.cacheDir, fileName)
        file.createNewFile()

        FileOutputStream(file).use { fos ->
            this.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }

        return file
    }
}