package com.bosandroidapp.oqmobilefinance.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.AddBankAccountReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEmiStatusReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerSearchForShortCutLoanRequest
import com.bosandroidapp.oqmobilefinance.data.model.DueOverdueRequest
import com.bosandroidapp.oqmobilefinance.data.model.GenerateAccessTokenRequest
import com.bosandroidapp.oqmobilefinance.data.model.GetRetailerLedgerReq
import com.bosandroidapp.oqmobilefinance.data.model.HoldAmountWithdrawReq
import com.bosandroidapp.oqmobilefinance.data.model.LowCibilCustomerReportReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerLoginOtpRequest
import com.bosandroidapp.oqmobilefinance.data.model.RetailerLoginOtpResendRequest
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletAmountReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletPayoutAtMakePaymentTimeReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletReportReq
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ShortCutCustomerRequest
import com.bosandroidapp.oqmobilefinance.data.model.UpdateCustomerUploadDataReq
import com.bosandroidapp.oqmobilefinance.data.model.UploadDeviceInfoReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateAccessKeyReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.VerifyCustomerReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerLoanEmiReceiveReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ForgotPasswordReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetEMISplitDetlailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetIsEligibleLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoanCreatedReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoginReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ManageCustomerStepWiseReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegistrationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerProfileReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerWalletPayoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.SendOtpReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.VerifyOTPReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.GetReportsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.LoanSettlementReportReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.PayoutReportReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.TransactionHistoryReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadharVerificationReq
import com.bosandroidapp.oqmobilefinance.data.notification.NotificationSendTokenRequest
import com.bosandroidapp.oqmobilefinance.data.notification.SendNotificationFeatureNameRequest
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.utils.ApiResponse
import okhttp3.MultipartBody
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AuthenticationViewModel (private val repository: AuthRepository):ViewModel(){

    private fun <T> handleApiResponse(response: Response<T>?, feature: String) = when {
        response == null -> ApiResponse.error(data = null, message = "No response from server")
        response.isSuccessful -> ApiResponse.success(data = response)
        else -> {
            val code = response.code()
            val message = when (code) {
                400 -> "Bad Request (400)"
                401 -> "Session Expired / Unauthorized (401)"
                403 -> "Forbidden Access (403)"
                404 -> "$feature Not Found (404)"
                500 -> "Internal Server Error (500)"
                502 -> "Bad Gateway (502)"
                503 -> "Service Unavailable (503)"
                else -> "Unexpected error occurred: $code"
            }
            Log.e("API_ERROR", "$feature - Code: $code | Body: ${response.errorBody()?.string()}")
            ApiResponse.error(data = null, message = message)
        }
    }

    fun getRegistration(req: RegistrationReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getregistration(req), "Registration"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getLogin(req: LoginReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getlogin(req), "Login"))
        } catch (e: HttpException) {
            emit(ApiResponse.error(data = null, message = "Server Error: ${e.code()}"))
        } catch (e: IOException) {
            emit(ApiResponse.error(data = null, message = "Network error! Please check your connection."))
        } catch (e: Exception) {
            emit(ApiResponse.error(data = null, message = e.message ?: "Unknown error occurred"))
        }
    }


    fun getRetailerLoginOTPRequest(req: RetailerLoginOtpRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerLoginOtp(req), "Login"))
        } catch (e: HttpException) {
            emit(ApiResponse.error(data = null, message = "Server Error: ${e.code()}"))
        } catch (e: IOException) {
            emit(ApiResponse.error(data = null, message = "Network error! Please check your connection."))
        } catch (e: Exception) {
            emit(ApiResponse.error(data = null, message = e.message ?: "Unknown error occurred"))
        }
    }


    fun getRetailerResendLoginOtp(req: RetailerLoginOtpResendRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.retailerResendLoginOtp(req), "Login"))
        } catch (e: HttpException) {
            emit(ApiResponse.error(data = null, message = "Server Error: ${e.code()}"))
        } catch (e: IOException) {
            emit(ApiResponse.error(data = null, message = "Network error! Please check your connection."))
        } catch (e: Exception) {
            emit(ApiResponse.error(data = null, message = e.message ?: "Unknown error occurred"))
        }
    }


    fun getLogout(req: LogoutReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getlogout(req), "Logout"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun sendOTPReq(req: SendOtpReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.sendOTP(req), "Send OTP"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun verifyOTPReq(req: VerifyOTPReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.verifyOTPReq(req), "Verify OTP"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun forgotPasswordReq(req: ForgotPasswordReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.forgotPassword(req), "Forgot Password"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getMobileList() = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getMobileList(), "Mobile List"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getSplitEmiDetails(req: GetEMISplitDetlailsReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getEmiSplitData(req), "EMI Split Details"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRetailerLoanCreatedReq(req: LoanCreatedReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerLoanCreatedReq(req), "Loan Creation"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Network Error Occurred!"))
        }
    }

    fun getCustomerLoanEmiDetailsReq(req: GetCustomerLoanDetailsReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getCustomerLoanEmiDetailsReq(req), "Loan EMI Details"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getTransactionHistoryList(req: TransactionHistoryReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getTransactionHistoryList(req), "Transaction History"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getCustomerLoanEmiDetailsReq(req: DueOverdueRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.dueoverdueCustomerRequest(req), "Due Overdue Details"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getCustomerLoanDetailsReq(req: CustomerLoanEmiReceiveReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getCustomerLoanEmiReceiveReq(req), "Loan Receiving"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRetailerWalletPayoutReqForMakePayment(req: RetailerWalletPayoutAtMakePaymentTimeReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.RetailerWalletPayoutReqForMakePayment(req), "Wallet Payout"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Network Error Occurred!"))
        }
    }

    fun getAadharVerificationReq(req: AadharVerificationReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getAadharVerificationReq(req), "Aadhar Verification"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getReportsReq(req: GetReportsReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getReportsReq(req), "Reports"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Network Error Occurred!"))
        }
    }

    fun getLoanEligibleReq(req: GetIsEligibleLoanReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getLoanEligibleReq(req), "Loan Eligibility"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getgetMemberShipReqeReq(req: GetIsEligibleLoanReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getMemberShipReq(req), "Membership Details"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRetailerProfileReq(req: RetailerProfileReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerProfileReq(req), "Retailer Profile"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRetailerLedgerReq(req: GetRetailerLedgerReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerLedgerReq(req), "Retailer Ledger"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRetailerWalletPayoutReq(req: RetailerWalletPayoutReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerWalletPayoutReq(req), "Wallet Payout"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun loanSettlementReportReq(req: LoanSettlementReportReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.loanSettlementReportReq(req), "Loan Settlement Report"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRetailerWalletAmountReq(req: RetailerWalletAmountReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerWalletAmountReq(req), "Wallet Amount"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Network Error Occurred!"))
        }
    }

    fun getRetailerWalletReport(req: RetailerWalletReportReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getRetailerWalletReportReq(req), "Wallet Report"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getPayoutReportReq(req: PayoutReportReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getPayoutReportReq(req), "Payout Report"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAddBankAccountReq(req: AddBankAccountReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getAddBankAccountReq(req), "Add Bank Account"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getHoldAmountWithdrawRequest(req: HoldAmountWithdrawReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.requestHoldAmountWithdrawRequest(req), "Hold Amount Withdraw"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getLowCibilReports(req: LowCibilCustomerReportReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getLowCibilReports(req), "Low CIBIL Report"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAccessKeyForValidateAPKReq(req: GenerateAccessTokenRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getAccessKeyForValidateAPKReq(req), "Generate Access Key"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAccessKeyForValidateAPKReq(req: ValidateAccessKeyReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.validateTokenFromRetailerReq(req), "Token Validation"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getSessionReq(req: SessionOutReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.sessionOutReq(req), "Session Request"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getverifycustomerReq(req: VerifyCustomerReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.verifycustomerReq(req), "Verify Customer"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getSessionExpiredReq(req: ValidateSessionRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.sessionExpired(req), "Session Status"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun uploadDeviceInfo(req: UploadDeviceInfoReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.uploadDeviceInfo(req), "Device Info Upload"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun UpdateEmandateDetails(req: EnachDateUploadReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.UpdateEmandateDetails(req), "E-Mandate Update"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun NotificationSendTokenRequest(req: NotificationSendTokenRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.sendTokenViaNotificationReq(req), "Notification Token"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun sendNotificationFeatureNameReq(req: SendNotificationFeatureNameRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.sendNotificationFeatureNameReq(req), "Notification Feature"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun LoanEmIScheduleWithStatusReq(req: CustomerEmiStatusReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.LoanEmIScheduleWithStatusReq(req), "Loan Schedule"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun uploadInVoiceRequest(customerCode: String, columnName: String, newValue: String, imagePart: MultipartBody.Part) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.uploadInVoiceRequest(customerCode, columnName, newValue, imagePart), "Invoice Upload"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getCustomerListForShortCutLoanCreateProcess(req: RetailerPerCustomerListShortCutForLoanReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getCustomerListForShortCutLoanCreateProcess(req), "Customer List"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }




    fun getCustomerDataForSearch(req: CustomerSearchForShortCutLoanRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getCustomerDataForSearch(req), "Customer List"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }



    fun uploadCustomerListForShortCutLoanCreateProcess(req: ManageCustomerStepWiseReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getCustomShortCutDataRequest(req), "Customer List"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }



    fun getCustomerDataSummaryForShortCut(req: ShortCutCustomerRequest) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.getCustomerDataSummaryForShortCut(req), "Customer List"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }




    fun UpdateCustomerUploadDataReq(req: UpdateCustomerUploadDataReq) = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading(data = null))
        try {
            emit(handleApiResponse(repository.updateCustomerDataReq(req), "Customer List"))
        } catch (exception: Exception) {
            emit(ApiResponse.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }




}
