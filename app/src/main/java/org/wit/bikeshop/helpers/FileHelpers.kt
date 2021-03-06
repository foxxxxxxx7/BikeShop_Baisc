package org.wit.bikeshop.helpers

import android.content.Context
import android.util.Log
import java.io.*

/**
 * It writes data to a file.
 *
 * @param context The context of the application.
 * @param fileName The name of the file you want to write to.
 * @param data The data you want to write to the file.
 */
fun write(context: Context, fileName: String, data: String) {
    try {
        val outputStreamWriter =
            OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE))
        outputStreamWriter.write(data)
        outputStreamWriter.close()
    } catch (e: Exception) {
        Log.e("Error: ", "Cannot read file: " + e.toString());
    }
}

/**
 * It reads a file from the internal storage of the app and returns the contents as a string
 *
 * @param context The context of the activity.
 * @param fileName The name of the file you want to read.
 * @return a string.
 */
fun read(context: Context, fileName: String): String {
    var str = ""
    try {
        val inputStream = context.openFileInput(fileName)
        if (inputStream != null) {
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val partialStr = StringBuilder()
            var done = false
            while (!done) {
                var line = bufferedReader.readLine()
                done = (line == null);
                if (line != null) partialStr.append(line);
            }
            inputStream.close()
            str = partialStr.toString()
        }
    } catch (e: FileNotFoundException) {
        Log.e("Error: ", "file not found: " + e.toString());
    } catch (e: IOException) {
        Log.e("Error: ", "cannot read file: " + e.toString());
    }
    return str
}

/**
 * It returns true if the file exists, and false if it doesn't
 *
 * @param context The context of the activity.
 * @param filename The name of the file you want to check for.
 * @return A boolean value.
 */
fun exists(context: Context, filename: String): Boolean {
    val file = context.getFileStreamPath(filename)
    return file.exists()
}