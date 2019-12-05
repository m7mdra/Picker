package m7mdra.com.picker.picker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import m7mdra.com.picker.ItemClickListener
import m7mdra.com.picker.R
import m7mdra.com.picker.model.Album
import m7mdra.com.picker.model.Image
import java.io.File

class ImageAdapter(val images: Album,
                   private val picasso: Picasso,
                   private val itemClickListener: ItemClickListener<String>,
                   private val multiPicks: Boolean,
                   private val selectListener: (List<Image>, Int) -> Unit) :
        RecyclerView.Adapter<ImageViewHolder>() {
    private val imageSet = mutableSetOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
            ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false))

    override fun getItemCount(): Int {
        return images.pathList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images.pathList[position]
        val imageId = image.imageId
        if (multiPicks) {
            holder.itemView.setOnClickListener {
                if (imageSet.contains(imageId))
                    imageSet.remove(imageId)
                else imageSet.add(imageId)
                notifyItemChanged(position)
                selectListener(getSelected(), imageSet.size)
            }
            holder.checkBox.isChecked = imageSet.contains(imageId)
        } else {
            holder.imageView.setOnClickListener { itemClickListener.onClick(it, image.imagePath, holder.adapterPosition) }
            holder.checkBox.visibility = View.GONE
        }
        picasso.load(File(image.imagePath))
                .centerCrop()
                .resize(image.width / 3, image.height / 3)
                .into(holder.imageView)
    }

    fun getSelected(): ArrayList< Image> {
        val list = mutableListOf<Image>()
        images.pathList.forEach { image ->
            imageSet.forEach { id ->
                if (image.imageId == id)
                    list.add(image)
            }
        }
        return ArrayList(list)
    }

    fun clearSelection() {
        imageSet.clear()
        notifyDataSetChanged()
    }
}