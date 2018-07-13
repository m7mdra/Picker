package m7mdra.com.picker

import android.view.View

interface ItemClickListener<in T> {
    fun onClick(view: View, item: T, position: Int)
}