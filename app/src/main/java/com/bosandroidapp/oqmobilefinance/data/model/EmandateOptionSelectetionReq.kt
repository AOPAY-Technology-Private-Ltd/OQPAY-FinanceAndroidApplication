package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class EmandateOptionSelectetionReq(

	@field:SerializedName("mode")
	val mode: String? = null,

	@field:SerializedName("registrationID")
	val registrationID: String? = null
)
