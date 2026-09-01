package com.bosandroidapp.oqmobilefinance.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bosandroidapp.oqmobilefinance.data.model.GetOrderStatusOnlinePGRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.PanVerificationReq
import com.bosandroidapp.oqmobilefinance.data.pg.PGOnlineRequestCall
import com.bosandroidapp.oqmobilefinance.data.repository.DikshifinsureRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.utils.ApiResponse
import kotlinx.coroutines.Dispatchers

class DikshifinsureViewModel(private val repository: DikshifinsureRepository) : ViewModel() {

    fun getPGRequestCallOnline(req: PGOnlineRequestCall) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(ApiResponse.success(data = repository.getPGRequestCallOnline(req)))
        }
        catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }


    fun getOrderOnlineStatusPgRequest(req: GetOrderStatusOnlinePGRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(ApiResponse.success(data = repository.getOrderOnlineStatusPgRequest(req)))
        }
        catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }



}