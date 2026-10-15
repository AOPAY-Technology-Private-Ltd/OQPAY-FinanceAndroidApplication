package com.bosandroidapp.oqmobilefinance.data.upiautomandate

import com.google.gson.annotations.SerializedName

data class UpiAutoOrderStatusRequest(

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null,

	@field:SerializedName("MerchantOrderId")
	val merchantOrderId: String? = null
)
