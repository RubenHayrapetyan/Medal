package com.sub_zet.medal.models

import com.google.gson.annotations.SerializedName

class LoginModel(
        @SerializedName("status")
        val status: String,
        @SerializedName("unique_id")
        val uniqueId: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("tutorial_video")
        val videoTutorial: String
)