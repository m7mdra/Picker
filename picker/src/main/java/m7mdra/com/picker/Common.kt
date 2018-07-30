package m7mdra.com.picker

import android.content.Context
import android.os.SystemClock
import android.util.Log
import okio.BufferedSink
import okio.Okio
import java.io.File
import java.io.FileOutputStream
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.content.ContentUris
import android.os.Environment.getExternalStorageDirectory
import android.os.Build
import android.annotation.TargetApi
import android.database.Cursor
import android.net.Uri
import android.os.Environment


fun saveImage(context: Context, jpeg: ByteArray): File {
    val parent = File(context.cacheDir, "/pick_image")
    parent.mkdir()
    val image = File(parent, "image" + SystemClock.elapsedRealtime() + ".jpg")
    val fileOutputStream = FileOutputStream(image)
    val sink = Okio.buffer(Okio.sink(fileOutputStream))
    (sink as BufferedSink).write(jpeg, 0, jpeg.size)
    return image
}

fun log(any: Any) {
    Log.d("MEGA", any.toString())
}

