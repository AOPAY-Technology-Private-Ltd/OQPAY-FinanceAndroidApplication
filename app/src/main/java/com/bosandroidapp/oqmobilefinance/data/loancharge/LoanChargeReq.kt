package com.bosandroidapp.oqmobilefinance.data.loancharge

import com.google.gson.annotations.SerializedName

data class LoanChargeReq(

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null,

	@field:SerializedName("RetailerCode")
	val retailerCode: String? = null
)
