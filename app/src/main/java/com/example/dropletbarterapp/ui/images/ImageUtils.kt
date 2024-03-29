package com.example.dropletbarterapp.ui.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.example.dropletbarterapp.R
import java.util.*

object ImageUtils {

    private fun decodeBitmapImageFromDB(image: String): Bitmap {
        val photoByte = Base64.getDecoder().decode(image)
        return BitmapFactory.decodeByteArray(
            photoByte, 0,
            photoByte.size
        )
    }

    fun loadImageBitmap(
        photo: String?,
        context: Context,
        imageView: ImageView,
        cropper: BitmapTransformation
    ) {
        if (photo != null) {
            Glide.with(context)
                .asBitmap()
                .apply(RequestOptions().centerCrop())
                .load(decodeBitmapImageFromDB(photo))
                .error(R.drawable.empty_profile_image)
                .placeholder(R.drawable.empty_profile_image)
                .apply(RequestOptions.bitmapTransform(cropper))
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.empty_profile_image)
                .error(R.drawable.empty_profile_image)
                .placeholder(R.drawable.empty_profile_image)
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions.bitmapTransform(cropper))
                .into(imageView)
        }

    }

    fun loadImageUri(
        uri: String?,
        context: Context,
        imageView: ImageView,
        cropper: BitmapTransformation
    ) {
        if (uri != null) {
            Glide.with(context)
                .load(uri)
                .error(R.drawable.empty_profile_image)
                .placeholder(R.drawable.empty_profile_image)
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions.bitmapTransform(cropper))
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.empty_profile_image)
                .error(R.drawable.empty_profile_image)
                .placeholder(R.drawable.empty_profile_image)
                .apply(RequestOptions().centerCrop())
                .apply(RequestOptions.bitmapTransform(cropper))
                .into(imageView)

        }
    }

}