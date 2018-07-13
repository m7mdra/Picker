package m7mdra.com.picker.picker.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import m7mdra.com.picker.R

class ImagesFolderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val folderName: TextView = view.findViewById(R.id.folderName)
        val imageCount: TextView = view.findViewById(R.id.imageCount)

    }
