package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class EmandateOptionSelectetionResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<EmandateSelectDataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class EmandateSelectDataItem(

	@field:SerializedName("apiName")
	val apiName: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null,

	@field:SerializedName("vender")
	val vender: String? = null
)
