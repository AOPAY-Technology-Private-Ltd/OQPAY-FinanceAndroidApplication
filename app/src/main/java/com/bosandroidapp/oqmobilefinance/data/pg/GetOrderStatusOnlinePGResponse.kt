package com.bosandroidapp.oqmobilefinance.data.pg

import com.google.gson.annotations.SerializedName

data class GetOrderStatusOnlinePGResponse(

	@field:SerializedName("payableCurrency")
	val payableCurrency: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("orderId")
	val orderId: String? = null,

	@field:SerializedName("feeCurrency")
	val feeCurrency: String? = null,

	@field:SerializedName("merchantOrderId")
	val merchantOrderId: String? = null,

	@field:SerializedName("expireAt")
	val expireAt: Long? = null,

	@field:SerializedName("payableAmount")
	val payableAmount: Int? = null,

	@field:SerializedName("feeAmount")
	val feeAmount: Int? = null,

	@field:SerializedName("metaInfo")
	val metaInfo: MetaInfo? = null,

	@field:SerializedName("merchantId")
	val merchantId: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("paymentDetails")
	val paymentDetails: List<PaymentDetailsItem?>? = null
)

data class PaymentDetailsItem(

	@field:SerializedName("payableCurrency")
	val payableCurrency: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("paymentMode")
	val paymentMode: String? = null,

	@field:SerializedName("rail")
	val rail: Rail? = null,

	@field:SerializedName("feeCurrency")
	val feeCurrency: String? = null,

	@field:SerializedName("splitInstruments")
	val splitInstruments: List<SplitInstrumentsItem?>? = null,

	@field:SerializedName("instrument")
	val instrument: Instrument? = null,

	@field:SerializedName("transactionId")
	val transactionId: String? = null,

	@field:SerializedName("payableAmount")
	val payableAmount: Int? = null,

	@field:SerializedName("feeAmount")
	val feeAmount: Int? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: Long? = null
)

data class MetaInfo(

	@field:SerializedName("udf3")
	val udf3: String? = null,

	@field:SerializedName("udf4")
	val udf4: String? = null,

	@field:SerializedName("udf1")
	val udf1: String? = null,

	@field:SerializedName("udf2")
	val udf2: String? = null
)

data class SplitInstrumentsItem(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("rail")
	val rail: Rail? = null,

	@field:SerializedName("instrument")
	val instrument: Instrument? = null,

	@field:SerializedName("currency")
	val currency: String? = null
)

data class Rail(

	@field:SerializedName("utr")
	val utr: String? = null,

	@field:SerializedName("vpa")
	val vpa: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("upiTransactionId")
	val upiTransactionId: String? = null
)

data class Instrument(

	@field:SerializedName("accountType")
	val accountType: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("ifsc")
	val ifsc: String? = null,

	@field:SerializedName("accountHolderName")
	val accountHolderName: String? = null
)
