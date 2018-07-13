package m7mdra.com.picker.picker.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import m7mdra.com.picker.R

class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.image)
    val checkBox: CheckBox = view.findViewById(R.id.checkbox)
}
