package m7mdra.com.picker

import android.content.Context
import android.os.SystemClock
import android.util.Log
import okio.BufferedSink
import okio.Okio
import java.io.File
import java.io.FileOutputStream

public fun saveImage(context: Context, jpeg: ByteArray): File {
    val parent = File(context.cacheDir, "/pick_image")
    parent.mkdir()
    val image = File(parent, "image" + SystemClock.elapsedRealtime() + ".jpg")
    val fileOutputStream = FileOutputStream(image)
    val sink = Okio.buffer(Okio.sink(fileOutputStream))
    (sink as BufferedSink).write(jpeg, 0, jpeg.size)
    return image
}

public fun log(any: Any) {
    Log.d("MEGA", any.toString())
}
