package com.sub_zet.medal.models

import com.google.gson.annotations.SerializedName

class ProfileGameRatingModel(
        @SerializedName("name")
        val gameName: String,
        @SerializedName("rating")
        val gameRating: String
)