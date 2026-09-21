package com.bosandroidapp.oqmobilefinance.data.pg

import com.google.gson.annotations.SerializedName

data class GetOrderStatusOnlinePGRequest(

	@field:SerializedName("OrderId")
	val orderId: String? = null
)
