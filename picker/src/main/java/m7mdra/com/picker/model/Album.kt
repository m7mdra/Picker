package m7mdra.com.picker.model

import java.io.Serializable

data class Album(val folderName: String, val pathList: List<Image>) :Serializable