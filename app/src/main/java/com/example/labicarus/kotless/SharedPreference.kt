package com.example.labicarus.kotless

import android.content.Context
import android.content.SharedPreferences

class SharedPreference (context: Context) {

    private val PREFS_NAME = "kotless"
    val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: String?){
        val editor : SharedPreferences.Editor = sharedPrefs.edit()
        editor.putString(KEY_NAME, value)
        editor.apply()
    }

    fun getValueString(KEY_NAME: String?): String? {
        return sharedPrefs.getString(KEY_NAME, null)
    }

    fun clear(){
        val editor: SharedPreferences.Editor = sharedPrefs.edit()

        editor.clear()
        editor.apply()
    }

    fun remove(KEY_NAME: String){
        val editor: SharedPreferences.Editor = sharedPrefs.edit()

        editor.remove(KEY_NAME)
        editor.apply()
    }

}