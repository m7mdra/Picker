package m7mdra.com.picker.model

import android.os.Parcel
import android.os.Parcelable

data class Image(var folderName: String,
                 var imagePath: String,
                 var height: Int = 0,
                 var width: Int = 0,
                 var imageId: Int) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()!!,
            source.readString()!!,
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(folderName)
        writeString(imagePath)
        writeInt(height)
        writeInt(width)
        writeInt(imageId)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(source: Parcel): Image = Image(source)
            override fun newArray(size: Int): Array<Image?> = arrayOfNulls(size)
        }
    }
}