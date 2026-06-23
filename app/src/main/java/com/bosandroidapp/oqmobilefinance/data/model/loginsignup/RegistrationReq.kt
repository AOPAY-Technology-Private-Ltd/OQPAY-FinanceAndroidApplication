package com.bosandroidapp.oqmobilefinance.data.model.loginsignup

import com.google.gson.annotations.SerializedName
import java.io.File

data class RegistrationReq(
    @SerializedName("firstName")
    var firstName:String,

    @SerializedName("lastName")
    var lastName:String,

    @SerializedName("mobileNumber")
    var mobileNumber:String,

    @SerializedName("emailID")
    var emailId:String,

    @SerializedName("password")
    var password:String,

    @SerializedName("confirmPassword")
    var confrmpassword:String,

    @SerializedName("address")
    var address:String,

    @SerializedName("aadharNumber")
    var aadharnumber:String,

    @SerializedName("panNumber")
    var panNumber:String,

    @SerializedName("storeName")
    var storeName:String,

    @SerializedName("storeAddress")
    var storeAddress:String,

    @SerializedName("GSTIN")
    var gstNumber:String,

    @SerializedName("LegalName")
    var legalName:String,

    @SerializedName("TradeName")
    var tradeName:String,

    @SerializedName("GSTStatus")
    var GSTStatus:String,

    @SerializedName("ConstitutionOfBusiness")
    var constitutionOfBusiness :String,

    @SerializedName("VerificationStatus")
    var verificationStatus:String,

    @SerializedName("VerificationMessage")
    var verificationMessage:String,

    @SerializedName("IsGSTVerified")  // 1 means yes and 0 means no
    var isGSTVerified: String,

    @SerializedName("IsPanVerified") // 1 means yes and 0 means no
    var isPanVerified: String,

    @SerializedName("IsAadhaarVerified")  // 1 means yes and 0 means no
    var isAadhaarVerified:String,


    val profilePhoto: File?,

    val aadhaarfront: File?,

    val aadhaarback: File?,

    val pancardfront: File?,

    val cancelcheque: File?,

    val storefront: File?,

    val companydoc: File?
)
