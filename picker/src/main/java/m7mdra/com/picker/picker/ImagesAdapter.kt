package m7mdra.com.picker.picker

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import m7mdra.com.picker.ItemClickListener
import m7mdra.com.picker.R
import m7mdra.com.picker.model.Album
import java.io.File

class ImagesAdapter(val images: Album, val picasso: Picasso, private val itemClickListener: ItemClickListener<String>) : RecyclerView.Adapter<ImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder =
            ImagesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false))

    override fun getItemCount(): Int {
        return images.pathList.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val image = images.pathList[position]
        Log.d("MEGA", image.imagePath + "\t" + "${image.width}x${image.height} will be resized to: ${image.width / 3}x${image.height / 3}")

        picasso.load(File(image.imagePath))
                .centerCrop()
                .resize(image.width/3,image.height/3)
                .into(holder.imageView)
        holder.imageView.setOnClickListener { itemClickListener.onClick(it, image.imagePath, holder.adapterPosition) }
    }
}