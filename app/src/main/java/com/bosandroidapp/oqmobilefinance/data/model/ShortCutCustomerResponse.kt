package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class ShortCutCustomerResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<CustomerStepDataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CustomerStepDataItem(

	@field:SerializedName("customerStatus")
	val customerStatus: String? = null,

	@field:SerializedName("currentStep")
	val currentStep: String? = null,

	@field:SerializedName("mobileNumber")
	val mobileNumber: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("customerImage")
	val customerImage: String? = null
)
