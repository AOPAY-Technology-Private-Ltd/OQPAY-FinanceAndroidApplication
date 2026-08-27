package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class UpdateCustomerDataIfAlreadyExistResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: AlreadyCustomerData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class AlreadyCustomerData(

	@field:SerializedName("currentStep")
	val currentStep: Int? = null,

	@field:SerializedName("activeStatus")
	val activeStatus: String? = null,

	@field:SerializedName("cibilScore")
	val cibilScore: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("rid")
	val rid: Int? = null
)
