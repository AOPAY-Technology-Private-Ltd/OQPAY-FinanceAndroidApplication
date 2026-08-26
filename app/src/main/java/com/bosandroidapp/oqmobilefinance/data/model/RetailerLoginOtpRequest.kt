package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class RetailerLoginOtpRequest(

	@field:SerializedName("retailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("otp")
	val otp: String? = null,

	@field:SerializedName("deviceId")
	val deviceId: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
