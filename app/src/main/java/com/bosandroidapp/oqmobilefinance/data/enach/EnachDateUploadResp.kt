package com.bosandroidapp.oqmobilefinance.data.enach

import com.google.gson.annotations.SerializedName

data class EnachDateUploadResp(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("success")
	val success: String? = null,

	@field:SerializedName("code")
	val code: Int? = null
)
