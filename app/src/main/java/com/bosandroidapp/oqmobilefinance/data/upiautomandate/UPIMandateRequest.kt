package com.bosandroidapp.oqmobilefinance.data.upiautomandate

import com.google.gson.annotations.SerializedName

data class UPIMandateRequest(

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("LoanCode")
	val loanCode: String? = null,

	@field:SerializedName("EMIAmount")
	val emiAmount: Double? = null,

	@field:SerializedName("LastEMIDueDate")
	val lastEMIDueDate: String? = null,

)
