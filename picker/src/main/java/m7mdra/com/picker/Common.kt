package m7mdra.com.picker

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast




fun log(any: Any, tag: String="MEGA") {
    Log.d(tag, any.toString())
}


fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Fragment.toast(message: String) = Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

