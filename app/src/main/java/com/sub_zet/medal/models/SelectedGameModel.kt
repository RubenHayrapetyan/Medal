package com.sub_zet.medal.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class SelectedGameModel (
        @SerializedName("id")
        val selectedGameId: String,
        @SerializedName("description")
        val selectedGameDescription: String,
        @SerializedName("url_image")
        val selectedGameImgURL: String,
        @SerializedName("url_video")
        val selectedGameVideoUrl: String,
        @SerializedName("game_rating")
        val gameRating: String,
        @SerializedName("balance")
        val balance: String,
        @SerializedName("user_picture")
        val userPicture: String,
        @SerializedName("price")
        val selectedGameBetPriceArray: ArrayList<BetPriceModel>
)