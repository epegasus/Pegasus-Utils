package dev.pegasus.utils.adapters

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import dev.pegasus.utils.R
import dev.pegasus.utils.extensions.tools.printToErrorLog
import java.io.File

/**
 * @Author: SOHAIB AHMED
 * @Date: 31,May,2023.
 * @Accounts
 *      -> https://github.com/epegasus
 *      -> https://www.linkedin.com/in/epegasus
 */

/**
 *  Types of ImageView
 *      -> ImageView
 *      -> ImageFilterView
 *      -> ShapeableImageView
 *
 *  Customize following methods according to your ImageView class.
 */

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

/**
 * @param: imageId -> Set image resource_id for this (e.g. R.drawable.img_dummy)
 *  Syntax:
 *      xml     -> app:imageId="@{imageResource}"
 *      kotlin  -> binding.imageView.setImageFromResource(R.drawable.img_dummy)
 */

@BindingAdapter("imageId")
fun ImageView.setImageFromResource(imageId: Int) {
    Glide
        .with(this)
        .load(imageId)
        .into(this)
}

@BindingAdapter("imageId")
fun ShapeableImageView.setImageFromResource(imageId: Int) {
    Glide
        .with(this)
        .load(imageId)
        .into(this)
}

@BindingAdapter("imageId")
fun ImageFilterView.setImageFromResource(imageId: Int) {
    Glide
        .with(this)
        .load(imageId)
        .into(this)
}

/**
 * @param: imageUri -> Set image uri for this
 *  Syntax:
 *      xml     -> app:imageUri="@{uri}"
 *      kotlin  -> binding.imageView.setImageFromUri(uri)
 */

@BindingAdapter("imageUri")
fun ImageView.setImageFromUri(imageUri: Uri?) {
    Glide
        .with(this)
        .load(imageUri)
        .into(this)
}

@BindingAdapter("imageUri")
fun ShapeableImageView.setImageFromUri(imageUri: Uri?) {
    Glide
        .with(this)
        .load(imageUri)
        .into(this)
}

@BindingAdapter("imageUri")
fun ImageFilterView.setImageFromUri(imageUri: Uri?) {
    Glide
        .with(this)
        .load(imageUri)
        .into(this)
}

/**
 * @param: imageFilePath -> Set image file path for this (e.g. File)
 *  Syntax:
 *      xml     -> app:imageFilePath="@{file}"
 *      kotlin  -> binding.imageView.setImageFromFilePath(file)
 */

@BindingAdapter("imageFile")
fun ImageView.setImageFromFile(imageFile: File) {
    try {
        if (imageFile.exists()) {
            Glide
                .with(this)
                .load(imageFile.toString())
                .into(this)
        }
    } catch (ex: SecurityException) {
        ex.printToErrorLog("ImageView: setImageFromFile: imageFilePath.exists")
    }
}

@BindingAdapter("imageFile")
fun ShapeableImageView.setImageFromFile(imageFile: File) {
    try {
        if (imageFile.exists()) {
            Glide
                .with(this)
                .load(imageFile.toString())
                .into(this)
        } else {
            Glide.with(this).load(R.drawable.img_error).into(this)
        }
    } catch (ex: SecurityException) {
        Glide.with(this).load(R.drawable.img_error).into(this)
        ex.printToErrorLog("ShapeableImageView: setImageFromFile: imageFilePath.exists")
    }
}

@BindingAdapter("imageFile")
fun ImageFilterView.setImageFromFile(imageFile: File) {
    try {
        if (imageFile.exists()) {
            Glide
                .with(this)
                .load(imageFile.toString())
                .into(this)
        } else {
            Glide.with(this).load(R.drawable.img_error).into(this)
        }
    } catch (ex: SecurityException) {
        Glide.with(this).load(R.drawable.img_error).into(this)
        ex.printToErrorLog("ShapeableImageView: setImageFromFile: imageFilePath.exists")
    }
}

