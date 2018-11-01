package m7mdra.com.picker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Environment
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import okio.BufferedSink
import okio.Okio
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun saveImage(context: Context, jpeg: ByteArray): File? {
    val parent = File(context.cacheDir, "/pick_image")
    parent.mkdir()
    val image = File(parent, "image" + SystemClock.elapsedRealtime() + ".jpg")
    val fileOutputStream = FileOutputStream(image)
    val sink = Okio.buffer(Okio.sink(fileOutputStream))
    (sink as BufferedSink).write(jpeg, 0, jpeg.size)
    return image
}

fun log(any: Any, tag: String="MEGA") {
    Log.d(tag, any.toString())
}



fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Fragment.toast(message: String) = Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

