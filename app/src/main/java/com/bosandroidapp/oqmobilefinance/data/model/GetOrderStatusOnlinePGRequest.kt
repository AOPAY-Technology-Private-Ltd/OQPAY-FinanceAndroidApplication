package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class GetOrderStatusOnlinePGRequest(

	@field:SerializedName("OrderId")
	val orderId: String? = null
)
