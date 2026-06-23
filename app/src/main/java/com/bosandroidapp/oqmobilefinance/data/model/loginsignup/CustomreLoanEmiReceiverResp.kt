package com.bosandroidapp.oqmobilefinance.data.model.loginsignup

import com.google.gson.annotations.SerializedName

data class CustomreLoanEmiReceiverResp(@SerializedName("message")
                                       val message: String = "",
                                       @SerializedName("status")
                                       val status: String = "")