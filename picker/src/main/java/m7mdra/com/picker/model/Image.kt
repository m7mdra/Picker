package m7mdra.com.picker.model

import java.io.Serializable

data class Image(var folderName: String, var imagePath: String, var height: Int = 0, var width: Int = 0) : Serializable