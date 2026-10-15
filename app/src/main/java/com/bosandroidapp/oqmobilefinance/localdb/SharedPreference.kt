package com.bosandroidapp.oqmobilefinance.localdb

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class SharedPreference(mContext: Context) {
    val prefFile = mContext.packageName
    private val preferences: SharedPreferences = mContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = preferences.edit()


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mMStash: SharedPreference? = null

        @Synchronized
        fun getInstance(context: Context): SharedPreference? {
            if (mMStash == null) {
                mMStash = SharedPreference(context.applicationContext)
            }
            return mMStash
        }

    }


    fun setBooleanValue(keyFlag: String?, value: Boolean) {
        preferences.edit().putBoolean(keyFlag, value).apply()
    }


    fun getBoolanValue(keyFlag: String?, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(keyFlag, defaultValue)
    }


    fun setStringValue(keyFlag: String?, value: String) {
        preferences.edit().putString(keyFlag, value).apply()
    }


    fun getStringValue(keyFlag: String?, defaultValue: String): String {
        return preferences.getString(keyFlag, "")!!
    }

    fun setIntValue(keyFlag: String?, value: Int) {
        preferences.edit().putInt(keyFlag, value).apply()
    }

    fun getIntValue(keyFlag: String?, defaultValue: Int): Int {
        return preferences.getInt(keyFlag, defaultValue)
    }

    fun setLongValue(keyFlag: String?, value: Long) {
        preferences.edit().putLong(keyFlag, value).apply()
    }

    fun getLongValue(keyFlag: String?, defaultValue: Long): Long {
        return preferences.getLong(keyFlag, defaultValue)
    }



}