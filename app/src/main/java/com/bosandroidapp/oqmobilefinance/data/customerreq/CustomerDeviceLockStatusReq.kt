package com.bosandroidapp.aopayfinance.data.model.customerreq

import com.google.gson.annotations.SerializedName

data class CustomerDeviceLockStatusReq(

	@field:SerializedName("DeviceModel")
	val deviceModel: String? = null,

	@field:SerializedName("Status")
	val status: Boolean? = null,

	@field:SerializedName("AdditionalData")
	val additionalData: String? = null,

	@field:SerializedName("ActionType")
	val actionType: String? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("RequestId")
	val requestId: String? = null,

	@field:SerializedName("CustomerCode")
	val customerCode: String? = null,

	@field:SerializedName("DeviceId")
	val deviceId: String? = null,

	@field:SerializedName("AndroidVersion")
	val androidVersion: String? = null,

	@field:SerializedName("RetailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("AppVersion")
	val appVersion: String? = null,

	@field:SerializedName("ActionName")
	val actionName: String? = null,

	@field:SerializedName("ClientCode")
	val clientCode: String? = null,

	@field:SerializedName("Manufacturer")
	val manufacturer: String? = null,

	@field:SerializedName("ErrorCode")
	val errorCode: String? = null,

	@field:SerializedName("ErrorMessage")
	val errorMessage: String? = null,

	@field:SerializedName("PerformedAt")
	val performedAt: String? = null,

	@field:SerializedName("AppPackageName")
	val appPackageName: String? = null
)
