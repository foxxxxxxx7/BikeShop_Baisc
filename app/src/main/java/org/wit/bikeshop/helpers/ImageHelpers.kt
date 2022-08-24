package org.wit.bikeshop.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.ParcelFileDescriptor
import org.wit.bikeshop.R
import java.io.FileDescriptor
import java.io.IOException

/**
 * "This function takes a path to an image and returns a Bitmap object."
 *
 * The function takes two parameters:
 *
 * context: Context - This is the application context.
 * path: String - This is the path to the image.
 * The function returns a Bitmap object
 *
 * @param context Context - The context of the activity.
 * @param path The path to the image file.
 * @return A Bitmap object.
 */
/*I was having a problem with the images only saving to the cache, I later realised it was a problem with my phone
  denying permission to read the files on my phone, I found a solution in the docs here:
  https://developer.android.com/training/data-storage/shared/documents-files#persist-permissions*/

fun readImageFromPath(context: Context, path: String): Bitmap? {
    var bitmap: Bitmap? = null
    val uri = Uri.parse(path)
    if (uri != null) {
        try {
            /* A way to get access to the file system.
            * this is the crucial line needed to allow images to save */
            val contentResolver = context.contentResolver

            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
// Check for the freshest data.
            contentResolver.takePersistableUriPermission(uri, takeFlags)
            val parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.getFileDescriptor()
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: Exception) {
            println(e)
        }
    }
    return bitmap
}

//val contentResolver = applicationContext.contentResolver

/*@Throws(IOException::class)
 fun getBitmapFromUri(context: Context, uriIn: String): Bitmap {
    val uri = Uri.parse(uriIn)
    val parcelFileDescriptor: ParcelFileDescriptor =
        context.contentResolver.openFileDescriptor(uri, "r")!!
    val fileDescriptor: FileDescriptor = parcelFileDescriptor.fileDescriptor
    val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
    parcelFileDescriptor.close()
    return image
}*/

/*fun readImageFromPath(context: Context, path: String): Bitmap? {
    var bitmap: Bitmap? = null
    val uri = Uri.parse(path)
    if (uri != null) {
        try {
            val parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.getFileDescriptor()
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: Exception) {
        }
    }
    return bitmap
}*/

/**
 * It creates an intent that will open the file picker, and then it starts the activity for that intent
 *
 * @param parent The activity that is calling the image picker.
 * @param id The request code that will be passed to onActivityResult()
 */
fun showImagePicker(parent: Activity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_bike_image.toString())
    parent.startActivityForResult(chooser, id)
}

/**
 * If the result code is OK and the data is not null, then decode the bitmap from the data
 *
 * @param activity The activity that is calling the function.
 * @param resultCode The result code of the activity.
 * @param data Intent? - The intent that was used to start the activity.
 * @return A Bitmap
 */
fun readImage(activity: Activity, resultCode: Int, data: Intent?): Bitmap? {
    var bitmap: Bitmap? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try {
            bitmap = ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    activity.contentResolver, data.data!!
                )
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return bitmap
}