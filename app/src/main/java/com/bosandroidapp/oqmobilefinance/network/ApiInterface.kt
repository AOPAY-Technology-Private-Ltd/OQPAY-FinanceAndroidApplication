package com.bosandroidapp.oqmobilefinance.network

import com.bosandroidapp.bosmobilefinance.ui.slideshow.data.model.loginsignup.cibilscore.CibilScoreReq
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateResponse
import com.bosandroidapp.oqmobilefinance.data.enach.ENachStatusReq
import com.bosandroidapp.oqmobilefinance.data.enach.ENachStatusResp
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadResp
import com.bosandroidapp.oqmobilefinance.data.generattoken.RefreshTokenRequest
import com.bosandroidapp.oqmobilefinance.data.generattoken.RefreshTokenResponse
import com.bosandroidapp.oqmobilefinance.data.gst.GstRequest
import com.bosandroidapp.oqmobilefinance.data.gst.GstResponse
import com.bosandroidapp.oqmobilefinance.data.loancharge.LoanChargeReq
import com.bosandroidapp.oqmobilefinance.data.loancharge.LoanChargeResp
import com.bosandroidapp.oqmobilefinance.data.model.AddedBankListResp
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEmiStatusReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEmiStatusResponse
import com.bosandroidapp.oqmobilefinance.data.model.CustomerSearchForShortCutLoanRequest
import com.bosandroidapp.oqmobilefinance.data.model.CustomerSearchForShortCutLoanResponse
import com.bosandroidapp.oqmobilefinance.data.model.CustomerlocationUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerlocationUploadResp
import com.bosandroidapp.oqmobilefinance.data.model.DueOverdueRequest
import com.bosandroidapp.oqmobilefinance.data.model.DueOverdueResponse
import com.bosandroidapp.oqmobilefinance.data.model.EmandateOptionSelectetionReq
import com.bosandroidapp.oqmobilefinance.data.model.EmandateOptionSelectetionResponse
import com.bosandroidapp.oqmobilefinance.data.model.GenerateAccessTokenRequest
import com.bosandroidapp.oqmobilefinance.data.model.GenerateAccessTokenResponse
import com.bosandroidapp.oqmobilefinance.data.pg.GetOrderStatusOnlinePGRequest
import com.bosandroidapp.oqmobilefinance.data.pg.GetOrderStatusOnlinePGResponse
import com.bosandroidapp.oqmobilefinance.data.model.GetRetailerLedgerReq
import com.bosandroidapp.oqmobilefinance.data.model.GetRetailerLedgerResponse
import com.bosandroidapp.oqmobilefinance.data.model.HoldAmountWithdrawReq
import com.bosandroidapp.oqmobilefinance.data.model.HoldAmountWithdrawResp
import com.bosandroidapp.oqmobilefinance.data.model.LoginResponse
import com.bosandroidapp.oqmobilefinance.data.model.LowCibilCustomerReportReq
import com.bosandroidapp.oqmobilefinance.data.model.LowCibilCustomerReportResp
import com.bosandroidapp.oqmobilefinance.data.model.ManageCustomerStepWiseResponse
import com.bosandroidapp.oqmobilefinance.data.model.RetailerLoginOtpRequest
import com.bosandroidapp.oqmobilefinance.data.model.RetailerLoginOtpResendRequest
import com.bosandroidapp.oqmobilefinance.data.model.RetailerLoginOtpResponse
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanResponse
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletAmountReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletPayoutAtMakePaymentTimeReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletPayoutAtMakePaymentTimeResp
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletReportReq
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletReportResp
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutResponse
import com.bosandroidapp.oqmobilefinance.data.model.ShortCutCustomerRequest
import com.bosandroidapp.oqmobilefinance.data.model.ShortCutCustomerResponse
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UPIMandateRequest
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UPIMandateResponse
import com.bosandroidapp.oqmobilefinance.data.model.UpdateCustomerDataIfAlreadyExistResponse
import com.bosandroidapp.oqmobilefinance.data.model.UploadDeviceInfoReq
import com.bosandroidapp.oqmobilefinance.data.model.UploadDeviceInfoResp
import com.bosandroidapp.oqmobilefinance.data.model.ValidateAccessKeyReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateAccessKeyResp
import com.bosandroidapp.oqmobilefinance.data.model.ValidateCustomerAccessKeyRequest
import com.bosandroidapp.oqmobilefinance.data.model.ValidateCustomerAccessKeyResponse
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionResp
import com.bosandroidapp.oqmobilefinance.data.model.VerifyCustomerReq
import com.bosandroidapp.oqmobilefinance.data.model.VerifyCustomerResp
import com.bosandroidapp.oqmobilefinance.data.model.cibilscore.CibilScroeResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerMakePaymentResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.EligibleLoanResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.EmiSplitRes
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ForgotPasswordReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetAllMobileDetailsListRes
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetEMISplitDetlailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetIsEligibleLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.GetReportsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoanCreatedReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoanCreatedResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoginReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.MembershipFeeResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegisterCustomerResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegistrationRes
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerProfileReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerProfileRespo
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerWalletPayoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerWalletPayoutResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.SendOtpReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.SendOtpRes
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.SmsResponse
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.VerifyOTPReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.LoanSettlementReportReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.LoanSettlementReportResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.PayoutReportReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.PayoutReportResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.ReportsResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.TransactionHistoryReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.TransactionHistoryResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AAdhaarDetailesReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadhaarDetailsResponse
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadharVerificationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadharVerificationResp
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.PanVerificationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.PanVerificationResponse
import com.bosandroidapp.oqmobilefinance.data.notification.NotificationSendTokenRequest
import com.bosandroidapp.oqmobilefinance.data.notification.NotificationSendTokenResponse
import com.bosandroidapp.oqmobilefinance.data.notification.SendNotificationFeatureNameRequest
import com.bosandroidapp.oqmobilefinance.data.notification.SendNotificationFeatureNameResp
import com.bosandroidapp.oqmobilefinance.data.pennydrop.BankListReq
import com.bosandroidapp.oqmobilefinance.data.pennydrop.BankListResponse
import com.bosandroidapp.oqmobilefinance.data.pennydrop.PennyDropCheckStatusRequest
import com.bosandroidapp.oqmobilefinance.data.pennydrop.PennyDropCheckStatusResponse
import com.bosandroidapp.oqmobilefinance.data.pennydrop.PennyDropRequest
import com.bosandroidapp.oqmobilefinance.data.pennydrop.PennyDropResponse
import com.bosandroidapp.oqmobilefinance.data.pg.PGOnlineRequestCall
import com.bosandroidapp.oqmobilefinance.data.pg.PGOnlineResponseCall
import com.bosandroidapp.oqmobilefinance.data.pg.PGRequestCall
import com.bosandroidapp.oqmobilefinance.data.pg.PGRequestResponse
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoOrderStatusRequest
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoOrderStatusResponse
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoTransactionRequest
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoTransactionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface ApiInterface {

    //pan verification
    @POST("api/AOP/V1/Validation/PanDetails")
    suspend fun getPanVarification(@Body req : PanVerificationReq): Response<PanVerificationResponse>?


    // Adhar verification
    @POST("api/AOP/V1/Validation/AadhaarValidateUrl")
    suspend fun getAadharVarification(@Body req : AadharVerificationReq): Response<AadharVerificationResp>?


    // Adhar details
    @POST("api/AOP/V1/Fetch/Digilocker/TransactionID")
    suspend fun getAadharDetails(@Body req : AAdhaarDetailesReq): Response<AadhaarDetailsResponse>?


    // cibil api for getting cibil score...............

    @POST("api/AOP/CreditAnalytics/Report")
    suspend fun getcibilscore(@Body req : CibilScoreReq): Response<CibilScroeResp>?


    // low cibil score customer report ....................
    @POST("api/V1/OQFinance/GetCustomerReports")
    suspend fun getLowCibilReports(@Body req : LowCibilCustomerReportReq): Response<LowCibilCustomerReportResp>?


    @Multipart
    @POST("api/V1/OQFinance/Registration")
    suspend fun registration(
        @Part("FirstName") firstname: RequestBody,
        @Part("LastName") lastname: RequestBody,
        @Part("MobileNumber") mobilenumber: RequestBody,
        @Part("EmailID") mailid: RequestBody,
        @Part("ConfirmPassword") cnfrmpass: RequestBody,
        @Part("Password") pass: RequestBody,
        @Part("Address") address: RequestBody,
        @Part("AadharNumber") aadhaarno: RequestBody,
        @Part("PanNumber") pannumber: RequestBody,
        @Part("StoreName") storename: RequestBody,
        @Part("StoreAddress") storeaddress: RequestBody,
        @Part("GSTIN") gstnumber: RequestBody,
        @Part("LegalName") legalname: RequestBody,
        @Part("TradeName") tradename: RequestBody,
        @Part("GSTStatus") gststatus: RequestBody,
        @Part("ConstitutionOfBusiness") constitutionOfBusiness: RequestBody,
        @Part("VerificationStatus") verificationstatus: RequestBody,
        @Part("VerificationMessage") verificationMessage: RequestBody,
        @Part("IsGSTVerified") isGstVerified: RequestBody,
        @Part("IsPanVerified") isPanVerified: RequestBody,
        @Part("IsAadhaarVerified") isAadhaarVerified: RequestBody,
        @Part profilePhoto: MultipartBody.Part,
        @Part aadhaarfront: MultipartBody.Part,
        @Part aadhaarback: MultipartBody.Part,
        @Part pancardfront: MultipartBody.Part,
        @Part cancelcheque: MultipartBody.Part,
        @Part storefront: MultipartBody.Part,
        @Part companydoc: MultipartBody.Part
        ):Response<RegistrationRes>



    @POST("api/V1/OQFinance/Login")
    suspend fun login(@Body req: LoginReq): Response<LoginResponse>?


    // access token generate................................................
    @POST("api/V1/OQFinance/RefreshToken")
    suspend fun generateRefreshToken(@Body req: RefreshTokenRequest): Response<RefreshTokenResponse>?



    // retailer sent otp for verify and login

    @POST("api/V1/OQFinance/VerifyRetailerLoginOTP")
    suspend fun retailerLoginOtp(@Body req: RetailerLoginOtpRequest): Response<RetailerLoginOtpResponse>?



    // retailer resend otp for verify and login
    @POST("api/V1/OQFinance/ResendLoginOTP")
    suspend fun retailerResendLoginOtp(@Body req: RetailerLoginOtpResendRequest): Response<RetailerLoginOtpResponse>?


    @POST("api/V1/OQFinance/Logout")
    suspend fun logout(@Body req: LogoutReq): Response<LogoutResp>?


    @POST("api/V1/OQFinance/ValidateSession")
    suspend fun sessionExpired(@Body req: ValidateSessionRequest): Response<ValidateSessionResp>?


    @POST("api/V1/OQFinance/SendOTP")
    suspend fun sendOTP(@Body req: SendOtpReq): Response<SendOtpRes>?


    @POST("api/V1/OQFinance/VerifyOTP")
    suspend fun verifyOTP(@Body req: VerifyOTPReq): Response<SendOtpRes>?


    @POST("api/V1/OQFinance/ForgotPassword")
    suspend fun forgotPassword(@Body req: ForgotPasswordReq): Response<SendOtpRes>?


    @POST("api/V1/OQFinance/GetAllDeviceDetails")
    suspend fun getAllDeviceDetails(): Response<GetAllMobileDetailsListRes>?


    @POST("api/V1/OQFinance/GetModelWiseLoanDetails")
    suspend fun getEmiSplitDataDetails(@Body req : GetEMISplitDetlailsReq): Response<EmiSplitRes>?


    @POST("api/V1/OQFinance/ManageLoan")
    suspend fun getLoanCreatedByRetailer(@Body req : LoanCreatedReq): Response<LoanCreatedResp>?


    @Multipart
    @POST("api/V1/OQFinance/ManageCustByCredit")
    suspend fun getCustomerCibilApprovedReq(
        @Part("Mode") mode: RequestBody,
        @Part("FirstName") firstName: RequestBody,
        @Part("MiddleName") middleName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("PrimaryMobileNumber") primaryMobileNumber: RequestBody,
        @Part("PrimaryOTP") primaryOTP: RequestBody,
        @Part("PrimaryMobileVerified") primaryMobileVerified: RequestBody,
        @Part("AlternateMobileNumber") alternateMobileNumber: RequestBody,
        @Part("AlternateMobileOTP") alternateMobileOTP: RequestBody,
        @Part("PAlternateMobileVerified") pAlternateMobileVerified: RequestBody,
        @Part("EMailID") eMailID: RequestBody,
        @Part("FlatNo") flatNo: RequestBody,
        @Part("AearSector") aearSector: RequestBody,
        @Part("PinCode") pinCode: RequestBody,
        @Part("CurrentAddress") currentAddress: RequestBody,
        @Part("StateName") stateName: RequestBody,
        @Part("CityName") cityName: RequestBody,
        @Part("Country") country: RequestBody,
        @Part("AadharNumber") aadharNumber: RequestBody,
        @Part("AadharNumberVerified") aadharNumberVerified: RequestBody,
        @Part("PANNumber") panNumber: RequestBody,
        @Part("PANNumberVerified") panNumberVerified: RequestBody,
        @Part("BrandName") brandName: RequestBody,
        @Part("ModelName") modelName: RequestBody,
        @Part("ModelVariant") modelVariant: RequestBody,
        @Part("Color") color: RequestBody,
        @Part("SellingPrice") sellingPrice: RequestBody,
        @Part("DownPayment") downPayment: RequestBody,
        @Part("Tenure") tenure: RequestBody,
        @Part("EMIAmount") emiAmount: RequestBody,
        @Part("IMEINumber1") imeiNumber1: RequestBody,
        @Part("IMEINumber2") imeiNumber2: RequestBody,
        @Part("AccountNumber") accountNumber: RequestBody,
        @Part("BankIFSCCode") bankIFSCCode: RequestBody,
        @Part("BankName") bankName: RequestBody,
        @Part("AccountType") accountType: RequestBody,
        @Part("BranchName") branchName: RequestBody,
        @Part("RefName") refName: RequestBody,
        @Part("RefRelationShip") refRelationShip: RequestBody,
        @Part("RefmobileNo") refmobileNo: RequestBody,
        @Part("RefAddress") refAddress: RequestBody,
        @Part("DebitOrCreditCard") debitOrCreditCard: RequestBody,
        @Part("UPIMandate") upiMandate: RequestBody,
        @Part("CreatedBy") createdBy: RequestBody,
        @Part("MemberShipFees") membershipfees: RequestBody,
        @Part("PanApiResponse") PanApiResponse: RequestBody,
        @Part("AadhaarApiResponse") AadhaarApiResponse: RequestBody,
        @Part("CibilApiResponse") CibilApiResponse: RequestBody,
        @Part("CustomerCodes") CustomerCodes: RequestBody,
        @Part("RetailerCode") retailercode: RequestBody,
        @Part("CibilScore") cibilScore: RequestBody,
        @Part("IsAggrementVerified") isAggrementVerified: RequestBody,
        @Part("IsRetailerAggrementVerified") IsRetailerAggrementVerified: RequestBody,
        @Part custPhoto_File: MultipartBody.Part?,
        @Part imeiNumber1_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber2_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber_PhotoPath: MultipartBody.Part?,
        @Part invoive_Path: MultipartBody.Part?,
        @Part aadharFront_Path: MultipartBody.Part?,
        @Part aadharBack_Path: MultipartBody.Part?,
        @Part panFront_Path: MultipartBody.Part?
        ): Response<RegisterCustomerResp>



    @Multipart
    @POST("api/V1/OQFinance/ManageCustomer")
    suspend fun getRegisterCustomerReq(
        @Part("Mode") mode: RequestBody,
        @Part("FirstName") firstName: RequestBody,
        @Part("MiddleName") middleName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("PrimaryMobileNumber") primaryMobileNumber: RequestBody,
        @Part("PrimaryOTP") primaryOTP: RequestBody,
        @Part("PrimaryMobileVerified") primaryMobileVerified: RequestBody,
        @Part("AlternateMobileNumber") alternateMobileNumber: RequestBody,
        @Part("AlternateMobileOTP") alternateMobileOTP: RequestBody,
        @Part("PAlternateMobileVerified") pAlternateMobileVerified: RequestBody,
        @Part("EMailID") eMailID: RequestBody,
        @Part("FlatNo") flatNo: RequestBody,
        @Part("AearSector") aearSector: RequestBody,
        @Part("PinCode") pinCode: RequestBody,
        @Part("CurrentAddress") currentAddress: RequestBody,
        @Part("StateName") stateName: RequestBody,
        @Part("CityName") cityName: RequestBody,
        @Part("Country") country: RequestBody,
        @Part("AadharNumber") aadharNumber: RequestBody,
        @Part("AadharNumberVerified") aadharNumberVerified: RequestBody,
        @Part("PANNumber") panNumber: RequestBody,
        @Part("PANNumberVerified") panNumberVerified: RequestBody,
        @Part("BrandName") brandName: RequestBody,
        @Part("ModelName") modelName: RequestBody,
        @Part("ModelVariant") modelVariant: RequestBody,
        @Part("Color") color: RequestBody,
        @Part("SellingPrice") sellingPrice: RequestBody,
        @Part("DownPayment") downPayment: RequestBody,
        @Part("Tenure") tenure: RequestBody,
        @Part("EMIAmount") emiAmount: RequestBody,
        @Part("IMEINumber1") imeiNumber1: RequestBody,
        @Part("IMEINumber2") imeiNumber2: RequestBody,
        @Part("AccountNumber") accountNumber: RequestBody,
        @Part("BankIFSCCode") bankIFSCCode: RequestBody,
        @Part("BankName") bankName: RequestBody,
        @Part("AccountType") accountType: RequestBody,
        @Part("BranchName") branchName: RequestBody,
        @Part("RefName") refName: RequestBody,
        @Part("RefRelationShip") refRelationShip: RequestBody,
        @Part("RefmobileNo") refmobileNo: RequestBody,
        @Part("RefAddress") refAddress: RequestBody,
        @Part("DebitOrCreditCard") debitOrCreditCard: RequestBody,
        @Part("UPIMandate") upiMandate: RequestBody,
        @Part("CreatedBy") createdBy: RequestBody,
        @Part("MemberShipFees") membershipfees: RequestBody,
        @Part("RetailerCode") retailercode: RequestBody,
        @Part("CustomerCodes") customerCode: RequestBody,
        @Part("CibilScore") cibilScore: RequestBody,
        @Part("IsAggrementVerified") isAggrementVerified: RequestBody,
        @Part("IsRetailerAggrementVerified") IsRetailerAggrementVerified: RequestBody,
        @Part custPhoto_File: MultipartBody.Part?, // File here
        @Part imeiNumber1_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber2_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber_PhotoPath: MultipartBody.Part?,
        @Part invoive_Path: MultipartBody.Part?,
        @Part aadharFront_Path: MultipartBody.Part?,
        @Part aadharBack_Path: MultipartBody.Part?,
        @Part panFront_Path: MultipartBody.Part?
    ): Response<RegisterCustomerResp>



    @Multipart
    @POST("api/V1/OQFinance/ManageCustomerStepWise")
    suspend fun getManageCustomerStepWiseReq(
        @Part("Mode") mode: RequestBody,
        @Part("Step") step: RequestBody,
        @Part("RID") rid: RequestBody,
        @Part("FirstName") firstName: RequestBody,
        @Part("MiddleName") middleName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("PrimaryMobileNumber") primaryMobileNumber: RequestBody,
        @Part("PrimaryOTP") primaryOTP: RequestBody,
        @Part("PrimaryMobileVerified") primaryMobileVerified: RequestBody,
        @Part("AlternateMobileNumber") alternateMobileNumber: RequestBody,
        @Part("AlternateMobileOTP") alternateMobileOTP: RequestBody,
        @Part("PAlternateMobileVerified") pAlternateMobileVerified: RequestBody,
        @Part("EMailID") eMailID: RequestBody,
        @Part("FlatNo") flatNo: RequestBody,
        @Part("AearSector") aearSector: RequestBody,
        @Part("PinCode") pinCode: RequestBody,
        @Part("CurrentAddress") currentAddress: RequestBody,
        @Part("StateName") stateName: RequestBody,
        @Part("CityName") cityName: RequestBody,
        @Part("Country") country: RequestBody,
        @Part("AadharNumber") aadharNumber: RequestBody,
        @Part("AadharNumberVerified") aadharNumberVerified: RequestBody,
        @Part("PANNumber") panNumber: RequestBody,
        @Part("PANNumberVerified") panNumberVerified: RequestBody,
        @Part("BrandName") brandName: RequestBody,
        @Part("ModelName") modelName: RequestBody,
        @Part("ModelVariant") modelVariant: RequestBody,
        @Part("Color") color: RequestBody,
        @Part("SellingPrice") sellingPrice: RequestBody,
        @Part("DownPayment") downPayment: RequestBody,
        @Part("Tenure") tenure: RequestBody,
        @Part("EMIAmount") emiAmount: RequestBody,
        @Part("IMEINumber1") imeiNumber1: RequestBody,
        @Part("IMEINumber2") imeiNumber2: RequestBody,
        @Part("AccountNumber") accountNumber: RequestBody,
        @Part("BankIFSCCode") bankIFSCCode: RequestBody,
        @Part("BankName") bankName: RequestBody,
        @Part("IsPannyDrop") isPannyDrop: RequestBody,
        @Part("AccountType") accountType: RequestBody,
        @Part("BranchName") branchName: RequestBody,
        @Part("RefName") refName: RequestBody,
        @Part("RefRelationShip") refRelationShip: RequestBody,
        @Part("RefmobileNo") refmobileNo: RequestBody,
        @Part("RefAddress") refAddress: RequestBody,
        @Part("DebitOrCreditCard") debitOrCreditCard: RequestBody,
        @Part("UPIMandate") upiMandate: RequestBody,
        @Part("CreatedBy") createdBy: RequestBody,
        @Part("MemberShipFees") membershipfees: RequestBody,
        @Part("RetailerCode") retailercode: RequestBody,
        @Part("CustomerCodes") customerCode: RequestBody,
        @Part("CibilScore") cibilScore: RequestBody,
        @Part("ActiveStatus") activeStatus: RequestBody,
        @Part("CibilApiResponse") cibilApiResponse: RequestBody,
        @Part("AadhaarApiResponse") aadhaarApiResponse: RequestBody,
        @Part("PanApiResponse") panApiResponse: RequestBody,
        @Part("IsAggrementVerified") isAggrementVerified: RequestBody,
        @Part("IsRetailerAggrementVerified") IsRetailerAggrementVerified: RequestBody,
        @Part custPhoto_File: MultipartBody.Part?,
        @Part CustAdhaarProfilePhoto_File: MultipartBody.Part?,
        @Part imeiNumber1_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber2_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber_PhotoPath: MultipartBody.Part?,
        @Part invoive_Path: MultipartBody.Part?,
        @Part aadharFront_Path: MultipartBody.Part?,
        @Part aadharBack_Path: MultipartBody.Part?,
        @Part panFront_Path: MultipartBody.Part?
    ): Response<ManageCustomerStepWiseResponse>



    @Multipart
    @POST("api/V1/OQFinance/ManageCustomer")
    suspend fun getRegisterOnlineCustomerReq(
        @Part("Mode") mode: RequestBody,
        @Part("FirstName") firstName: RequestBody,
        @Part("MiddleName") middleName: RequestBody,
        @Part("LastName") lastName: RequestBody,
        @Part("PrimaryMobileNumber") primaryMobileNumber: RequestBody,
        @Part("PrimaryOTP") primaryOTP: RequestBody,
        @Part("PrimaryMobileVerified") primaryMobileVerified: RequestBody,
        @Part("AlternateMobileNumber") alternateMobileNumber: RequestBody,
        @Part("AlternateMobileOTP") alternateMobileOTP: RequestBody,
        @Part("PAlternateMobileVerified") pAlternateMobileVerified: RequestBody,
        @Part("EMailID") eMailID: RequestBody,
        @Part("FlatNo") flatNo: RequestBody,
        @Part("AearSector") aearSector: RequestBody,
        @Part("PinCode") pinCode: RequestBody,
        @Part("CurrentAddress") currentAddress: RequestBody,
        @Part("StateName") stateName: RequestBody,
        @Part("CityName") cityName: RequestBody,
        @Part("Country") country: RequestBody,
        @Part("AadharNumber") aadharNumber: RequestBody,
        @Part("AadharNumberVerified") aadharNumberVerified: RequestBody,
        @Part("PANNumber") panNumber: RequestBody,
        @Part("PANNumberVerified") panNumberVerified: RequestBody,
        @Part("BrandName") brandName: RequestBody,
        @Part("ModelName") modelName: RequestBody,
        @Part("ModelVariant") modelVariant: RequestBody,
        @Part("Color") color: RequestBody,
        @Part("SellingPrice") sellingPrice: RequestBody,
        @Part("DownPayment") downPayment: RequestBody,
        @Part("Tenure") tenure: RequestBody,
        @Part("EMIAmount") emiAmount: RequestBody,
        @Part("IMEINumber1") imeiNumber1: RequestBody,
        @Part("IMEINumber2") imeiNumber2: RequestBody,
        @Part("AccountNumber") accountNumber: RequestBody,
        @Part("BankIFSCCode") bankIFSCCode: RequestBody,
        @Part("BankName") bankName: RequestBody,
        @Part("AccountType") accountType: RequestBody,
        @Part("BranchName") branchName: RequestBody,
        @Part("RefName") refName: RequestBody,
        @Part("RefRelationShip") refRelationShip: RequestBody,
        @Part("RefmobileNo") refmobileNo: RequestBody,
        @Part("RefAddress") refAddress: RequestBody,
        @Part("DebitOrCreditCard") debitOrCreditCard: RequestBody,
        @Part("UPIMandate") upiMandate: RequestBody,
        @Part("CreatedBy") createdBy: RequestBody,
        @Part("MemberShipFees") membershipfees: RequestBody,
        @Part("RetailerCode") retailercode: RequestBody,
        @Part("CustomerCodes") customerCode: RequestBody,
        @Part("PanApiResponse") PanApiResponse: RequestBody,
        @Part("AadhaarApiResponse") AadhaarApiResponse: RequestBody,
        @Part("CibilApiResponse") CibilApiResponse: RequestBody,
        @Part("CibilScore") cibilScore: RequestBody,
        @Part("IsAggrementVerified") isAggrementVerified: RequestBody,
        @Part("IsRetailerAggrementVerified") IsRetailerAggrementVerified: RequestBody,
        @Part("IsrefKycVerified") IsrefKYCVerified: RequestBody,
        @Part("refAdhaarNumber") refAdhaarNumber: RequestBody,
        @Part custPhoto_File: MultipartBody.Part?, // File here
        @Part imeiNumber1_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber2_SealPhotoPath: MultipartBody.Part?,
        @Part imeiNumber_PhotoPath: MultipartBody.Part?,
        @Part invoive_Path: MultipartBody.Part?,
        ): Response<RegisterCustomerResp>


    // update customer data if already exist

    @Multipart
    @POST("api/V1/OQFinance/UpdateCustomer")
    suspend fun customerUpdateDataIfAlreadyExistReq(
        @Part("CustomerCode") customerCode: RequestBody,
        @Part("UpdatedBy") UpdatedBy: RequestBody,
        @Part("FirstName") FirstName: RequestBody,
        @Part("LastName") LastName: RequestBody,
        @Part("PrimaryMobileNumber") PrimaryMobileNumber: RequestBody,
        @Part("PrimaryOTP") PrimaryOTP: RequestBody,
        @Part("PrimaryMobileVerified") PrimaryMobileVerified: RequestBody,
        @Part("CurrentAddress") CurrentAddress: RequestBody,
        @Part("PinCode") PinCode: RequestBody,
        @Part("Country") Country: RequestBody,
        @Part("StateName") StateName: RequestBody,
        @Part("CityName") CityName: RequestBody,
        @Part("AadharNumber") AadharNumber: RequestBody,
        @Part("AadharNumberVerified") AadharNumberVerified: RequestBody,
        @Part("PANNumber") PANNumber: RequestBody,
        @Part("PANNumberVerified") PANNumberVerified: RequestBody,
        @Part("IsAggrementVerified") IsAggrementVerified: RequestBody,
        @Part("MemberShipFees") MemberShipFees: RequestBody,
        @Part("PanApiResponse") PanApiResponse: RequestBody,
        @Part("AadhaarApiResponse") AadhaarApiResponse: RequestBody,
        @Part("CibilApiResponse") CibilApiResponse: RequestBody,
        @Part("CibilScore") CibilScore: RequestBody,
        @Part("RetailerCode") RetailerCode: RequestBody,
        @Part("activeStatus") activeStatus: RequestBody,
        @Part("custPhoto_path") custPhotoPath: RequestBody,
        @Part custPhoto_File: MultipartBody.Part?
    ): Response<UpdateCustomerDataIfAlreadyExistResponse>


    @POST("api/V1/OQFinance/GetUpdateProfile")
    suspend fun getRetailerProfileGetUpdateReq(@Body req : RetailerProfileReq): Response<RetailerProfileRespo>?


    @POST("api/V1/OQFinance/GetDealerHoldWalletLedger")
    suspend fun getRetailerLedgerReq(@Body req : GetRetailerLedgerReq): Response<GetRetailerLedgerResponse>?



    // retailer wallet payout........................
    @POST("api/V1/OQFinance/ManagePayout")
    suspend fun getRetailerWalletPayoutReq(@Body req : RetailerWalletPayoutReq): Response<RetailerWalletPayoutResp>?



    // retailer loan settlement report
    @POST("api/V1/OQFinance/GetDisbursedLoanSettlement")
    suspend fun loanSettlementReportReq(@Body req : LoanSettlementReportReq): Response<LoanSettlementReportResp>?



    @POST("api/V1/OQFinance/WalletBalance")
    suspend fun getRetailerWalletAmountReq(@Body req : RetailerWalletAmountReq): Response<com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletResponse>?




    // retailer payout report
    @POST("api/V1/OQFinance/GetPayoutTransferDetails")
    suspend fun getPayoutReportReq(@Body req : PayoutReportReq): Response<PayoutReportResp>?




    @POST("api/V1/OQFinance/GetLookupReports")
    suspend fun getRetailerWalletReport(@Body req : RetailerWalletReportReq): Response<RetailerWalletReportResp>?




    // api for both addbank and get bank list.............................................................................
    @POST("api/V1/OQFinance/RetailerBankAccountManage")
    suspend fun addBankAccounts(@Body req : com.bosandroidapp.oqmobilefinance.data.model.AddBankAccountReq): Response<AddedBankListResp>?




    // api for hold amount request .............................................................................
    @POST("api/V1/OQFinance/ManageHoldingAmount")
    suspend fun requestHoldAmountWithdrawRequest(@Body req : HoldAmountWithdrawReq): Response<HoldAmountWithdrawResp>?



    // get due overdue customer data......................
    @POST("api/V1/OQFinance/getloancardfulldetails")
    suspend fun dueoverdueCustomerRequest(@Body req : DueOverdueRequest): Response<DueOverdueResponse>?



    // for customer.....................................................
    @POST("api/V1/OQFinance/GetLoanDetailsCustomerWise") // for view retailer
    suspend fun getCustomerLoanDetailsList(@Body req : GetCustomerLoanDetailsReq): Response<com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerLoanEmiResp>?



    // retailer transaction history...................................................
    @POST("api/V1/OQFinance/GetWalletCreditDebitHistory")
    suspend fun getTransactionHistoryList(@Body req : TransactionHistoryReq): Response<TransactionHistoryResp>?



    @Multipart
    @POST("api/V1/OQFinance/LoanEMIReceiving")
    suspend fun getcustomerLoanEmiReceive(
        @Part("Mode") mode: RequestBody,
        @Part("LoanCode") loancode: RequestBody,
        @Part("PaymentDate") paymentDate: RequestBody,
        @Part("PaymentMode") paymentMode: RequestBody,
        @Part("TransactionNo") utrNumber: RequestBody,
        @Part("Reason") remarks: RequestBody,
        @Part("UpdatedBy") createdBy: RequestBody,
        @Part("Customercode") customerCode: RequestBody,
        @Part("RetailerCode") retailerCode: RequestBody,
        @Part("BankName") bankName: RequestBody,
        @Part("ReceiptImagePath") receiptImagePath: RequestBody,
        @Part receiptImage: MultipartBody.Part
        ): Response<CustomerMakePaymentResp>



    @POST("api/V1/OQFinance/TransferAmtToDealer")
    suspend fun RetailerWalletPayoutReq(@Body req : RetailerWalletPayoutAtMakePaymentTimeReq): Response<RetailerWalletPayoutAtMakePaymentTimeResp>?



    @Multipart
    @POST("api/V1/OQFinance/MakePayment")
    suspend fun getCustomerReceiptUpload(
        @Part("CustomerCode") customercode: RequestBody,
        @Part("LoanCode") loancode: RequestBody,
        @Part("PaidAmount") paidamount: RequestBody,
        @Part("ReceiptImage_Path") path: RequestBody,
        @Part("CreatedBy") createBy: RequestBody,
        @Part("CreatedAt") createat: RequestBody,
        @Part("ActiveStatus") activestatus: RequestBody,
        @Part("RecordStatus") recordstatus: RequestBody,
        @Part("PaidEMINo") paidemino: RequestBody,
        @Part("TxnNumber") txnnumber: RequestBody,
        @Part("Remarks") remarks: RequestBody,
        @Part receiptImage: MultipartBody.Part
        ): Response<CustomerMakePaymentResp>




    // sms api implemented ...............................................
    @POST("vb/apikey.php")
    suspend fun sendSMSForVerifyMob(@Query("apikey") apikey : String,
                                    @Query("senderid") senderid : String,
                                    @Query("templateid") templateid : String,
                                    @Query("number") mobnumber : String,
                                    @Query("message") message : String): Response<SmsResponse>?


    // for customer and retailer both showing reports
    @POST("api/V1/OQFinance/GetLoanDetailsRetailerWise")
    suspend fun getReports(@Body req : GetReportsReq): Response<ReportsResp>?



    // revalidate user eligible for loan or not
    @POST("api/V1/OQFinance/IsLoanReapplyEligible")
    suspend fun getEligiblereq(@Body req : GetIsEligibleLoanReq): Response<EligibleLoanResp>?



    // revalidate user eligible for loan or not
    @POST("api/V1/OQFinance/GetMembershipFee")
    suspend fun getMemberShipReq(@Body req : GetIsEligibleLoanReq): Response<MembershipFeeResp>?



    // get customer location......................
    @POST("api/V1/OQFinance/managecustomerlocation")
    suspend fun uploadcustomerlocation(@Body req : CustomerlocationUploadReq): Response<CustomerlocationUploadResp>?



    // link for download apk file
    @GET("api/V1/OQFinance/generate-qr")
    suspend fun getApkUrlLink(): Response<ResponseBody>?



    // for customer generate token key
    @POST("api/V1/OQFinance/generatekey")
    suspend fun getAccessKeyForValidateAPKReq(@Body req : GenerateAccessTokenRequest): Response<GenerateAccessTokenResponse>?



    // verify generated key customer side
    @POST("api/V1/OQFinance/customervalidatekey")
    suspend fun getCustomerValidateKeyReq(@Body req : ValidateCustomerAccessKeyRequest): Response<ValidateCustomerAccessKeyResponse>?



    //  key validate retailer end
    @POST("api/V1/OQFinance/validatekey")
    suspend fun validateTokenFromRetailerReq(@Body req : ValidateAccessKeyReq): Response<ValidateAccessKeyResp>?



    //  retailer sessionout api
    @POST("api/V1/OQFinance/RetailerStatusManage")
    suspend fun sessionOutReq(@Body req : SessionOutReq): Response<SessionOutResponse>?



    //  customer mobile verification api
    @POST("api/V1/OQFinance/VerifyCustomer")
    suspend fun verifycustomerReq(@Body req : VerifyCustomerReq): Response<VerifyCustomerResp>?




    // PennyDrop api for cheking bank details
    @POST("api/AOP/V1/PennyDrop/Request")
    suspend fun pennyDropReq(@Body req : PennyDropRequest): Response<PennyDropResponse>?



    @POST("api/AOP/V1/PennyDrop/CheckStatus")
    suspend fun pennyDropStatus(@Body req : PennyDropCheckStatusRequest): Response<PennyDropCheckStatusResponse>?


    @POST("api/AOP/Enach/V1/GetBankList")
    suspend fun getBankListRequest(@Body req: BankListReq): Response<BankListResponse>?


    // select option for eMandate ............................................................

    @POST("api/V1/OQFinance/GetRegistrationApis")
    suspend fun geteMandateSelectOptionRequest(@Body req: EmandateOptionSelectetionReq): Response<EmandateOptionSelectetionResponse>?


    // for Offline eNach Api ..................................................................
    @POST("api/AOP/Enach/V1/eMandate")
    suspend fun geteMandateRequest(@Body req: EMandateRequest): Response<EMandateResponse>?


    @POST("api/AOP/Enach/V1/eMandate/getStatus")
    suspend fun geteMandateSatusRequest(@Body req: ENachStatusReq): Response<ENachStatusResp>?

    //............................................................................................



    //for online eNach Api .......................................................................

    @POST("api/OQPay/Enach/V1/eMandate")
    suspend fun geteMandateOnlineRequest(@Body req: EMandateRequest): Response<EMandateResponse>?



    @POST("api/OQPay/Enach/V1/eMandate/getStatus")
    suspend fun geteMandateOnlineSatusRequest(@Body req: ENachStatusReq): Response<ENachStatusResp>?



    // for UPI Auto Mandate Api ....................................................................
    @POST("api/OQPay/Finance/V1/SetupSubscription/Pennydrop")
    suspend fun getUpiMandateOnlineRequest(@Body req: UPIMandateRequest): Response<UPIMandateResponse>?


    @POST("api/OQPay/Finance/V1/SetupSubscription/Order/Status")
    suspend fun getUpiAutoMandateOrderStatusRequest(@Body req: UpiAutoOrderStatusRequest): Response<UpiAutoOrderStatusResponse>?


    @POST("api/OQPay/Finance/V1/SetupSubscription/Transaction")
    suspend fun getUpiAutoMandateTransactionRequest(@Body req: UpiAutoTransactionRequest): Response<UpiAutoTransactionResponse>?


    //............................................................................................


    // loan charge for each loan retailer
    @POST("api/Customer/LoanApplyCharges")
    suspend fun loanApplyChargesReq(@Body req: LoanChargeReq): Response<LoanChargeResp>?


    // customer device info
    @POST("api/V1/OQFinance/GetDeviceInformation")
    suspend fun uploadDeviceInfo(@Body req : UploadDeviceInfoReq): Response<UploadDeviceInfoResp>?


    // customer device info
    @POST("api/V1/OQFinance/UpdateEmandateDetails")
    suspend fun UpdateEmandateDetails(@Body req : EnachDateUploadReq): Response<EnachDateUploadResp>?


    @POST("api/notification/save-token")
    suspend fun sendTokenViaNotificationReq(@Body req : NotificationSendTokenRequest): Response<NotificationSendTokenResponse>?


    @POST("api/notification/send")
    suspend fun sendNotificationFeatureNameReq(@Body req : SendNotificationFeatureNameRequest): Response<SendNotificationFeatureNameResp>?


    @POST("api/V1/OQFinance/GetLoanEmIScheduleWithStatus")
    suspend fun LoanEmIScheduleWithStatusReq(@Body req : CustomerEmiStatusReq): Response<CustomerEmiStatusResponse>?


    @POST("api/AOPay/Finance/Offline/V1/PaymentGateway")
    suspend fun callPGOffline(@Body req : PGRequestCall) : Response<PGRequestResponse>?


    @POST("api/AOP/V1/Validation/GstNumber")
    suspend fun getGstNumberVerify(@Body req : GstRequest) : Response<GstResponse>?


    @POST("api/OQPay/Finance/Online/V1/PaymentGateway")
    suspend fun callPGOnline(@Body req : PGOnlineRequestCall) : Response<PGOnlineResponseCall>?


    // getStatus Of PG on callback
    @POST("api/OQPay/Finance/Online/GetOrderStatus")
    suspend fun getOrderOnlineStatusPgRequest(@Body req : GetOrderStatusOnlinePGRequest) : Response<GetOrderStatusOnlinePGResponse>?



    // upload invoice file...............
    @Multipart
    @POST("api/V1/OQFinance/UpdateCustomerPhotoPath")
    suspend fun uploadInVoiceRequest(
        @Query("CustomerCode") customerCode: String,
        @Query("ColumnName") columnName: String,
        @Query("NewValue") newValue: String,
        @Part invoiceImage: MultipartBody.Part
    ): Response<CustomerMakePaymentResp>?



    // api for customer list shortcut option for loan generate

    @POST("api/V1/OQFinance/GetCustomerByRetailer")
    suspend fun getCustomerListForShortCutLoanCreateProcess(@Body req : RetailerPerCustomerListShortCutForLoanReq) : Response<RetailerPerCustomerListShortCutForLoanResponse>?



    // search customer for shortcut flow ................................................................................................

    @POST("api/V1/OQFinance/RetailerSearchCustomer")
    suspend fun getCustomerDataForSearch(@Body req : CustomerSearchForShortCutLoanRequest) : Response<CustomerSearchForShortCutLoanResponse>?



    @POST("api/V1/OQFinance/GetCustomerByRetailerSummary")
    suspend fun getCustomerDataForSummary(@Body req : ShortCutCustomerRequest) : Response<ShortCutCustomerResponse>?


}