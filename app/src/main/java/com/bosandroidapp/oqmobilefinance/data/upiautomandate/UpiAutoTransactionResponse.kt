package com.bosandroidapp.oqmobilefinance.data.upiautomandate

import com.google.gson.annotations.SerializedName

data class UpiAutoTransactionResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("State")
	val state: String? = null,

	@field:SerializedName("IntentUrl")
	val intentUrl: String? = null,

	@field:SerializedName("OrderID")
	val orderID: String? = null,

	@field:SerializedName("ErrorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("MarchentOrderID")
	val marchentOrderID: String? = null,

	@field:SerializedName("ExpiryAt")
	val expiryAt: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
