package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class RetailerLoginOtpResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: RetailerLoginData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class RetailerLoginData(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("aadharNumber")
	val aadharNumber: String? = null,

	@field:SerializedName("activeStatus")
	val activeStatus: String? = null,

	@field:SerializedName("isOTPRequired")
	val isOTPRequired: Boolean? = null,

	@field:SerializedName("remainingSeconds")
	val remainingSeconds: Int? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("emailID")
	val emailID: String? = null,

	@field:SerializedName("retailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("mobileNo")
	val mobileNo: String? = null,

	@field:SerializedName("panNumber")
	val panNumber: String? = null
)
