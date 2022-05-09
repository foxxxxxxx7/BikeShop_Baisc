package org.wit.bikeshop.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import org.wit.bikeshop.R
import java.io.IOException

/**
 * "Given a path to an image, return a Bitmap object."
 *
 * The function takes two parameters: a Context object and a String. The Context object is used to get
 * access to the file system. The String is the path to the image
 *
 * @param context The context of the activity.
 * @param path The path to the image file.
 * @return A Bitmap object
 */
fun readImageFromPath(context: Context, path: String): Bitmap? {
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
}

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