package com.example.androidmemorygame.data

import android.os.Parcel
import android.os.Parcelable

data class GameSettings (val speed:Double,
                         val gridSize:Int):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(speed)
        parcel.writeInt(gridSize)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameSettings> {
        override fun createFromParcel(parcel: Parcel): GameSettings {
            return GameSettings(parcel)
        }

        override fun newArray(size: Int): Array<GameSettings?> {
            return arrayOfNulls(size)
        }
    }

}