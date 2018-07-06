package m7mdra.com.picker.picker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import m7mdra.com.picker.R

class ImagesViewHolder(val view:View) :RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.image)
}
