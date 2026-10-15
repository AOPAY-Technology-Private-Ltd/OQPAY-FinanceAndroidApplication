package com.bosandroidapp.oqmobilefinance.data.notification

import com.google.gson.annotations.SerializedName

data class SendNotificationFeatureNameResp(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
