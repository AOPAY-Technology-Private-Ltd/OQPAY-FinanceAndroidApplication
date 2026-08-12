package com.bosandroidapp.oqmobilefinance.data.repository

import com.bos.payment.appName.network.ApiInterface
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.createMultipartFromUri
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.gst.GstRequest
import com.bosandroidapp.oqmobilefinance.data.model.AddBankAccountReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEmiStatusReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerlocationUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.DueOverdueRequest
import com.bosandroidapp.oqmobilefinance.data.model.GenerateAccessTokenRequest
import com.bosandroidapp.oqmobilefinance.data.model.GetRetailerLedgerReq
import com.bosandroidapp.oqmobilefinance.data.model.HoldAmountWithdrawReq
import com.bosandroidapp.oqmobilefinance.data.model.LowCibilCustomerReportReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletAmountReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletPayoutAtMakePaymentTimeReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletReportReq
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.UploadDeviceInfoReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateAccessKeyReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.VerifyCustomerReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerLoanEmiReceiveReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerMakePaymentResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ForgotPasswordReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetEMISplitDetlailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetIsEligibleLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoanCreatedReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoginReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegistrationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegistrationRes
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerProfileReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerWalletPayoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.SendOtpReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.VerifyOTPReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.GetReportsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.LoanSettlementReportReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.PayoutReportReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.TransactionHistoryReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AAdhaarDetailesReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadharVerificationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.PanVerificationReq
import com.bosandroidapp.oqmobilefinance.data.notification.NotificationSendTokenRequest
import com.bosandroidapp.oqmobilefinance.data.notification.SendNotificationFeatureNameRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class AuthRepository(private val apiInterface: ApiInterface) {

  suspend fun getregistration(req: RegistrationReq): Response<RegistrationRes> {
    val firstname = req.firstName.toRequestBody("text/plain".toMediaTypeOrNull())
    val lastname = req.lastName.toRequestBody("text/plain".toMediaTypeOrNull())
    val mob = req.mobileNumber.toRequestBody("text/plain".toMediaTypeOrNull())
    val mailid = req.emailId.toRequestBody("text/plain".toMediaTypeOrNull())
    val password = req.password.toRequestBody("text/plain".toMediaTypeOrNull())
    val cnfrmpassword = req.confrmpassword.toRequestBody("text/plain".toMediaTypeOrNull())
    val address = req.address.toRequestBody("text/plain".toMediaTypeOrNull())
    val aadhaarnumber = req.aadharnumber.toRequestBody("text/plain".toMediaTypeOrNull())
    val pannumber = req.panNumber.toRequestBody("text/plain".toMediaTypeOrNull())
    val storename = req.storeName.toRequestBody("text/plain".toMediaTypeOrNull())
    val storeaddress = req.storeAddress.toRequestBody("text/plain".toMediaTypeOrNull())
    val gstNumber = req.gstNumber.toRequestBody("text/plain".toMediaTypeOrNull())
    val gstStatus = req.GSTStatus.toRequestBody("text/plain".toMediaTypeOrNull())
    val legalname = req.legalName.toRequestBody("text/plain".toMediaTypeOrNull())
    val tradename = req.tradeName.toRequestBody("text/plain".toMediaTypeOrNull())
    val constitutionOfBusiness = req.constitutionOfBusiness.toRequestBody("text/plain".toMediaTypeOrNull())
    val verificationStatus = req.verificationStatus.toRequestBody("text/plain".toMediaTypeOrNull())
    val verificationMessage = req.verificationMessage.toRequestBody("text/plain".toMediaTypeOrNull())
    val isGSTVerified = req.isGSTVerified.toRequestBody("text/plain".toMediaTypeOrNull())
    val isPanVerified = req.isPanVerified.toRequestBody("text/plain".toMediaTypeOrNull())
    val isAadhaarVerified = req.isAadhaarVerified.toRequestBody("text/plain".toMediaTypeOrNull())



    // Convert image file to MultipartBody.Part
    val profilePhoto = if (req.profilePhoto != null && req.profilePhoto.exists()) {
      val requestFile = req.profilePhoto.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("Profile_Photo_FileName", req.profilePhoto.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("Profile_Photo_FileName", "")
    }


    val aadhaarfront = if (req.aadhaarfront != null && req.aadhaarfront.exists()) {
      val requestFile = req.aadhaarfront.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("Adhaar_front_photo_FileName", req.aadhaarfront.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("Adhaar_front_photo_FileName", "")
    }


    val aadhaarback = if (req.aadhaarback != null && req.aadhaarback.exists()) {
      val requestFile = req.aadhaarback.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("Adhaar_back_Photo_FileName", req.aadhaarback.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("Adhaar_back_Photo_FileName", "")
    }

    val pancardfront = if (req.pancardfront != null && req.pancardfront.exists()) {
      val requestFile = req.pancardfront.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("PanCard_fornt_Photo_FileName", req.pancardfront.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("PanCard_fornt_Photo_FileName", "")
    }

    val cancelcheque = if (req.cancelcheque != null && req.cancelcheque.exists()) {
      val requestFile = req.cancelcheque.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("cancle_cheque_Photo_FileName", req.cancelcheque.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("cancle_cheque_Photo_FileName", "")
    }

    val storefront = if (req.storefront != null && req.storefront.exists()) {
      val requestFile = req.storefront.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("store_front_Photo_FileName", req.storefront.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("store_front_Photo_FileName", "")
    }

    val companydoc = if (req.companydoc != null && req.companydoc.exists()) {
      val requestFile = req.companydoc.asRequestBody("image/*".toMediaTypeOrNull())
      MultipartBody.Part.createFormData("company_doc_Photo_FileName", req.companydoc.name, requestFile)
    } else {
      // send empty multipart field
      MultipartBody.Part.createFormData("company_doc_Photo_FileName", "")
    }

    return apiInterface.registration(firstname,lastname,mob,mailid,cnfrmpassword,password,address,aadhaarnumber,pannumber,storename,storeaddress,gstNumber,legalname,tradename,gstStatus,
      constitutionOfBusiness,verificationStatus,verificationMessage, isGSTVerified,isPanVerified,isAadhaarVerified,
      profilePhoto,aadhaarfront,aadhaarback,pancardfront,cancelcheque,storefront,companydoc)

  }

  suspend fun getlogin(req: LoginReq) = apiInterface.login(req)

  suspend fun getlogout(req: LogoutReq) = apiInterface.logout(req)

  suspend fun sendOTP(req: SendOtpReq) = apiInterface.sendOTP(req)

  suspend fun verifyOTPReq(req: VerifyOTPReq) = apiInterface.verifyOTP(req)

  suspend fun forgotPassword(req: ForgotPasswordReq) = apiInterface.forgotPassword(req)

  suspend fun getMobileList() = apiInterface.getAllDeviceDetails()

  suspend fun getEmiSplitData(req: GetEMISplitDetlailsReq) = apiInterface.getEmiSplitDataDetails(req)

  suspend fun getRetailerLoanCreatedReq(req:LoanCreatedReq) = apiInterface.getLoanCreatedByRetailer(req)

  suspend fun getCustomerLoanEmiDetailsReq(req: GetCustomerLoanDetailsReq) = apiInterface.getCustomerLoanDetailsList(req)

  suspend fun getTransactionHistoryList(req: TransactionHistoryReq) = apiInterface.getTransactionHistoryList(req)

  suspend fun dueoverdueCustomerRequest(req: DueOverdueRequest) = apiInterface.dueoverdueCustomerRequest(req)

  suspend fun getCustomerLoanEmiReceiveReq(req: CustomerLoanEmiReceiveReq): Response<CustomerMakePaymentResp> {
    val mode = req.mode.toRequestBody("text/plain".toMediaTypeOrNull())
    val loanCode = req.loanCode.toRequestBody("text/plain".toMediaTypeOrNull())
    val paymentDate = req.paymentDate.toRequestBody("text/plain".toMediaTypeOrNull())
    val paymentMode = req.paymentMode.toRequestBody("text/plain".toMediaTypeOrNull())
    val utrNumber = req.utrNumber.toRequestBody("text/plain".toMediaTypeOrNull())
    val remarks = req.remarks.toRequestBody("text/plain".toMediaTypeOrNull())
    val createdBy = req.createdBy.toRequestBody("text/plain".toMediaTypeOrNull())
    val Bankname = req.bankName.toRequestBody("text/plain".toMediaTypeOrNull())
    val customerCode = req.customerCode.toRequestBody("text/plain".toMediaTypeOrNull())
    val retailerCode = req.retailerCode.toRequestBody("text/plain".toMediaTypeOrNull())
    val receiptImagePath = req.receiptImagePath.toRequestBody("text/plain".toMediaTypeOrNull())

//
//    // Convert image file to MultipartBody.Part
//    val imagePart = if (req.imageFile != null && req.imageFile.exists()) {
//      val requestFile = req.imageFile.asRequestBody("image/*".toMediaTypeOrNull())
//      MultipartBody.Part.createFormData("ReceiptImage_FileName", req.imageFile.name, requestFile)
//    }
//    else {
//      // send empty multipart field
//        MultipartBody.Part.createFormData("ReceiptImage_FileName", "")
//    }
    val imagePart =  MultipartBody.Part.createFormData("ReceiptImage_FileName", "")
    return apiInterface.getcustomerLoanEmiReceive(mode, loanCode,  paymentDate, paymentMode, utrNumber, remarks, createdBy, customerCode, retailerCode, Bankname,receiptImagePath, imagePart)

  }

  suspend fun RetailerWalletPayoutReqForMakePayment(req: RetailerWalletPayoutAtMakePaymentTimeReq) = apiInterface.RetailerWalletPayoutReq(req)

  suspend fun getAadharVerificationReq(req: AadharVerificationReq) = apiInterface.getAadharVarification(req)

  suspend fun getReportsReq(req: GetReportsReq) = apiInterface.getReports(req)

  suspend fun getLoanEligibleReq(req: GetIsEligibleLoanReq) = apiInterface.getEligiblereq(req)

  suspend fun getMemberShipReq(req: GetIsEligibleLoanReq) = apiInterface.getMemberShipReq(req)

  suspend fun getRetailerProfileReq(req: RetailerProfileReq) = apiInterface.getRetailerProfileGetUpdateReq(req)

  suspend fun getRetailerLedgerReq(req: GetRetailerLedgerReq) = apiInterface.getRetailerLedgerReq(req)

  suspend fun getRetailerWalletPayoutReq(req: RetailerWalletPayoutReq) = apiInterface.getRetailerWalletPayoutReq(req)

  suspend fun loanSettlementReportReq(req: LoanSettlementReportReq) = apiInterface.loanSettlementReportReq(req)

  suspend fun getRetailerWalletAmountReq(req: RetailerWalletAmountReq) = apiInterface.getRetailerWalletAmountReq(req)

  suspend fun getRetailerWalletReportReq(req: RetailerWalletReportReq) = apiInterface.getRetailerWalletReport(req)

  suspend fun getPayoutReportReq(req: PayoutReportReq) = apiInterface.getPayoutReportReq(req)

  suspend fun getAddBankAccountReq(req: AddBankAccountReq) = apiInterface.addBankAccounts(req)

  suspend fun requestHoldAmountWithdrawRequest(req: HoldAmountWithdrawReq) = apiInterface.requestHoldAmountWithdrawRequest(req)

  suspend fun getLowCibilReports(req: LowCibilCustomerReportReq) = apiInterface.getLowCibilReports(req)

  suspend fun uploadcustomerlocation(req: CustomerlocationUploadReq) = apiInterface.uploadcustomerlocation(req)

  suspend fun getAccessKeyForValidateAPKReq(req: GenerateAccessTokenRequest) = apiInterface.getAccessKeyForValidateAPKReq(req)

  suspend fun validateTokenFromRetailerReq(req: ValidateAccessKeyReq) = apiInterface.validateTokenFromRetailerReq(req)

  suspend fun sessionOutReq(req: SessionOutReq) = apiInterface.sessionOutReq(req)

  suspend fun verifycustomerReq(req: VerifyCustomerReq) = apiInterface.verifycustomerReq(req)

  suspend fun sessionExpired(req: ValidateSessionRequest) = apiInterface.sessionExpired(req)

  suspend fun uploadDeviceInfo(req: UploadDeviceInfoReq) = apiInterface.uploadDeviceInfo(req)

  suspend fun UpdateEmandateDetails(req: EnachDateUploadReq) = apiInterface.UpdateEmandateDetails(req)
  suspend fun sendTokenViaNotificationReq(req: NotificationSendTokenRequest) = apiInterface.sendTokenViaNotificationReq(req)

  suspend fun sendNotificationFeatureNameReq(req: SendNotificationFeatureNameRequest) = apiInterface.sendNotificationFeatureNameReq(req)

  suspend fun LoanEmIScheduleWithStatusReq(req: CustomerEmiStatusReq) = apiInterface.LoanEmIScheduleWithStatusReq(req)

  suspend fun uploadInVoiceRequest(customerCode: String, columnName: String, newValue: String, imagePart: MultipartBody.Part) = 
      apiInterface.uploadInVoiceRequest(
          customerCode,
          columnName,
          newValue,
          imagePart
      )




}