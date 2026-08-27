package com.bosandroidapp.oqmobilefinance.data.model

import java.io.File

data class UpdateCustomerUploadDataReq (
    var customerCode: String = "",
    var updatedBy : String = "",
    var firstName : String = "",
    var lastName: String = "",
    var primaryMobileNumber: String = "",
    var primaryOTP: String = "",
    var primaryMobileVerified: String = "",
    var currentAddress: String = "",
    var pinCode: String = "",
    var country: String = "",
    var stateName: String = "",
    var cityName: String = "",
    var aadharNumber: String = "",
    var aadharNumberVerified: String = "",
    var panNumber: String = "",
    var panNumberVerified: String = "",
    var IsAggrementVerified: String = "",
    var memberShipFees: String = "",
    var panApiResponse: String = "",
    var aadhaarApiResponse: String = "",
    var cibilApiResponse: String = "",
    var cibilScore: String = "",
    var retailerCode: String = "",
    var activeStatus: String = "",
    var custPhoto_path: String = "",
    var custPhoto_File: File? = null
)