/**
 * @param: imageFilePath -> Set image file path for this (e.g. File)
 *  Syntax:
 *      xml     -> app:imageFilePath="@{file}"
 *      kotlin  -> binding.imageView.setImageFromFilePath(file)
 */

@BindingAdapter("imageFilePath")
fun ImageView.setImageFromFilePath(imageFilePath: String) {
    try {
        if (File(imageFilePath).exists()) {
            Glide
                .with(this)
                .load(imageFilePath)
                .into(this)
        } else
            Glide.with(this).load(R.drawable.img_error).into(this)
    } catch (ex: SecurityException) {
        ex.printToErrorLog("ImageView: setImageFromFilePath: imageFilePath.exists")
    }
}

@BindingAdapter("imageFilePath")
fun ShapeableImageView.setImageFromFilePath(imageFilePath: String) {
    try {
        if (File(imageFilePath).exists()) {
            Glide
                .with(this)
                .load(imageFilePath)
                .into(this)
        } else {
            Glide.with(this).load(R.drawable.img_error).into(this)
        }
    } catch (ex: SecurityException) {
        Glide.with(this).load(R.drawable.img_error).into(this)
        ex.printToErrorLog("ShapeableImageView: setImageFromFilePath: imageFilePath.exists")
    }
}

@BindingAdapter("imageFilePath")
fun ImageFilterView.setImageFromFilePath(imageFilePath: String) {
    try {
        if (File(imageFilePath).exists()) {
            Glide
                .with(this)
                .load(imageFilePath)
                .into(this)
        } else {
            Glide.with(this).load(R.drawable.img_error).into(this)
        }
    } catch (ex: SecurityException) {
        Glide.with(this).load(R.drawable.img_error).into(this)
        ex.printToErrorLog("ImageFilterView: setImageFromFilePath: imageFilePath.exists")
    }
}

/**
 * @param: imageDrawable -> Set image drawable object for this (e.g. Drawable)
 *  Syntax:
 *      xml     -> app:imageDrawable="@{@drawable/img_dummy}"
 *      kotlin  -> binding.imageView.setImageFromDrawable(Drawable)
 */

@BindingAdapter("imageDrawable")
fun ImageView.setImageFromDrawable(imageDrawable: Drawable) {
    Glide
        .with(this)
        .load(imageDrawable)
        .into(this)
}

@BindingAdapter("imageDrawable")
fun ShapeableImageView.setImageFromDrawable(imageDrawable: Drawable) {
    Glide
        .with(this)
        .load(imageDrawable)
        .into(this)
}

@BindingAdapter("imageDrawable")
fun ImageFilterView.setImageFromDrawable(imageDrawable: Drawable) {
    Glide
        .with(this)
        .load(imageDrawable)
        .into(this)
}

/**
 * @param: imageAsset -> Set image from assets directory for this (e.g. Assets > flags > imgDummy)
 *  Syntax:
 *      xml     -> app:imageAsset="@{imgDummy}"
 *      xml     -> app:assetPath="@{path}"
 *      kotlin  -> binding.imageView.setImageFromAssets("imgDummy")
 */

@BindingAdapter(value = ["assetPath", "imageAsset"], requireAll = false)
fun ImageView.setImageFromAssets(assetPath: String, imageAsset: String) {
    Glide
        .with(this)
        .load(Uri.parse("file:///android_asset/$assetPath/$imageAsset.webp"))
        .into(this)
}

@BindingAdapter(value = ["assetPath", "imageAsset"], requireAll = false)
fun ShapeableImageView.setImageFromAssets(assetPath: String, imageAsset: String) {
    Glide
        .with(this)
        .load(Uri.parse("file:///android_asset/$assetPath/$imageAsset.webp"))
        .into(this)
}

@BindingAdapter(value = ["assetPath", "imageAsset"], requireAll = false)
fun ImageFilterView.setImageFromAssets(assetPath: String, imageAsset: String) {
    Glide
        .with(this)
        .load(Uri.parse("file:///android_asset/$assetPath/$imageAsset.webp"))
        .into(this)
}

