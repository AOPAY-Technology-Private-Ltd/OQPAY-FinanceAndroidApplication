package com.bosandroidapp.oqmobilefinance.data.gst

import com.google.gson.annotations.SerializedName

data class GstResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("result_code")
	val resultCode: Int? = null,

	@field:SerializedName("client_ref_num")
	val clientRefNum: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("request_id")
	val requestId: String? = null,

	@field:SerializedName("http_response_code")
	val httpResponseCode: Int? = null
)

data class Result(

	@field:SerializedName("goods_service")
	val goodsService: GoodsService? = null,

	@field:SerializedName("taxpayerDetails")
	val taxpayerDetails: TaxpayerDetails? = null,

	@field:SerializedName("taxpayerReturnDetails")
	val taxpayerReturnDetails: TaxpayerReturnDetails? = null
)

data class GoodsService(

	@field:SerializedName("bzgddtls")
	val bzgddtls: List<Any?>? = null,

	@field:SerializedName("bzsdtls")
	val bzsdtls: List<BzsdtlsItem?>? = null
)

data class TaxpayerReturnDetails(

	@field:SerializedName("filingStatus")
	val filingStatus: List<FilingStatusItem?>? = null,

	@field:SerializedName("array_length")
	val arrayLength: Int? = null
)

data class Pradr(

	@field:SerializedName("adr")
	val adr: String? = null
)

data class BzsdtlsItem(

	@field:SerializedName("saccd")
	val saccd: String? = null,

	@field:SerializedName("sdes")
	val sdes: String? = null
)

data class TaxpayerDetails(

	@field:SerializedName("ntcrbs")
	val ntcrbs: String? = null,

	@field:SerializedName("adhrVFlag")
	val adhrVFlag: String? = null,

	@field:SerializedName("lgnm")
	val lgnm: String? = null,

	@field:SerializedName("stj")
	val stj: String? = null,

	@field:SerializedName("dty")
	val dty: String? = null,

	@field:SerializedName("cxdt")
	val cxdt: String? = null,

	@field:SerializedName("gstin")
	val gstin: String? = null,

	@field:SerializedName("nba")
	val nba: List<String?>? = null,

	@field:SerializedName("ekycVFlag")
	val ekycVFlag: String? = null,

	@field:SerializedName("cmpRt")
	val cmpRt: String? = null,

	@field:SerializedName("rgdt")
	val rgdt: String? = null,

	@field:SerializedName("ctb")
	val ctb: String? = null,

	@field:SerializedName("pradr")
	val pradr: Pradr? = null,

	@field:SerializedName("sts")
	val sts: String? = null,

	@field:SerializedName("tradeNam")
	val tradeNam: String? = null,

	@field:SerializedName("isFieldVisitConducted")
	val isFieldVisitConducted: String? = null,

	@field:SerializedName("ctj")
	val ctj: String? = null,

	@field:SerializedName("einvoiceStatus")
	val einvoiceStatus: String? = null
)

data class FilingStatusItem(

	@field:SerializedName("fy")
	val fy: String? = null,

	@field:SerializedName("taxp")
	val taxp: String? = null,

	@field:SerializedName("mof")
	val mof: String? = null,

	@field:SerializedName("dof")
	val dof: String? = null,

	@field:SerializedName("rtntype")
	val rtntype: String? = null,

	@field:SerializedName("arn")
	val arn: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
