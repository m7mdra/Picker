package m7mdra.com.picker

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import m7mdra.com.picker.model.Album
import m7mdra.com.picker.model.Image

class ImageProvider(private val contentResolver: ContentResolver) {


    fun loadImages(): List<Album> {

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val imageList = mutableListOf<Image>()
        val images = mutableListOf<Album>()


        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        cursor = contentResolver.query(uri,
                arrayOf(MediaStore.MediaColumns.DATA,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.HEIGHT,
                        MediaStore.Images.Media.WIDTH),
                null,
                null,
                "$orderBy DESC")
        if (cursor != null) {
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            val columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val columnIndexHeight = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val columnIndexWidth = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)

            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(columnIndexData)

                val width = cursor.getInt(columnIndexWidth)
                val height = cursor.getInt(columnIndexHeight)
                if (width != 0 && height != 0) {
                    val element = Image(
                            folderName = cursor.getString(columnIndexFolderName),
                            imagePath = absolutePathOfImage,
                            width = width,
                            height = height,
                            imageId = absolutePathOfImage.hashCode())
                    imageList.add(element)
                }
            }

            cursor.close()
        }
        for (entry in imageList.groupBy { it.folderName }) {
            images.add(Album(entry.key, entry.value.map { it }))
        }
        return images
    }
}
