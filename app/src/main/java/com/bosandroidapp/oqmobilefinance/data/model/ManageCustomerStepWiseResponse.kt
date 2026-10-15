package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class ManageCustomerStepWiseResponse(

	@SerializedName("code")
	val code: Int? = null,

	@SerializedName("data")
	val data: CustomerRegistrationData? = null,

	@SerializedName("success")
	val success: Boolean? = null,

	@SerializedName("message")
	val message: String? = null
)

data class CustomerRegistrationData(

	@SerializedName("customerCode")
	val customerCode: String? = null,

	@SerializedName("rid")
	val rid: Int? = null
)
