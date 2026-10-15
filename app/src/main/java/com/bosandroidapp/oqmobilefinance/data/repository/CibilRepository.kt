package com.bosandroidapp.oqmobilefinance.data.repository

import com.bosandroidapp.oqmobilefinance.network.ApiInterface
import com.bosandroidapp.bosmobilefinance.ui.slideshow.data.model.loginsignup.cibilscore.CibilScoreReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AAdhaarDetailesReq

class CibilRepository(private val apiInterface: ApiInterface) {

    suspend fun getReportsReq(req: CibilScoreReq) = apiInterface.getcibilscore(req)

    suspend fun getAadharDetailsReq(req: AAdhaarDetailesReq) = apiInterface.getAadharDetails(req)



}