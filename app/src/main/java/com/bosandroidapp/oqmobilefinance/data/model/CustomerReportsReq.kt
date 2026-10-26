package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class CustomerReportsReq(

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("loanCode")
	val loanCode: String? = null,

	@field:SerializedName("recordStatus")
	val recordStatus: String? = null
)
