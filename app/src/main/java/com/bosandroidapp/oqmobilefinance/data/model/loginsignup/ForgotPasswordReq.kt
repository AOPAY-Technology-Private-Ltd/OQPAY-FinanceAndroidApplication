package com.bosandroidapp.oqmobilefinance.data.model.loginsignup

import com.google.gson.annotations.SerializedName

data class ForgotPasswordReq(
    @SerializedName("mobileOrEmailID")
    var mobileormailid:String,
    @SerializedName("password")
    var password:String
)
