package m7mdra.com.picker.picker.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import m7mdra.com.picker.R

class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.image)
    val checkBox: CheckBox = view.findViewById(R.id.checkbox)
}
