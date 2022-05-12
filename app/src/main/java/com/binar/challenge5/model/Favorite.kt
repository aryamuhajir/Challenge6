package com.binar.challenge5.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var idFav : Int?,
    @ColumnInfo(name = "id")
    var id : String?,
    @ColumnInfo(name = "name")
    var name : String?,
    @ColumnInfo(name = "image")
    var image : String?,
    @ColumnInfo(name = "director")
    var director : String?,
    @ColumnInfo(name = "description")
    var description : String?,
    @ColumnInfo(name = "date")
    var date : String?,
) : Parcelable


