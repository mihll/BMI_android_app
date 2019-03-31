package com.example.bmi

import android.content.Context
import android.content.SharedPreferences
import com.example.bmi.Logic.BmiEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreference(myContext: Context) {

    companion object {
        const val SHARED_PREFERENCE = "SHARED_PREFERENCE"
    }

    private val sharedPref: SharedPreferences = myContext.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

    fun save(keyName: String, list: MutableList<BmiEntry>) {
        val listJson = Gson().toJson(list)
        val editor = sharedPref.edit()
        editor.putString(keyName, listJson)
        editor.apply()
    }

    fun getValueList(keyName: String): MutableList<BmiEntry> {
        class Token : TypeToken<MutableList<BmiEntry>>()
        val keyList = sharedPref.getString(keyName, null)
        if (keyList != null) {
            return Gson().fromJson(keyList, Token().type)
        }
        return mutableListOf()
    }
}