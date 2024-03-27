package com.example.dropletbarterapp.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.dropletbarterapp.BuildConfig
import java.io.File
import java.io.InputStream
import java.util.*

object Utils {


    fun decodeBitmapImageFromDB(image: String): Bitmap {
        val photoByte = Base64.getDecoder().decode(image)
        return BitmapFactory.decodeByteArray(
            photoByte, 0,
            photoByte.size
        )
    }


}