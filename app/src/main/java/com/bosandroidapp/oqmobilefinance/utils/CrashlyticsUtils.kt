package com.bosandroidapp.oqmobilefinance.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsUtils {

    fun setUserId(userId: String) {
        try {
            if (userId.isNotBlank()) {
                FirebaseCrashlytics.getInstance().setUserId(userId)
            }
        } catch (e: Exception) {
            Log.e("CrashlyticsUtils", "Error setting userId", e)
        }
    }

    fun setCustomKey(key: String, value: String) {
        try {
            FirebaseCrashlytics.getInstance().setCustomKey(key, value)
        } catch (e: Exception) {
            Log.e("CrashlyticsUtils", "Error setting custom key", e)
        }
    }


    fun log(message: String) {
        try {
            FirebaseCrashlytics.getInstance().log(message)
        } catch (e: Exception) {
            Log.e("CrashlyticsUtils", "Error logging message", e)
        }
    }

    fun logException(throwable: Throwable) {
        try {
            FirebaseCrashlytics.getInstance().recordException(throwable)
        } catch (e: Exception) {
            Log.e("CrashlyticsUtils", "Error recording exception", e)
        }
    }

}
