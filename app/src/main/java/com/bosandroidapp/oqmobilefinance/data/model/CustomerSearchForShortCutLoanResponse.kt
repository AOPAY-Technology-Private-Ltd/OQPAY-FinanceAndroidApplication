package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class CustomerSearchForShortCutLoanResponse(

    @field:SerializedName("code")
	val code: Int? = null,

    @field:SerializedName("data")
	val data: List<CustomerShortCutDataItem> = emptyList(),

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("status")
	val status: Boolean? = null

)


