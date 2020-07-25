package com.sub_zet.medal.helpers

import android.content.Context
import android.content.SharedPreferences

class MyUniqueID(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val KEY_UNIQUE_ID = "uniqueID"

    init {
        sharedPreferences = context.getSharedPreferences(KEY_UNIQUE_ID, Context.MODE_PRIVATE)
    }

    fun loadUniqueId(): String? {
        return sharedPreferences.getString(KEY_UNIQUE_ID, "No unique id")
    }

    fun saveUniqueID(data: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_UNIQUE_ID, data)
        editor.apply()
    }
}