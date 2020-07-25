package com.sub_zet.medal.models

import com.google.gson.annotations.SerializedName

class ProfileModel(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("image")
        val imageURL: String,
        @SerializedName("balance")
        val balance: String,
        @SerializedName("games_rating")
        val gamesRatingArray: ArrayList<ProfileGameRatingModel>
)