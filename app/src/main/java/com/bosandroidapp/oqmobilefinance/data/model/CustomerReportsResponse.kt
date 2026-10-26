package com.bosandroidapp.oqmobilefinance.data.model

import com.google.gson.annotations.SerializedName

data class CustomerReportsResponse(

	@field:SerializedName("data")
	val data: MutableList<CustomerReportDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CustomerReportDataItem(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("customerImagePath")
	val customerImagePath: String? = null,

	@field:SerializedName("dueDate")
	val dueDate: String? = null,

	@field:SerializedName("custMobileNo")
	val custMobileNo: String? = null,

	@field:SerializedName("emBankName")
	val emBankName: String? = null,

	@field:SerializedName("rid")
	val rid: Int? = null,

	@field:SerializedName("productDetails")
	val productDetails: String? = null,

	@field:SerializedName("settlementOrForeclosureCharg")
	val settlementOrForeclosureCharg: Any? = null,

	@field:SerializedName("settlementOrForeclosureStatus")
	val settlementOrForeclosureStatus: Any? = null,

	@field:SerializedName("totalOtherCharge")
	val totalOtherCharge: Int? = null,

	@field:SerializedName("emiAmount")
	val emiAmount: Any? = null,

	@field:SerializedName("totalLateFine")
	val totalLateFine: Int? = null,

	@field:SerializedName("downPayment")
	val downPayment: Any? = null,

	@field:SerializedName("duesEMI")
	val duesEMI: Int? = null,

	@field:SerializedName("bankIFSCCode")
	val bankIFSCCode: String? = null,

	@field:SerializedName("paidEMICount")
	val paidEMICount: Int? = null,

	@field:SerializedName("finalPaidAmount")
	val finalPaidAmount: Any? = null,

	@field:SerializedName("tenure")
	val tenure: Int? = null,

	@field:SerializedName("accountType")
	val accountType: String? = null,

	@field:SerializedName("closureType")
	val closureType: String? = null,

	@field:SerializedName("statuss")
	val statuss: String? = null,

	@field:SerializedName("customerName")
	val customerName: String? = null,

	@field:SerializedName("custeMailID")
	val custeMailID: Any? = null,

	@field:SerializedName("isEmandateVerified")
	val isEmandateVerified: String? = null,

	@field:SerializedName("isPannydropVerified")
	val isPannydropVerified: String? = null,

	@field:SerializedName("totalEMIRecords")
	val totalEMIRecords: Int? = null,

	@field:SerializedName("loanMode")
	val loanMode: String? = null,

	@field:SerializedName("closureDate")
	val closureDate: String? = null,

	@field:SerializedName("retailerCode")
	val retailerCode: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("customerCode")
	val customerCode: String? = null,

	@field:SerializedName("bankName")
	val bankName: String? = null,

	@field:SerializedName("txnNo")
	val txnNo: Any? = null,

	@field:SerializedName("settlementOrForeclosuredate")
	val settlementOrForeclosuredate: Any? = null,

	@field:SerializedName("retailerName")
	val retailerName: String? = null,

	@field:SerializedName("emAccountNumber")
	val emAccountNumber: String? = null,

	@field:SerializedName("lastPaymentDate")
	val lastPaymentDate: Any? = null,

	@field:SerializedName("emIfscCode")
	val emIfscCode: String? = null,

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("isSuccess")
	val isSuccess: Boolean? = null,

	@field:SerializedName("interestRate")
	val interestRate: Int? = null,

	@field:SerializedName("emAccountType")
	val emAccountType: String? = null,

	@field:SerializedName("settlementType")
	val settlementType: Any? = null,

	@field:SerializedName("paidEMI")
	val paidEMI: Int? = null,

	@field:SerializedName("paymentMode")
	val paymentMode: Any? = null,

	@field:SerializedName("totalWaiveOffAmount")
	val totalWaiveOffAmount: Int? = null,

	@field:SerializedName("branchName")
	val branchName: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null,

	@field:SerializedName("loanAmount")
	val loanAmount: Any? = null,

	@field:SerializedName("recordStatus")
	val recordStatus: String? = null,

	@field:SerializedName("totalBouncingCharge")
	val totalBouncingCharge: Int? = null,

	@field:SerializedName("totalPendingAmount")
	val totalPendingAmount: Any? = null,

	@field:SerializedName("pendingEMICount")
	val pendingEMICount: Int? = null,

	@field:SerializedName("loanCode")
	val loanCode: String? = null,

	@field:SerializedName("txnDate")
	val txnDate: Any? = null
)
