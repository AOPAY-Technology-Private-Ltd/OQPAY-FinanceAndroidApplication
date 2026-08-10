package com.bosandroidapp.oqmobilefinance.data.repository

import com.bos.payment.appName.network.ApiInterface
import com.bosandroidapp.oqmobilefinance.data.pg.PGOnlineRequestCall

class DikshifinsureRepository (private val apiInterface: ApiInterface){

     suspend fun getPGRequestCallOnline(req: PGOnlineRequestCall) = apiInterface.callPGOnline(req)

}