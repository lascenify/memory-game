package com.example.androidmemorygame.data

import android.icu.text.CaseMap
import android.os.Parcel
import android.os.Parcelable

data class MemoryCard (val id:Long, val title:String?, val image:String?):Parcelable{
    var isVisible = false

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    ) {
        isVisible = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeByte(if (isVisible) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemoryCard> {
        override fun createFromParcel(parcel: Parcel): MemoryCard {
            return MemoryCard(parcel)
        }

        override fun newArray(size: Int): Array<MemoryCard?> {
            return arrayOfNulls(size)
        }
    }
}