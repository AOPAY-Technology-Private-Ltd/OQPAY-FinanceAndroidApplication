package com.bosandroidapp.oqmobilefinance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

import com.bosandroidapp.bosmobilefinance.ui.slideshow.data.model.loginsignup.cibilscore.CibilScoreReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AAdhaarDetailesReq
import com.bosandroidapp.oqmobilefinance.data.repository.CibilRepository
import com.bosandroidapp.oqmobilefinance.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CibilViewModel (private val repository: CibilRepository): ViewModel(){

    fun getCibilReq(req: CibilScoreReq) = liveData(Dispatchers.IO) {

        emit(ApiResponse.loading(data = null))

        try {
            emit(ApiResponse.success(data = repository.getReportsReq(req)))
        }
        catch (exception: HttpException) {

            when (exception.code()) {

                404 -> {
                    emit(ApiResponse.error(data = null, message = "Page not found"))
                }

                500 -> {
                    emit(ApiResponse.error(data = null, message = "Server error"))
                }

                else -> {
                    emit(ApiResponse.error(data = null, message = "Something went wrong"))
                }
            }

        }
        catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }


    fun getAAdhaarDetailesReq(req: AAdhaarDetailesReq) = liveData(Dispatchers.IO) {

        emit(ApiResponse.loading(data = null))

        try {

            emit(
                ApiResponse.success(
                    data = repository.getAadharDetailsReq(req)
                )
            )

        } catch (exception: HttpException) {

            val errorMessage = when (exception.code()) {

                400 -> "Bad request"

                401 -> "Unauthorized access"

                403 -> "Access forbidden"

                404 -> "Data not found"

                405 -> "Method not allowed"

                408 -> "Request timeout"

                409 -> "Conflict occurred"

                422 -> "Validation failed"

                429 -> "Too many requests"

                500 -> "Internal server error"

                502 -> "Bad gateway"

                503 -> "Service unavailable"

                504 -> "Gateway timeout"

                else -> "Something went wrong"
            }

            emit(ApiResponse.error(data = null, message = errorMessage))

        } catch (exception: SocketTimeoutException) {

            emit(ApiResponse.error(data = null, message = "Connection timeout"))

        } catch (exception: UnknownHostException) {

            emit(ApiResponse.error(data = null, message = "No internet connection"))

        } catch (exception: IOException) {

            emit(ApiResponse.error(data = null, message = "Network error"))

        } catch (exception: Exception) {

            emit(
                ApiResponse.error(
                    data = null,
                    message = exception.message ?: "Error Occurred!"
                )
            )
        }
    }





}