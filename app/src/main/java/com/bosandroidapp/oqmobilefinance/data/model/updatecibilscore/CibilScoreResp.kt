package com.bosandroidapp.oqmobilefinance.data.model.updatecibilscore

import com.google.gson.annotations.SerializedName

data class CibilScoreResp(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("credit_score")
	val creditScore: Int? = null,

	@field:SerializedName("ApiResponseLog")
	val apiResponseLog: String? = null,

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("date_of_birth")
	val dateOfBirth: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("Value")
	val value: String? = null,

	@field:SerializedName("pan")
	val pan: String? = null,

	@field:SerializedName("score_band")
	val scoreBand: String? = null,

	@field:SerializedName("report_date")
	val reportDate: String? = null
)
