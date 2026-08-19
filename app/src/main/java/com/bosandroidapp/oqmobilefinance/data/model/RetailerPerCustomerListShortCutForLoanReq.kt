package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class RetailerPerCustomerListShortCutForLoanReq(

	@field:SerializedName("retailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("searchText")
	val searchText: String? = null,

	@field:SerializedName("recordStatus")
	val recordStatus: String? = null

)
