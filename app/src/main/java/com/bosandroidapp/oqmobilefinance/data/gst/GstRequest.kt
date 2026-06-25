package com.bosandroidapp.oqmobilefinance.data.gst

import com.google.gson.annotations.SerializedName

data class GstRequest(

	@field:SerializedName("GSTNumber")
	val gSTNumber: String? = null,

	@field:SerializedName("RegistrationID")
	val registrationID: String? = null
)
