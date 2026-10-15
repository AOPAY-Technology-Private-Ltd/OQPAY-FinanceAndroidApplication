package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class VerifyCustomerReq(

	@field:SerializedName("primaryMobileNumber")
	val primaryMobileNumber: String? = null
)
