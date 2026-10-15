package com.bosandroidapp.oqmobilefinance.network

import android.content.Intent
import android.util.Log
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.utils.ApplicationClass
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Skip adding token for public endpoints
        val path = originalRequest.url.encodedPath
        val isPublicEndpoint = path.contains("Login") || 
                path.contains("Registration") || 
                path.contains("GetDeviceInformation") ||
                path.contains("RefreshToken") || 
                path.contains("ForgotPassword") || 
                path.contains("SendOTP") ||
                path.contains("VerifyOTP")

        if (!isPublicEndpoint) {
            val preference = SharedPreference(ApplicationClass.instance)
            val token = preference.getStringValue(ConstantClass.AccessToken, "")
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }

        val response = chain.proceed(requestBuilder.build())

        if (response.code == 403) {
            Log.e("AuthInterceptor", "403 Forbidden - Redirecting to login")
            
            val preference = SharedPreference(ApplicationClass.instance)
            preference.setBooleanValue(ConstantClass.LoggedIn, false)
            preference.setStringValue(ConstantClass.AccessToken, "")
            preference.setStringValue(ConstantClass.RefreshToken, "")

            val intent = Intent(ApplicationClass.instance, ChooseYourRolePage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            ApplicationClass.instance.startActivity(intent)
        }

        return response
    }
}