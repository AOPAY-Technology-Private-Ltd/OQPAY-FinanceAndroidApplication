package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class RetailerPerCustomerListShortCutForLoanResponse(

	@field:SerializedName("data")
	val data: List<CustomerShortCutDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CustomerShortCutDataItem(

	@field:SerializedName("bankDetails")
	val bankDetails: BankDetails? = null,

	@field:SerializedName("invoiceAndAppVerification")
	val invoiceAndAppVerification: InvoiceAndAppVerification? = null,

	@field:SerializedName("imeiDetails")
	val imeiDetails: ImeiDetails? = null,

	@field:SerializedName("createLoanDetails")
	val createLoanDetails: CreateLoanDetails? = null,

	@field:SerializedName("eMandateDetails")
	val eMandateDetails: EMandateDetails? = null,

	@field:SerializedName("customerDetails")
	val customerDetails: CustomerDetails? = null,

	@field:SerializedName("productDetails")
	val productDetails: ProductDetails? = null,

	@field:SerializedName("referenceDetails")
	val referenceDetails: ReferenceDetails? = null
)

data class CustomerDetails(

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("custAadharBackPhoto_Path")
	val custAadharBackPhotoPath: String? = null,

	@field:SerializedName("panApiResponse")
	val panApiResponse: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("aadharNumberVerified")
	val aadharNumberVerified: String? = null,

	@field:SerializedName("primaryOTP")
	val primaryOTP: String? = null,

	@field:SerializedName("aadhaarApiResponse")
	val aadhaarApiResponse: String? = null,

	@field:SerializedName("cibilApiResponse")
	val cibilApiResponse: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isAggrementVerified")
	val isAggrementVerified: String? = null,

	@field:SerializedName("cityName")
	val cityName: String? = null,

	@field:SerializedName("eMailID")
	val eMailID: String? = null,

	@field:SerializedName("stateName")
	val stateName: String? = null,

	@field:SerializedName("cibilScore")
	val cibilScore: String? = null,

	@field:SerializedName("aearSector")
	val aearSector: String? = null,

	@field:SerializedName("alternateMobileNumber")
	val alternateMobileNumber: String? = null,

	@field:SerializedName("primaryMobileNumber")
	val primaryMobileNumber: String? = null,

	@field:SerializedName("custPanNumberPhoto_Path")
	val custPanNumberPhotoPath: String? = null,

	@field:SerializedName("panNumberVerified")
	val panNumberVerified: String? = null,

	@field:SerializedName("primaryMobileVerified")
	val primaryMobileVerified: String? = null,

	@field:SerializedName("custPhoto_path")
	val custPhotoPath: String? = null,

	@field:SerializedName("panNumber")
	val panNumber: String? = null,

	@field:SerializedName("pAlternateMobileVerified")
	val pAlternateMobileVerified: String? = null,

	@field:SerializedName("currentAddress")
	val currentAddress: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("alternateMobileOTP")
	val alternateMobileOTP: String? = null,

	@field:SerializedName("flatNo")
	val flatNo: String? = null,

	@field:SerializedName("aadharNumber")
	val aadharNumber: String? = null,

	@field:SerializedName("activeStatus")
	val activeStatus: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("pinCode")
	val pinCode: String? = null,

	@field:SerializedName("middleName")
	val middleName: String? = null,

	@field:SerializedName("retailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("custAadharPhoto_Path")
	val custAadharPhotoPath: String? = null
)

data class EMandateDetails(

	@field:SerializedName("upiMandate")
	val upiMandate: String? = null
)

data class CreateLoanDetails(

	@field:SerializedName("isEmandateVerified")
	val isEmandateVerified: String? = null,

	@field:SerializedName("loanStartDate")
	val loanStartDate: String? = null,

	@field:SerializedName("loanMode")
	val loanMode: String? = null,

	@field:SerializedName("loanRID")
	val loanRID: String? = null,

	@field:SerializedName("loanStatus")
	val loanStatus: String? = null,

	@field:SerializedName("memberShip")
	val memberShip: String? = null,

	@field:SerializedName("loanCode")
	val loanCode: String? = null,

	@field:SerializedName("defaulterEmiDebit")
	val defaulterEmiDebit: String? = null,

	@field:SerializedName("loanEndDate")
	val loanEndDate: String? = null
)

data class ImeiDetails(

	@field:SerializedName("imeiNumber1_SealPhotoPath")
	val imeiNumber1SealPhotoPath: String? = null,

	@field:SerializedName("imeiNumber_PhotoPath")
	val imeiNumberPhotoPath: String? = null,

	@field:SerializedName("isRetailerAggrementVerified")
	val isRetailerAggrementVerified: String? = null,

	@field:SerializedName("imeiNumber1")
	val imeiNumber1: String? = null,

	@field:SerializedName("imeiNumber2")
	val imeiNumber2: String? = null,

	@field:SerializedName("imeiNumber2_SealPhotoPath")
	val imeiNumber2SealPhotoPath: String? = null
)

data class ProductDetails(

	@field:SerializedName("interestRate")
	val interestRate: String? = null,

	@field:SerializedName("modelName")
	val modelName: String? = null,

	@field:SerializedName("interestAmt")
	val interestAmt: String? = null,

	@field:SerializedName("brandName")
	val brandName: String? = null,

	@field:SerializedName("sellingPrice")
	val sellingPrice: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("emiAmount")
	val emiAmount: String? = null,

	@field:SerializedName("downPayment")
	val downPayment: String? = null,

	@field:SerializedName("processingFees")
	val processingFees: String? = null,

	@field:SerializedName("modelVariant")
	val modelVariant: String? = null,

	@field:SerializedName("tenure")
	val tenure: String? = null,

	@field:SerializedName("loanAmount")
	val loanAmount: String? = null
)

data class BankDetails(

	@field:SerializedName("isPannydropVerified")
	val isPannydropVerified: String? = null,

	@field:SerializedName("accountType")
	val accountType: String? = null,

	@field:SerializedName("bankIFSCCode")
	val bankIFSCCode: String? = null,

	@field:SerializedName("branchName")
	val branchName: String? = null,

	@field:SerializedName("branchAddress")
	val branchAddress: String? = null,

	@field:SerializedName("bankName")
	val bankName: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null
)

data class InvoiceAndAppVerification(

	@field:SerializedName("invoive_Path")
	val invoivePath: String? = null,

	@field:SerializedName("isAccessKeyVerified")
	val isAccessKeyVerified: String? = null
)

data class ReferenceDetails(

	@field:SerializedName("refRelationShip")
	val refRelationShip: String? = null,

	@field:SerializedName("refAdhaarNumberFrontPhoto")
	val refAdhaarNumberFrontPhoto: String? = null,

	@field:SerializedName("refmobileNo")
	val refmobileNo: String? = null,

	@field:SerializedName("refPanNumber")
	val refPanNumber: String? = null,

	@field:SerializedName("refAdhaarNumberBackPhoto")
	val refAdhaarNumberBackPhoto: String? = null,

	@field:SerializedName("refName")
	val refName: String? = null,

	@field:SerializedName("refAdhaarNumber")
	val refAdhaarNumber: String? = null,

	@field:SerializedName("refPanNumberPhoto")
	val refPanNumberPhoto: String? = null,

	@field:SerializedName("refAddress")
	val refAddress: String? = null,

	@field:SerializedName("isrefKycVerified")
	val isrefKycVerified: String? = null
)
