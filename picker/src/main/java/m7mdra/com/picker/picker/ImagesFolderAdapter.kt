package m7mdra.com.picker.picker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import m7mdra.com.picker.ItemClickListener
import m7mdra.com.picker.R
import m7mdra.com.picker.model.Album
import java.io.File

class ImagesFolderAdapter(private val imagesList: List<Album>, private val picasso: Picasso,
                          private val itemClickListener: ItemClickListener<Album>) :
        RecyclerView.Adapter<ImagesFolderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesFolderViewHolder =
            ImagesFolderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_folder_image, parent, false))

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: ImagesFolderViewHolder, position: Int) {
        val images = imagesList[position]
        if (images.pathList.isNotEmpty()) {

            val image = images.pathList[0]
            picasso.load(File(image.imagePath))
                    .centerCrop()
                    .resize(image.width / 3, image.height / 3)
                    .into(holder.imageView)
        }
            holder.folderName.text = images.folderName
        holder.imageCount.text = String.format("%s %s", images.pathList.size.toString(),
                holder.itemView.context.getString(R.string.images))
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, images, holder.adapterPosition)
        }
    }


}

