package com.bosandroidapp.oqmobilefinance.data.model.loginsignup

import com.google.gson.annotations.SerializedName

data class EligibleLoanResp(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("statuss")
	val statuss: String? = null,

	@field:SerializedName("value")
	val value: Value? = null
)

data class Value(

	@field:SerializedName("refRelationShip")
	val refRelationShip: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("custPanNumberPhoto_File")
	val custPanNumberPhotoFile: String? = null,

	@field:SerializedName("aadharNumberVerified")
	val aadharNumberVerified: String? = null,

	@field:SerializedName("refPanNumber")
	val refPanNumber: String? = null,

	@field:SerializedName("refAdhaarNumberBackPhoto_Path")
	val refAdhaarNumberBackPhotoPath: String? = null,

	@field:SerializedName("custAadharPhoto_File")
	val custAadharPhotoFile: String? = null,

	@field:SerializedName("eMailID")
	val eMailID: String? = null,

	@field:SerializedName("stateName")
	val stateName: String? = null,

	@field:SerializedName("aearSector")
	val aearSector: String? = null,

	@field:SerializedName("bankIFSCCode")
	val bankIFSCCode: String? = null,

	@field:SerializedName("refName")
	val refName: String? = null,

	@field:SerializedName("refAdhaarNumberFrontPhoto_Path")
	val refAdhaarNumberFrontPhotoPath: String? = null,

	@field:SerializedName("accountType")
	val accountType: String? = null,

	@field:SerializedName("custAadharBackPhoto_File")
	val custAadharBackPhotoFile: String? = null,

	@field:SerializedName("refPanNumberPhoto_Path")
	val refPanNumberPhotoPath: String? = null,

	@field:SerializedName("panNumber")
	val panNumber: String? = null,

	@field:SerializedName("pAlternateMobileVerified")
	val pAlternateMobileVerified: String? = null,

	@field:SerializedName("custPhoto_File")
	val custPhotoFile: Any? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("flatNo")
	val flatNo: String? = null,

	@field:SerializedName("pinCode")
	val pinCode: String? = null,

	@field:SerializedName("retailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("refPanNumberPhoto_File")
	val refPanNumberPhotoFile: Any? = null,

	@field:SerializedName("custAadharBackPhoto_Path")
	val custAadharBackPhotoPath: String? = null,

	@field:SerializedName("refAdhaarNumberFrontPhoto_File")
	val refAdhaarNumberFrontPhotoFile: Any? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("bankName")
	val bankName: String? = null,

	@field:SerializedName("primaryOTP")
	val primaryOTP: String? = null,

	@field:SerializedName("isAggrementVerified")
	val isAggrementVerified: String? = null,

	@field:SerializedName("cityName")
	val cityName: String? = null,

	@field:SerializedName("alternateMobileNumber")
	val alternateMobileNumber: String? = null,

	@field:SerializedName("primaryMobileNumber")
	val primaryMobileNumber: String? = null,

	@field:SerializedName("custPanNumberPhoto_Path")
	val custPanNumberPhotoPath: String? = null,

	@field:SerializedName("refAdhaarNumber")
	val refAdhaarNumber: Any? = null,

	@field:SerializedName("refAddress")
	val refAddress: String? = null,

	@field:SerializedName("panNumberVerified")
	val panNumberVerified: String? = null,

	@field:SerializedName("refAdhaarNumberBackPhoto_File")
	val refAdhaarNumberBackPhotoFile: Any? = null,

	@field:SerializedName("primaryMobileVerified")
	val primaryMobileVerified: String? = null,

	@field:SerializedName("custPhoto_path")
	val custPhotoPath: String? = null,

	@field:SerializedName("refmobileNo")
	val refmobileNo: String? = null,

	@field:SerializedName("branchName")
	val branchName: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null,

	@field:SerializedName("currentAddress")
	val currentAddress: String? = null,

	@field:SerializedName("alternateMobileOTP")
	val alternateMobileOTP: Any? = null,

	@field:SerializedName("aadharNumber")
	val aadharNumber: String? = null,

	@field:SerializedName("activeStatus")
	val activeStatus: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("middleName")
	val middleName: Any? = null,

	@field:SerializedName("custAadharPhoto_Path")
	val custAadharPhotoPath: String? = null,

	@field:SerializedName("isrefKycVerified")
	val isrefKycVerified: Any? = null
)
