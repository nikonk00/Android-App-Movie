package com.example.playmovie.utils

import android.content.Context
import com.example.playmovie.utils.Const.NAMA_USER
import com.example.playmovie.utils.Const.PREFS_NAME
import com.example.playmovie.utils.Const.STATUS_USER

class UserPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setStatusUser(value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(STATUS_USER, value)
        editor.apply()
    }

    fun getStatusUser(): Boolean {
        return preferences.getBoolean(STATUS_USER, false)
    }

    fun setNamaUser(value: String) {
        val editor = preferences.edit()
        editor.putString(NAMA_USER, value)
        editor.apply()
    }

    fun getNamaUser(): String? {
        return preferences.getString(NAMA_USER, "")
    }
}