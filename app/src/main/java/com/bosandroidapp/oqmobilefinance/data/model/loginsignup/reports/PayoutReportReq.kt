package com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports

import com.google.gson.annotations.SerializedName

data class PayoutReportReq(

	@field:SerializedName("registrationId")
	val registrationId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
