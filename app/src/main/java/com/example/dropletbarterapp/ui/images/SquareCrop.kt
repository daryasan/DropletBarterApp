package com.example.dropletbarterapp.ui.images

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class SquareCrop : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("square-crop".toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val size = toTransform.width.coerceAtMost(toTransform.height)
        val x = (toTransform.width - size) / 2
        val y = (toTransform.height - size) / 2
        return Bitmap.createBitmap(toTransform, x, y, size, size)
    }
}