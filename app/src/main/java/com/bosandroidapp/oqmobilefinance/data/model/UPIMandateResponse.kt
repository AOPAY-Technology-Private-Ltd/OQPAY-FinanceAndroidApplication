package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class UPIMandateResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("State")
	val state: Any? = null,

	@field:SerializedName("IntentUrl")
	val intentUrl: String? = null,

	@field:SerializedName("OrderID")
	val orderID: String? = null,

	@field:SerializedName("ErrorMessage")
	val errorMessage: Any? = null,

	@field:SerializedName("MarchentOrderID")
	val marchentOrderID: String? = null,

	@field:SerializedName("ExpiryAt")
	val expiryAt: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
