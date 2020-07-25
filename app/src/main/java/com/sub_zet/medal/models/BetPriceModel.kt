package com.sub_zet.medal.models

import com.google.gson.annotations.SerializedName

class BetPriceModel (
        @SerializedName("id")
        val id: String,
        @SerializedName("price")
        val price : String)