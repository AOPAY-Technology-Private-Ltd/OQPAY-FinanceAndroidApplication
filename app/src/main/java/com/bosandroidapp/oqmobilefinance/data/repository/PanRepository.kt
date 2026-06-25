package com.bosandroidapp.oqmobilefinance.data.repository

import com.bos.payment.appName.network.ApiInterface
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.ENachStatusReq
import com.bosandroidapp.oqmobilefinance.data.gst.GstRequest
import com.bosandroidapp.oqmobilefinance.data.loancharge.LoanChargeReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadharVerificationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.PanVerificationReq
import com.bosandroidapp.oqmobilefinance.data.pennydrop.BankListReq
import com.bosandroidapp.oqmobilefinance.data.pennydrop.PennyDropCheckStatusRequest
import com.bosandroidapp.oqmobilefinance.data.pennydrop.PennyDropRequest
import com.bosandroidapp.oqmobilefinance.data.pg.PGOnlineRequestCall
import com.bosandroidapp.oqmobilefinance.data.pg.PGRequestCall

class PanRepository(private val apiInterface: ApiInterface) {

    suspend fun getPanVerificationReq(req: PanVerificationReq) = apiInterface.getPanVarification(req)

    suspend fun getBankListReq(req: BankListReq) = apiInterface.getBankListRequest(req)

    suspend fun getpennyDropReq(req: PennyDropRequest) = apiInterface.pennyDropReq(req)

    suspend fun getpennyDropCheckStatusReq(req: PennyDropCheckStatusRequest) = apiInterface.pennyDropStatus(req)

    suspend fun getEMandateRequestReq(req: EMandateRequest) = apiInterface.geteMandateRequest(req)
    suspend fun geteMandateSatusRequest(req: ENachStatusReq) = apiInterface.geteMandateSatusRequest(req)
    suspend fun loanApplyChargesReq(req: LoanChargeReq) = apiInterface.loanApplyChargesReq(req)

    suspend fun getAadharVerificationReq(req: AadharVerificationReq) = apiInterface.getAadharVarification(req)

    suspend fun getPGRequestCall(req: PGRequestCall) = apiInterface.callPGOffline(req)

    suspend fun getPGRequestCallOnline(req: PGOnlineRequestCall) = apiInterface.callPGOnline(req)

    suspend fun getGstNumberVerify(req: GstRequest) = apiInterface.getGstNumberVerify(req)

}
