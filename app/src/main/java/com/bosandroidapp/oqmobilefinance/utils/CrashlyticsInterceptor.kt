package com.bosandroidapp.oqmobilefinance.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CrashlyticsInterceptor : Interceptor {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val startTime = System.currentTimeMillis()

        val apiName = request.url.encodedPath
        val method = request.method

        crashlytics.log(
            "API REQUEST: $method $apiName"
        )

        return try {

            val response = chain.proceed(request)

            val duration = System.currentTimeMillis() - startTime

            crashlytics.log(
                """
                API RESPONSE
                URL: $apiName
                Method: $method
                Status Code: ${response.code}
                Duration: ${duration}ms
                """.trimIndent()
            )

            response

        } catch (e: IOException) {

            val duration = System.currentTimeMillis() - startTime

            crashlytics.log(
                """
                API NETWORK ERROR
                URL: $apiName
                Method: $method
                Duration: ${duration}ms
                Error: ${e.message}
                """.trimIndent()
            )

            crashlytics.recordException(e)

            throw e
        }
    }
}