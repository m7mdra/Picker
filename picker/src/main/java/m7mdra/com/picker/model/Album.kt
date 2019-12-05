package m7mdra.com.picker.model

import android.os.Parcel
import android.os.Parcelable

data class Album(val folderName: String, val pathList: List<Image>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.createTypedArrayList(Image.CREATOR)!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(folderName)
        parcel.writeTypedList(pathList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }

}