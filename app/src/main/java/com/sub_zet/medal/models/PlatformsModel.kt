package com.sub_zet.medal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Constructor

@Entity(tableName = "platforms")
class PlatformsModel(
        @PrimaryKey
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val platformName: String,
        @SerializedName("image")
        val platformIconUrl: String,
        var isSelected: Boolean = false

){
    constructor(id: String , platformName: String , platformIconUrl: String) : this(
            id, platformName , platformIconUrl , false)

}