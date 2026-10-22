package com.bosandroidapp.aopayfinance.data.model.customerreq

import com.google.gson.annotations.SerializedName

data class CustomerDeviceLockStatusResp(

	@field:SerializedName("data")
	val data: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
