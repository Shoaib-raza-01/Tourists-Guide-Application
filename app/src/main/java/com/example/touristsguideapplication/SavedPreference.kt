package com.example.touristsguideapplication

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.prefs.Preferences

object SavedPreference {
    const val EMAIL = "email"
    const val USERNAME = "username"

    private fun getSharedPreference(context:Context):SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    private fun editor(context: Context, const: String, string: String){
        getSharedPreference(context).edit().putString(const,string).apply()
    }
    fun getEmail(context: Context) = getSharedPreference(
        context
    ).getString(EMAIL,"")

    fun setEmail(context: Context, email:String){
        editor(context, EMAIL, email)
    }
    fun getUsername(context: Context)= getSharedPreference(
        context
    ).getString(USERNAME,"")

    fun setUsername(context: Context,username:String){
        editor(context, USERNAME, username)
    }
}