package com.bosandroidapp.oqmobilefinance.data.pg

import com.google.gson.annotations.SerializedName

data class PGOnlineRequestCall(

	@field:SerializedName("Amount")
	val amount: Double? = null,

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null,

	@field:SerializedName("EMINumbers")
	val eMINumbers: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("LoanCode")
	val loanCode: String? = null
)
