package com.example.dropletbarterapp.ui.images

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.example.dropletbarterapp.BuildConfig
import com.example.dropletbarterapp.validators.Toaster
import java.io.File
import java.io.InputStream
import java.util.*

class ImageLoader(
    val context: Context,
    val fragment: Fragment,
    private val imageView: ImageView,
    private val cropper: BitmapTransformation
) {

    var photo: ByteArray? = null
    private val toaster = Toaster()

    // permissions
    private val galleryRequest =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = it.data?.data
                ImageUtils.loadImageUri(
                    imageUri.toString(),
                    context,
                    imageView,
                    cropper
                )
                imageUri?.let { uri ->
                    val inputStream: InputStream? =
                        context.contentResolver.openInputStream(uri)
                    inputStream?.use { stream ->
                        photo = stream.readBytes()
                    }
                }
            } else {
                toaster.getToast(context, "Что-то пошло не так...")
            }
        }

    private val cameraRequest =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uuid: String = UUID.randomUUID().toString()
                val outputDir: File = context.cacheDir
                val file: File = File.createTempFile(uuid, ".jpg", outputDir)

                val imageUri = FileProvider.getUriForFile(
                    Objects.requireNonNull(context),
                    BuildConfig.APPLICATION_ID + ".provider", file
                )
                ImageUtils.loadImageUri(
                    imageUri.toString(),
                    context,
                    imageView,
                    cropper
                )
                photo = File(it.data?.data.toString()).readBytes()
            } else {
                toaster.getToast(context, "Что-то пошло не так...")
            }
        }

    private val requestPermissionCameraLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toaster.getToast(context, "Невозможно продолжить без разрешений!")
        } else {
            takePhoto()
        }
    }

    private val requestPermissionStorageLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            toaster.getToast(context, "Невозможно продолжить без разрешений!")
        } else {
            selectPhotoFromGallery()
        }
    }

    private fun selectPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryRequest.launch(intent)
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraRequest.launch(intent)
    }

    fun addPhoto() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Выберите действие:")
        builder.setItems(arrayOf("Сделать фото", "Выбрать из галереи")) { dialog, action ->
            when (action) {
                0 -> {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        takePhoto()
                    } else {
                        requestPermissionCameraLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
                1 -> {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        selectPhotoFromGallery()
                    } else {
                        requestPermissionStorageLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }

                }
            }
        }
        builder.show()
    }

}