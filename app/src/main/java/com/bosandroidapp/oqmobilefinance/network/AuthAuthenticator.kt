package com.bosandroidapp.oqmobilefinance.network

import android.util.Log
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.generattoken.RefreshTokenRequest
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.utils.ApplicationClass
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AuthAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val preference = SharedPreference(ApplicationClass.instance)
        val refreshToken = preference.getStringValue(ConstantClass.RefreshToken, "")

        if (refreshToken.isEmpty()) return null

        synchronized(this) {
            // Check if token was already refreshed by another thread
            val currentToken = preference.getStringValue(ConstantClass.AccessToken, "")
            val requestToken = response.request.header("Authorization")?.replace("Bearer ", "")
            
            if (currentToken != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }

            // Call refresh token API synchronously
            val newToken = getNewToken(refreshToken)
            return if (newToken != null) {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            } else {
                null
            }
        }
    }

    private fun getNewToken(refreshToken: String): String? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ConstantClass.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        val service = retrofit.create(ApiInterface::class.java)
        
        return try {
            val response = runCatching {
                // Using runBlocking or executing synchronously
                // Since this is called from OkHttp's background thread, we can use execute()
                // But ApiInterface uses suspend functions.
                // I might need a non-suspend version or use runBlocking.
                // However, Retrofit's Authenticator is expected to be synchronous.
                
                // Let's check if I can add a non-suspend version to ApiInterface or use a separate interface.
                // For now, I'll try to find if there's a way to call suspend from here or use a synchronous call.
                
                // Actually, I'll just use OkHttp directly for simplicity and to avoid Coroutine dependencies here if possible.
                null as String? // Placeholder
            }.getOrNull()
            
            // Re-implementing with direct OkHttp call to avoid suspend issue in Authenticator
            val mediaType = "application/json".toMediaTypeOrNull()
            val requestBody = "{\"refreshToken\":\"$refreshToken\"}".toRequestBody(mediaType)
            
            val request = Request.Builder()
                .url(ConstantClass.BASE_URL + "api/V1/OQFinance/RefreshToken")
                .post(requestBody)
                .build()
                
            val okResponse = okHttpClient.newCall(request).execute()

            if (okResponse.isSuccessful) {
                val body = okResponse.body?.string()
                val refreshResponse = com.google.gson.Gson().fromJson(body, com.bosandroidapp.oqmobilefinance.data.generattoken.RefreshTokenResponse::class.java)
                if (refreshResponse != null && refreshResponse.success == true) {
                    val preference = SharedPreference(ApplicationClass.instance)
                    preference.setStringValue(ConstantClass.AccessToken, refreshResponse.accessToken ?: "")
                    preference.setStringValue(ConstantClass.RefreshToken, refreshResponse.refreshToken ?: "")
                    refreshResponse.accessToken
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthAuthenticator", "Error refreshing token", e)
            null
        }
    }
}