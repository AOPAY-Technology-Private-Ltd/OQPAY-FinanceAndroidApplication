package com.bosandroidapp.oqmobilefinance.data.upiautomandate

import com.google.gson.annotations.SerializedName

data class UpiAutoTransactionRequest(

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null,

	@field:SerializedName("Amount")
	val amount: Int? = null,

	@field:SerializedName("EMINumbers")
	val eMINumbers: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("LoanCode")
	val loanCode: String? = null
)
