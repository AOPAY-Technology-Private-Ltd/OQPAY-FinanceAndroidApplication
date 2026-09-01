package com.bosandroidapp.oqmobilefinance.data.repository

import com.bosandroidapp.oqmobilefinance.network.ApiInterface
import com.bosandroidapp.oqmobilefinance.data.model.GetOrderStatusOnlinePGRequest
import com.bosandroidapp.oqmobilefinance.data.pg.PGOnlineRequestCall

class DikshifinsureRepository (private val apiInterface: ApiInterface){

     suspend fun getPGRequestCallOnline(req: PGOnlineRequestCall) = apiInterface.callPGOnline(req)
     suspend fun getOrderOnlineStatusPgRequest(req: GetOrderStatusOnlinePGRequest) = apiInterface.getOrderOnlineStatusPgRequest(req)

}