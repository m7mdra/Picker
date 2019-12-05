package m7mdra.com.picker.picker.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import m7mdra.com.picker.R

class ImagesFolderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image)
        val folderName: TextView = view.findViewById(R.id.folderName)
        val imageCount: TextView = view.findViewById(R.id.imageCount)

    }
