package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import com.bos.payment.appName.network.ApiInterface
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.bosmobilefinance.ui.slideshow.ui.view.activity.retailer.cibilreportsfragment.BureauScore.Companion.userScore
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.FirstName
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.LastName
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.MiddleName
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.loaneCode
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityQrcodePageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadhaarResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharBackImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharFrontImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankIFSCCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BrandName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CardType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CheckOnlineOrOffline
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CibilResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ClickOnCardLowCibilScore
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileOTP
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAreaSector
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCityName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCountry
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCurrentAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustFirstName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustFlatNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustLastName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustMiddleName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPinCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryOTP
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustStateName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CusteMailID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustomerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DefaulterEmiDebitAutoApproved
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DefaulterEmiDebitPending
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DownPayment
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.EmiAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber1
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber1SealPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber2
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber2SealPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumberPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.InterestAmt
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.InterestRate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Invoive_Path
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.IsRetailerAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanEndDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanStartDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanStatus
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ModelColor
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ModelName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ModelVarient
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanFrontImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumberVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ProcessingFees
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefRelationShip
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ReferenceAadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ReferenceAadharVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefmobileNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RetailerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Tenure
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ToBePaidAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.calculateEmiEndDateFromNow
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.createMultipartFromUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.dialog
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.getCurrentStartDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.iisAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAggrementVerified

import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.uploadDataOnFirebaseConsole
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.ENachStatusReq
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.loancharge.LoanChargeReq
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateAccessKeyReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetIsEligibleLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoanCreatedReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegisterCustomerResp
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage.Companion.webUrl
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import kotlin.math.roundToInt


class QRCodePage : BaseActivity() {
    lateinit var binding: ActivityQrcodePageBinding
    lateinit var viewModel: AuthenticationViewModel
    lateinit var panViewModel: PanViewModel
    lateinit var api: ApiInterface
    lateinit var preference: SharedPreference


    var downPayment: String = ""
    var membershipAmt: String = ""
    var isEmandateVerified : String= ""
    var isPannydropVerified : String= "Yes"
    var loancreatedreq: LoanCreatedReq? = null


    companion object{
        var isEnachCancelled : Boolean = false
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }


        if (IMEIDetailsPage.dialog != null && IMEIDetailsPage.dialog.isShowing) {
            IMEIDetailsPage.dialog.dismiss()
        }


        preference = SharedPreference(this)
        api = RetrofitClient.apiInterface
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        panViewModel = ViewModelProvider(this, com.bosandroidapp.oqmobilefinance.data.viewModelFactory.PanViewModelFactory(PanRepository(RetrofitClient.apiInterfacePAN)))[PanViewModel::class.java]


        setOnClickListner()
        hitApiForMemberShipFee()

    }



    override fun onResume() {
        super.onResume()

        hitApiForLogin()

    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner() {

        binding.home.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            onBackPressed()
        }

        binding.back.setOnClickListener {
            OpenPopUpForVAlert()
        }

        binding.nextlayout.setOnClickListener {
            if (isInternetAvailable(this@QRCodePage)) {
                ConstantClass.OpenPopUpForVeryfyOTP(this)
                binding.nextlayout.isEnabled= false
                loaneCode=""
                LoanStartDate=""
                LoanEndDate=""
                loancreatedreq= null
                hitApiForCustomerRegister()
            }
            else {
                loaneCode=""
                binding.nextlayout.isEnabled= true
                Toast.makeText(this, "Please check your internet connection .", Toast.LENGTH_SHORT).show()
            }

        }

        binding.LoanCreatelayout.setOnClickListener {
            if (loancreatedreq != null) {
                if (!loaneCode.isNullOrEmpty()) {

                    // Loan already created in a previous attempt, retry E-Nach directly

                   /* LoanStartDate = "2026-08-25T09:21:03.988Z"
                    LoanEndDate = "2026-09-25T09:21:03.988Z"*/

                    val startDate = LoanStartDate
                    val endDate = LoanEndDate

                     val emiAmount = EmiAmount.toDouble().roundToInt()
                   /* val emiAmount = 1*/

                    val request = EMandateRequest(
                        categoryID = 7,
                        collectionAmount = emiAmount,
                        collectCollectionUntilCancle = false,
                        seqType = "RCUR",
                        iFSCCode = BankIFSCCode,
                        frequncy = "MNTH",
                        registrationID =  if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                            ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
                        } else {
                            ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
                        },
                        accountHolderName = ConstantClass.AccountHolderName,
                        finalCollectionDate = endDate,
                        loanNo = loaneCode,
                        accountType = AccountType,
                        emailAddress = CusteMailID,
                        firstCollectionDate = startDate,
                        mobileNumber = CustPrimaryMobileNumber,
                        bankAccountNumberConfirmation = AccountNumber,
                        addIn2 = BranchAddress,
                        addIn3 = "",
                        debitType = true,
                        teleNumber = "",
                        authType = "",
                        bankID = BankID,
                        bankAccountNumber = AccountNumber
                    )

                    hitApiForEnach(request,true)
                    //startActivity(Intent(this@QRCodePage, AppScanInstallPage::class.java))
                }
                else {
                    // No loan created yet, proceed with the normal flow
                    binding.LoanCreatelayout.isEnabled= false
                   // startActivity(Intent(this@QRCodePage, AppScanInstallPage::class.java))
                    hitApiForCheckLoanCharge(loancreatedreq!!)

                  // for testing purpose.
                }
            }
            else{
                binding.LoanCreatelayout.isEnabled= true
            }

        }


    }






    @RequiresApi(Build.VERSION_CODES.O)
    fun hitApiForCustomerRegister() {

        val custPhotoPart = createMultipartFromUri(this, CustPhotoPath, "CustPhoto_File", "CustomerPhoto")

        val imei1SealPart = createMultipartFromUri(this,
            ImeiNumber1SealPhotoPath,
            "IMEINumber1_SealPhotoFile",
            "IMEINumber1Image"
        )

        val imei2SealPart = createMultipartFromUri(
            this,
            ImeiNumber2SealPhotoPath,
            "IMEINumber2_SealPhotoFile",
            "IMEINumber2Image"
        )

        val imeiPhotoPart = createMultipartFromUri(
            this,
            ImeiNumberPhotoPath,
            "IMEINumberPhotoFile",
            "IMEINumberImage"
        )

        val invoicePart = createMultipartFromUri(this, Invoive_Path, "InvoiceFile", "InvoiceImage")

        val aadharFrontPart = createMultipartFromUri(
            this,
            AadharFrontImageUri,
            "CustAadharPhoto_File",
            "AadharFrontImage"
        )

        val aadharBackPart = createMultipartFromUri(
            this,
            AadharBackImageUri,
            "CustAadharBackPhoto_File",
            "AadharBackImage"
        )

        val PanFrontPart = createMultipartFromUri(
            this,
            PanFrontImageUri,
            "CustPanNumberPhoto_File",
            "PanFrontImage"
        )

        val firstName = preference.getStringValue(ConstantClass.FirstName, "").orEmpty()
        val lastName = preference.getStringValue(ConstantClass.LastName, "").orEmpty()
        val safeLastName = if (!lastName.isNullOrBlank() && lastName != "null") lastName else ""
        var createdBy = firstName.plus(" ").plus(safeLastName)
        var retailercode = preference.getStringValue(ConstantClass.RetailerCode, "")

        if(CustCountry.isNullOrBlank()){
            CustCountry ="India"
        }

        if (ConstantClass.ClickOnCardLowCibilScore.equals(CardType)) {

            // Map safely (avoid !!)
            val requestMap = hashMapOf(
                "Mode" to "UPDATE".toRequestBody(),
                "FirstName" to (CustFirstName ?: "").toRequestBody(),
                "MiddleName" to (CustMiddleName ?: "").toRequestBody(),
                "LastName" to (CustLastName ?: "").toRequestBody(),
                "PrimaryMobileNumber" to (CustPrimaryMobileNumber ?: "").toRequestBody(),
                "PrimaryOTP" to (CustPrimaryOTP ?: "").toRequestBody(),
                "PrimaryMobileVerified" to (CustPrimaryMobileVerified ?: "").toRequestBody(),
                "AlternateMobileNumber" to (CustAlternateMobileNumber ?: "").toRequestBody(),
                "AlternateMobileOTP" to "".toRequestBody(),
                "PAlternateMobileVerified" to "no".toRequestBody(),
                "EMailID" to (CusteMailID ?: "").toRequestBody(),
                "FlatNo" to (CustFlatNo ?: "").toRequestBody(),
                "AearSector" to (CustAreaSector ?: "").toRequestBody(),
                "PinCode" to (CustPinCode ?: "").toRequestBody(),
                "CurrentAddress" to (CustCurrentAddress ?: "").toRequestBody(),
                "StateName" to (CustStateName ?: "").toRequestBody(),
                "CityName" to (CustCityName ?: "").toRequestBody(),
                "Country" to (CustCountry ?: "").toRequestBody(),
                "AadharNumber" to (AadharNumber ?: "").toRequestBody(),
                "AadharNumberVerified" to (ConstantClass.AadharVerified ?: "").toRequestBody(),
                "PANNumber" to (PanNumber ?: "").toRequestBody(),
                "PANNumberVerified" to (PanNumberVerified ?: "").toRequestBody(),
                "BrandName" to BrandName.toRequestBody(),
                "ModelName" to ConstantClass.ModelName.toRequestBody(),
                "ModelVariant" to ConstantClass.ModelVarient.toRequestBody(),
                "Color" to ConstantClass.ModelColor.toRequestBody(),
                "SellingPrice" to ConstantClass.SellingPrice.toRequestBody(),
                "DownPayment" to downPayment.toRequestBody(),
                "Tenure" to ConstantClass.Tenure.toRequestBody(),
                "EMIAmount" to EmiAmount.toRequestBody(),
                "IMEINumber1" to ImeiNumber1.toRequestBody(),
                "IMEINumber2" to ImeiNumber2.toRequestBody(),
                "AccountNumber" to AccountNumber.toRequestBody(),
                "BankIFSCCode" to BankIFSCCode.toRequestBody(),
                "BankName" to BankName.toRequestBody(),
                "AccountType" to AccountType.toRequestBody(),
                "BranchName" to BranchName.toRequestBody(),
                "RefName" to RefName.toRequestBody(),
                "MemberShipFees" to membershipAmt.toRequestBody(),
                "RefRelationShip" to RefRelationShip.toRequestBody(),
                "RefmobileNo" to RefmobileNo.toRequestBody(),
                "RefAddress" to RefAddress.toRequestBody(),
                "PanApiResponse" to (PanResponse ?: "").toRequestBody(),
                "AadhaarApiResponse" to (AadhaarResponse ?: "").toRequestBody(),
                "CibilApiResponse" to (CibilResponse ?: "").toRequestBody(),
                "CustomerCodes" to CustCode.toRequestBody(),
                "RefRelationShip" to RefRelationShip.toRequestBody(),
                "RefmobileNo" to RefmobileNo.toRequestBody(),
                "RefAddress" to RefAddress.toRequestBody(),
                "DebitOrCreditCard" to "".toRequestBody(),
                "UPIMandate" to "yes".toRequestBody(),
                "CreatedBy" to createdBy.toRequestBody(),
                "RetailerCode" to retailercode.toRequestBody(),
                "CibilScore" to userScore.toString().toRequestBody(),
                "IsAggrementVerified" to isAggrementVerified.toRequestBody(),
                "IsRetailerAggrementVerified" to IsRetailerAggrementVerified.toRequestBody(),
            )

            // Debug log full request
            Log.e("API_REQ_MAP", Gson().toJson(requestMap))

            Log.e("API_REQ_IMAGES", "CustPhoto=$CustPhotoPath | AadharFront=$AadharFrontImageUri | PanFront=$PanFrontImageUri")

            lifecycleScope.launch {
                try {
                    val response = api.getCustomerCibilApprovedReq(
                        requestMap["Mode"]!!,
                        requestMap["FirstName"]!!,
                        requestMap["MiddleName"]!!,
                        requestMap["LastName"]!!,
                        requestMap["PrimaryMobileNumber"]!!,
                        requestMap["PrimaryOTP"]!!,
                        requestMap["PrimaryMobileVerified"]!!,
                        requestMap["AlternateMobileNumber"]!!,
                        requestMap["AlternateMobileOTP"]!!,
                        requestMap["PAlternateMobileVerified"]!!,
                        requestMap["EMailID"]!!,
                        requestMap["FlatNo"]!!,
                        requestMap["AearSector"]!!,
                        requestMap["PinCode"]!!,
                        requestMap["CurrentAddress"]!!,
                        requestMap["StateName"]!!,
                        requestMap["CityName"]!!,
                        requestMap["Country"]!!,
                        requestMap["AadharNumber"]!!,
                        requestMap["AadharNumberVerified"]!!,
                        requestMap["PANNumber"]!!,
                        requestMap["PANNumberVerified"]!!,
                        requestMap["BrandName"]!!,
                        requestMap["ModelName"]!!,
                        requestMap["ModelVariant"]!!,
                        requestMap["Color"]!!,
                        requestMap["SellingPrice"]!!,
                        requestMap["DownPayment"]!!,
                        requestMap["Tenure"]!!,
                        requestMap["EMIAmount"]!!,
                        requestMap["IMEINumber1"]!!,
                        requestMap["IMEINumber2"]!!,
                        requestMap["AccountNumber"]!!,
                        requestMap["BankIFSCCode"]!!,
                        requestMap["BankName"]!!,
                        requestMap["AccountType"]!!,
                        requestMap["BranchName"]!!,
                        requestMap["RefName"]!!,
                        requestMap["RefRelationShip"]!!,
                        requestMap["RefmobileNo"]!!,
                        requestMap["RefAddress"]!!,
                        requestMap["DebitOrCreditCard"]!!,
                        requestMap["UPIMandate"]!!,
                        requestMap["CreatedBy"]!!,
                        requestMap["MemberShipFees"]!!,
                        requestMap["PanApiResponse"]!!,
                        requestMap["AadhaarApiResponse"]!!,
                        requestMap["CibilApiResponse"]!!,
                        requestMap["CustomerCodes"]!!,
                        requestMap["RetailerCode"]!!,
                        requestMap["CibilScore"]!!,
                        requestMap["IsAggrementVerified"]!!,
                        requestMap["IsRetailerAggrementVerified"]!!,
                        custPhotoPart,
                        imei1SealPart,
                        imei2SealPart,
                        imeiPhotoPart,
                        null,
                        aadharFrontPart,
                        aadharBackPart,
                        PanFrontPart
                    )

                    withContext(Dispatchers.Main) {
                        ConstantClass.dialog?.takeIf { it.isShowing }?.dismiss()
                        Log.e("API_RESPONSE_CODE", response.code().toString())

                        if (response.isSuccessful) {
                            val body = response.body()
                            var message = body?.message ?: "Success"
                            Log.e("API_RESPONSE_SUCCESS", Gson().toJson(body))

                            Log.d("createcustresp", Gson().toJson(body))

                            if (body!!.statuss.equals("FAILED")) {
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                binding.LoanCreatelayout.visibility = View.GONE
                                binding.nextlayout.visibility = View.VISIBLE
                                CheckOnlineOrOffline =""
                                updateUI(false)
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@QRCodePage, DashBoard::class.java))
                                finish()
                                clearData()
                            }
                            else if (body!!.statuss.equals("401")) {
                                if (ConstantClass.dialog?.isShowing == true) {
                                    ConstantClass.dialog.dismiss()
                                }
                                updateUI(false)
                                binding.nextlayout.isEnabled= true
                                binding.LoanCreatelayout.visibility = View.GONE
                                binding.nextlayout.visibility = View.VISIBLE
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_LONG).show()
                            }
                            else {
                                var customerCode = body?.customerCode

                                if (body.statuss.equals("218")) {
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    binding.nextlayout.visibility = View.VISIBLE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                } else if (body.statuss.equals("219")) {
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    binding.nextlayout.visibility = View.VISIBLE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else if(body.statuss.equals("226")){
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    binding.nextlayout.visibility = View.VISIBLE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else if(body.statuss.equals("213")){
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    binding.nextlayout.visibility = View.VISIBLE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    val safecustomerCode = if (!customerCode.isNullOrBlank() && customerCode != "null") customerCode else ""
                                    if(safecustomerCode.isNullOrBlank()){
                                        if (ConstantClass.dialog?.isShowing == true) {
                                            ConstantClass.dialog.dismiss()
                                        }
                                        updateUI(false)
                                        binding.nextlayout.isEnabled= true
                                        binding.LoanCreatelayout.visibility = View.GONE
                                        binding.nextlayout.visibility = View.VISIBLE
                                        Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                    }
                                    else{
                                        val startDate = getCurrentStartDate()
                                        val endDate = calculateEmiEndDateFromNow(Tenure.toInt())

                                        loancreatedreq = LoanCreatedReq(
                                            modetype = "INSERT",
                                            rid = 0,
                                            customerCode = safecustomerCode,
                                            loanAmount = ConstantClass.LoanAmount.toDouble(),
                                            downPayment = downPayment.toDouble(),
                                            emiAmount = EmiAmount.toDouble(),
                                            tenure = Tenure.toInt(),
                                            interestRate = InterestRate.toDouble(),
                                            startDate = startDate,
                                            endDate = endDate,
                                            imeiNumber = ImeiNumber1,
                                            createdBy = createdBy,
                                            brandname = BrandName,
                                            modelname = ModelName,
                                            variantname = ModelVarient,
                                            avlcolor = ModelColor,
                                            retailerCode = retailercode,
                                            processingFees = ProcessingFees,
                                            interestAmt = InterestAmt,
                                            remarks = "",
                                            recordStatus = LoanStatus,
                                            creditScore = userScore.toString(),
                                            validateKey = "",
                                            defaultEmidebit = DefaulterEmiDebitPending,
                                            sellingPrice = ConstantClass.SellingPrice.toDouble(),
                                            loanMode = ConstantClass.online
                                        )
                                        LoanMode = ConstantClass.online

                                        Log.d("LoanCreateReq", Gson().toJson(loancreatedreq))

                                        if (ConstantClass.dialog?.isShowing == true) {
                                            ConstantClass.dialog.dismiss()
                                        }
                                        updateUI(true)

                                        binding.LoanCreatelayout.visibility = View.VISIBLE
                                        binding.nextlayout.visibility = View.GONE

                                    }

                                }
                            }
                        }
                        else {
                            val err = response.errorBody()?.string()
                            Log.e("API_RESPONSE_ERROR", err ?: "Unknown error")
                            Toast.makeText(this@QRCodePage, err ?: "Unknown error", Toast.LENGTH_LONG).show()
                            binding.nextlayout.isEnabled= true
                        }

                    }

                } catch (e: Exception) {
                    ConstantClass.dialog?.takeIf { it.isShowing }?.dismiss()
                    binding.nextlayout.isEnabled= true
                    Log.e("API_EXCEPTION", "Error: ${e.localizedMessage}", e)
                }
            }

        }

        else {

            if (ConstantClass.CheckOnlineOrOffline.equals(ConstantClass.offline)) {
                userScore = 0.0f
            }
            else {

            }

            val requestMap = hashMapOf(
                "Mode" to "INSERT".toRequestBody(),
                "FirstName" to CustFirstName.toRequestBody(),
                "MiddleName" to CustMiddleName.toRequestBody(),
                "LastName" to CustLastName.toRequestBody(),
                "PrimaryMobileNumber" to CustPrimaryMobileNumber.toRequestBody(),
                "PrimaryOTP" to CustPrimaryOTP.toRequestBody(),
                "PrimaryMobileVerified" to CustPrimaryMobileVerified.toRequestBody(),
                "AlternateMobileNumber" to CustAlternateMobileNumber.toRequestBody(),
                "AlternateMobileOTP" to CustAlternateMobileOTP.toRequestBody(),
                "PAlternateMobileVerified" to CustAlternateMobileVerified.toRequestBody(),
                "EMailID" to CusteMailID.toRequestBody(),
                "FlatNo" to CustFlatNo.toRequestBody(),
                "AearSector" to CustAreaSector.toRequestBody(),
                "PinCode" to CustPinCode.toRequestBody(),
                "CurrentAddress" to CustCurrentAddress.toRequestBody(),
                "StateName" to CustStateName.toRequestBody(),
                "CityName" to CustCityName.toRequestBody(),
                "Country" to CustCountry!!.toRequestBody(),
                "AadharNumber" to AadharNumber.toRequestBody(),
                "AadharNumberVerified" to ConstantClass.AadharVerified.toRequestBody(),/*"".toRequestBody()*/
                "PANNumber" to PanNumber.toRequestBody(),
                "PANNumberVerified" to PanNumberVerified.toRequestBody(), /*"".toRequestBody()*/
                "BrandName" to BrandName.toRequestBody(),
                "ModelName" to ConstantClass.ModelName.toRequestBody(),
                "ModelVariant" to ConstantClass.ModelVarient.toRequestBody(),
                "Color" to ConstantClass.ModelColor.toRequestBody(),
                "SellingPrice" to ConstantClass.SellingPrice.toRequestBody(),
                "DownPayment" to downPayment.toRequestBody(),
                "Tenure" to ConstantClass.Tenure.toRequestBody(),
                "EMIAmount" to EmiAmount.toRequestBody(),
                "IMEINumber1" to ImeiNumber1.toRequestBody(),
                "IMEINumber2" to ImeiNumber2.toRequestBody(),
                "AccountNumber" to AccountNumber.toRequestBody(),
                "BankIFSCCode" to BankIFSCCode.toRequestBody(),
                "BankName" to BankName.toRequestBody(),
                "AccountType" to AccountType.toRequestBody(),
                "BranchName" to BranchName.toRequestBody(),
                "RefName" to RefName.toRequestBody(),
                "MemberShipFees" to membershipAmt.toRequestBody(),
                "RefRelationShip" to RefRelationShip.toRequestBody(),
                "RefmobileNo" to RefmobileNo.toRequestBody(),
                "RefAddress" to RefAddress.toRequestBody(),
                "DebitOrCreditCard" to "".toRequestBody(),
                "UPIMandate" to "yes".toRequestBody(),
                "CreatedBy" to createdBy.toRequestBody(),
                "RetailerCode" to retailercode.toRequestBody(),
                "CustomerCodes" to "".toRequestBody(),
                "PanApiResponse" to (PanResponse ?: "").toRequestBody(),
                "AadhaarApiResponse" to (AadhaarResponse ?: "").toRequestBody(),
                "CibilApiResponse" to (CibilResponse ?: "").toRequestBody(),
                "CibilScore" to userScore.toString().toRequestBody(),
                "IsAggrementVerified" to isAggrementVerified.toRequestBody(),
                "IsRetailerAggrementVerified" to IsRetailerAggrementVerified.toRequestBody(),
                "IsRefAdhaarVerified" to ReferenceAadharVerified.toRequestBody(),
                "IsRefAadhaarNumber" to ReferenceAadharNumber.toRequestBody()
            )

            Log.d("RefVerified", "${ ReferenceAadharVerified } ${ ReferenceAadharNumber }")

            Log.d("RequestRegis", requestMap.toString())

            if (ConstantClass.CheckOnlineOrOffline.equals(ConstantClass.offline)) {
                lifecycleScope.launch {
                    try {
                        val response = api.getRegisterCustomerReq(
                            requestMap["Mode"]!!,
                            requestMap["FirstName"]!!,
                            requestMap["MiddleName"]!!,
                            requestMap["LastName"]!!,
                            requestMap["PrimaryMobileNumber"]!!,
                            requestMap["PrimaryOTP"]!!,
                            requestMap["PrimaryMobileVerified"]!!,
                            requestMap["AlternateMobileNumber"]!!,
                            requestMap["AlternateMobileOTP"]!!,
                            requestMap["PAlternateMobileVerified"]!!,
                            requestMap["EMailID"]!!,
                            requestMap["FlatNo"]!!,
                            requestMap["AearSector"]!!,
                            requestMap["PinCode"]!!,
                            requestMap["CurrentAddress"]!!,
                            requestMap["StateName"]!!,
                            requestMap["CityName"]!!,
                            requestMap["Country"]!!,
                            requestMap["AadharNumber"]!!,
                            requestMap["AadharNumberVerified"]!!,
                            requestMap["PANNumber"]!!,
                            requestMap["PANNumberVerified"]!!,
                            requestMap["BrandName"]!!,
                            requestMap["ModelName"]!!,
                            requestMap["ModelVariant"]!!,
                            requestMap["Color"]!!,
                            requestMap["SellingPrice"]!!,
                            requestMap["DownPayment"]!!,
                            requestMap["Tenure"]!!,
                            requestMap["EMIAmount"]!!,
                            requestMap["IMEINumber1"]!!,
                            requestMap["IMEINumber2"]!!,
                            requestMap["AccountNumber"]!!,
                            requestMap["BankIFSCCode"]!!,
                            requestMap["BankName"]!!,
                            requestMap["AccountType"]!!,
                            requestMap["BranchName"]!!,
                            requestMap["RefName"]!!,
                            requestMap["RefRelationShip"]!!,
                            requestMap["RefmobileNo"]!!,
                            requestMap["RefAddress"]!!,
                            requestMap["DebitOrCreditCard"]!!,
                            requestMap["UPIMandate"]!!,
                            requestMap["CreatedBy"]!!,
                            requestMap["MemberShipFees"]!!,
                            requestMap["RetailerCode"]!!,
                            requestMap["CustomerCodes"]!!,
                            requestMap["CibilScore"]!!,
                            requestMap["IsAggrementVerified"]!!,
                            requestMap["IsRetailerAggrementVerified"]!!,
                            custPhotoPart!!,
                            imei1SealPart!!,
                            imei2SealPart!!,
                            imeiPhotoPart!!,
                            null,
                            aadharFrontPart!!,
                            aadharBackPart!!,
                            PanFrontPart!!
                        )


                        if (response.isSuccessful) {
                            // Handle success
                            val body = response.body()

                            var message = body?.message ?: "Success"

                            Log.d("createcustresp", Gson().toJson(body))


                            if (body!!.statuss.equals("FAILED")) {
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                CheckOnlineOrOffline =""
                                updateUI(false)
                                binding.LoanCreatelayout.visibility = View.GONE
                                binding.nextlayout.visibility = View.VISIBLE
                                Log.d("customer create", body.message)
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@QRCodePage, DashBoard::class.java))
                                finish()
                                clearData()
                            }
                            else if (body!!.statuss.equals("401")) {
                                binding.LoanCreatelayout.visibility = View.GONE
                                binding.nextlayout.visibility = View.VISIBLE
                                updateUI(false)
                                binding.nextlayout.isEnabled= true
                                if (ConstantClass.dialog?.isShowing == true) {
                                    ConstantClass.dialog.dismiss()
                                }
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_LONG).show()
                            }
                            else {
                                var customerCode = body?.customerCode
                                if (body.statuss.equals("218")) {
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    binding.nextlayout.visibility = View.VISIBLE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()

                                } else if (body.statuss.equals("219")) {
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    binding.nextlayout.visibility = View.VISIBLE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else if(body.statuss.equals("226")){
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.nextlayout.visibility = View.VISIBLE
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else if(body.statuss.equals("213")){
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    updateUI(false)
                                    binding.nextlayout.isEnabled= true
                                    binding.nextlayout.visibility = View.VISIBLE
                                    binding.LoanCreatelayout.visibility = View.GONE
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    val safecustomerCode = if (!customerCode.isNullOrBlank() && customerCode != "null") customerCode else ""
                                    if(safecustomerCode.isNullOrBlank()){
                                        if (ConstantClass.dialog?.isShowing == true) {
                                            ConstantClass.dialog.dismiss()
                                        }
                                        updateUI(false)
                                        binding.nextlayout.isEnabled= true
                                        binding.nextlayout.visibility = View.VISIBLE
                                        binding.LoanCreatelayout.visibility = View.GONE
                                        Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                    }
                                    else
                                    {
                                        val startDate = getCurrentStartDate()
                                        val endDate = calculateEmiEndDateFromNow(Tenure.toInt())

                                        loancreatedreq = LoanCreatedReq(
                                            modetype = "INSERT",
                                            rid = 0,
                                            customerCode = safecustomerCode,
                                            loanAmount = ConstantClass.LoanAmount.toDouble(),
                                            downPayment = downPayment.toDouble(),
                                            emiAmount = EmiAmount.toDouble(),
                                            tenure = Tenure.toInt(),
                                            interestRate = InterestRate.toDouble(),
                                            startDate = startDate,
                                            endDate = endDate,
                                            imeiNumber = ImeiNumber1,
                                            createdBy = createdBy,
                                            brandname = BrandName,
                                            modelname = ModelName,
                                            variantname = ModelVarient,
                                            avlcolor = ModelColor,
                                            retailerCode = retailercode,
                                            processingFees = ProcessingFees,
                                            interestAmt = InterestAmt,
                                            remarks = "",
                                            recordStatus = LoanStatus,
                                            creditScore = userScore.toString(),
                                            validateKey = "",
                                            defaultEmidebit = DefaulterEmiDebitPending,
                                            sellingPrice = ConstantClass.SellingPrice.toDouble(),
                                            loanMode = ConstantClass.offline
                                        )

                                        LoanMode = ConstantClass.offline

                                        Log.d("LoanCreateReq", Gson().toJson(loancreatedreq))

                                        if (ConstantClass.dialog?.isShowing == true) {
                                            ConstantClass.dialog.dismiss()
                                        }
                                        updateUI(true)

                                        binding.LoanCreatelayout.visibility = View.VISIBLE
                                        binding.nextlayout.visibility = View.GONE
                                    }
                                }

                            }

                        }
                        else {
                            val errorMsg = response.errorBody()?.string()
                            handleApiError(response.code(), errorMsg)
                        }
                    }
                    catch (e: Exception) {
                        if (ConstantClass.dialog?.isShowing == true) {
                            ConstantClass.dialog.dismiss()
                        }
                        binding.nextlayout.isEnabled= true

                        val errorMsg = when (e) {
                            is java.net.SocketTimeoutException ->
                                "Connection timed out. Please check internet."

                            is java.net.UnknownHostException ->
                                "No internet connection."

                            is java.io.IOException ->
                                "Network error. Please try again."

                            else ->
                                "Unexpected error occurred."
                        }

                        Log.e("API_EXCEPTION", e.message ?: "Unknown Exception")

                        Toast.makeText(this@QRCodePage, errorMsg, Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                lifecycleScope.launch {
                    try {
                        val response = api.getRegisterOnlineCustomerReq(
                            requestMap["Mode"]!!,
                            requestMap["FirstName"]!!,
                            requestMap["MiddleName"]!!,
                            requestMap["LastName"]!!,
                            requestMap["PrimaryMobileNumber"]!!,
                            requestMap["PrimaryOTP"]!!,
                            requestMap["PrimaryMobileVerified"]!!,
                            requestMap["AlternateMobileNumber"]!!,
                            requestMap["AlternateMobileOTP"]!!,
                            requestMap["PAlternateMobileVerified"]!!,
                            requestMap["EMailID"]!!,
                            requestMap["FlatNo"]!!,
                            requestMap["AearSector"]!!,
                            requestMap["PinCode"]!!,
                            requestMap["CurrentAddress"]!!,
                            requestMap["StateName"]!!,
                            requestMap["CityName"]!!,
                            requestMap["Country"]!!,
                            requestMap["AadharNumber"]!!,
                            requestMap["AadharNumberVerified"]!!,
                            requestMap["PANNumber"]!!,
                            requestMap["PANNumberVerified"]!!,
                            requestMap["BrandName"]!!,
                            requestMap["ModelName"]!!,
                            requestMap["ModelVariant"]!!,
                            requestMap["Color"]!!,
                            requestMap["SellingPrice"]!!,
                            requestMap["DownPayment"]!!,
                            requestMap["Tenure"]!!,
                            requestMap["EMIAmount"]!!,
                            requestMap["IMEINumber1"]!!,
                            requestMap["IMEINumber2"]!!,
                            requestMap["AccountNumber"]!!,
                            requestMap["BankIFSCCode"]!!,
                            requestMap["BankName"]!!,
                            requestMap["AccountType"]!!,
                            requestMap["BranchName"]!!,
                            requestMap["RefName"]!!,
                            requestMap["RefRelationShip"]!!,
                            requestMap["RefmobileNo"]!!,
                            requestMap["RefAddress"]!!,
                            requestMap["DebitOrCreditCard"]!!,
                            requestMap["UPIMandate"]!!,
                            requestMap["CreatedBy"]!!,
                            requestMap["MemberShipFees"]!!,
                            requestMap["RetailerCode"]!!,
                            requestMap["CustomerCodes"]!!,
                            requestMap["PanApiResponse"]!!,
                            requestMap["AadhaarApiResponse"]!!,
                            requestMap["CibilApiResponse"]!!,
                            requestMap["CibilScore"]!!,
                            requestMap["IsAggrementVerified"]!!,
                            requestMap["IsRetailerAggrementVerified"]!!,
                            requestMap["IsRefAdhaarVerified"]!!,
                            requestMap["IsRefAadhaarNumber"]!!,
                            custPhotoPart!!,
                            imei1SealPart!!,
                            imei2SealPart!!,
                            imeiPhotoPart!!,
                            null
                        )

                        if (response.isSuccessful) {
                            // Handle success
                            val body = response.body()
                            /* ConstantClass.dialog.dismiss()
                             startActivity(Intent(this@QRCodePage,CongratulationPage::class.java))*/
                            var message = body?.message ?: "Success"

                            Log.d("createcustresp", Gson().toJson(body))


                            if (body!!.statuss.equals("FAILED")) {
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                updateUI(false)
                                CheckOnlineOrOffline =""
                                binding.nextlayout.visibility = View.VISIBLE
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@QRCodePage, DashBoard::class.java))
                                finish()
                                clearData()
                            }

                            else if (body!!.statuss.equals("401")) {
                                if (ConstantClass.dialog?.isShowing == true) {
                                    ConstantClass.dialog.dismiss()
                                }
                                updateUI(false)
                                binding.nextlayout.isEnabled= true
                                binding.nextlayout.visibility = View.VISIBLE
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_LONG).show()
                            }

                            else if(body.statuss.equals("226")){
                                if (ConstantClass.dialog?.isShowing == true) {
                                    ConstantClass.dialog.dismiss()
                                }
                                updateUI(false)
                                binding.nextlayout.isEnabled= true
                                binding.nextlayout.visibility = View.VISIBLE
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                            }
                            else if(body.statuss.equals("213")){
                                if (ConstantClass.dialog?.isShowing == true) {
                                    ConstantClass.dialog.dismiss()
                                }
                                updateUI(false)
                                binding.nextlayout.isEnabled= true
                                binding.nextlayout.visibility = View.VISIBLE
                                Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                            }

                            else {
                                var customerCode = body?.customerCode

                                val safecustomerCode = if (!customerCode.isNullOrBlank() && customerCode != "null") customerCode else ""

                                if(safecustomerCode.isNullOrBlank()){
                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    binding.nextlayout.isEnabled= true
                                    binding.nextlayout.visibility = View.VISIBLE
                                    updateUI(false)
                                    Toast.makeText(this@QRCodePage, body.message, Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    val startDate = getCurrentStartDate()
                                    val endDate = calculateEmiEndDateFromNow(Tenure.toInt())

                                    loancreatedreq = LoanCreatedReq(
                                        modetype = "INSERT",
                                        rid = 0,
                                        customerCode = safecustomerCode,
                                        loanAmount = ConstantClass.LoanAmount.toDouble(),
                                        downPayment = downPayment.toDouble(),
                                        emiAmount = EmiAmount.toDouble(),
                                        tenure = Tenure.toInt(),
                                        interestRate = InterestRate.toDouble(),
                                        startDate = startDate,
                                        endDate = endDate,
                                        imeiNumber = ImeiNumber1,
                                        createdBy = createdBy,
                                        brandname = BrandName,
                                        modelname = ModelName,
                                        variantname = ModelVarient,
                                        avlcolor = ModelColor,
                                        retailerCode = retailercode,
                                        processingFees = ProcessingFees,
                                        interestAmt = InterestAmt,
                                        remarks = "",
                                        recordStatus = LoanStatus, //Approved
                                        creditScore = userScore.toString(),
                                        validateKey = "",
                                        defaultEmidebit = DefaulterEmiDebitAutoApproved,
                                        sellingPrice = ConstantClass.SellingPrice.toDouble(),
                                        loanMode =  ConstantClass.online
                                    )

                                    LoanMode = ConstantClass.online

                                    Log.d("LoanCreateReq", Gson().toJson(loancreatedreq))

                                    if (ConstantClass.dialog?.isShowing == true) {
                                        ConstantClass.dialog.dismiss()
                                    }

                                    binding.LoanCreatelayout.visibility = View.VISIBLE
                                    updateUI(true)

                                }
                            }

                        }

                        else {
                            val errorMsg = response.errorBody()?.string()
                            handleApiError(response.code(), errorMsg)
                        }

                    }
                    catch (e: Exception) {

                        if (ConstantClass.dialog?.isShowing == true) {
                            ConstantClass.dialog.dismiss()
                        }
                        binding.nextlayout.isEnabled= true

                        val errorMsg = when (e) {
                            is java.net.SocketTimeoutException ->
                                "Connection timed out. Please check internet."

                            is java.net.UnknownHostException ->
                                "No internet connection."

                            is java.io.IOException ->
                                "Network error. Please try again."

                            else ->
                                "Unexpected error occurred."

                        }

                        Log.e("API_EXCEPTION", e.message ?: "Unknown Exception")

                        Toast.makeText(this@QRCodePage, errorMsg, Toast.LENGTH_LONG).show()

                    }

                }
            }
        }

    }


    
    fun updateUI(isRegistered: Boolean) {
        if (isRegistered) {
            // STEP 2: Registered
            showSuccessPopup()
            binding.statusCardContainer.setBackgroundResource(R.drawable.bg_status_success)
            binding.statusTitle.text = "Customer Status"
            binding.statusTitle.setTextColor(Color.parseColor("#2E7D32")) // Green
            binding.statusIcon.setImageResource(R.drawable.ic_check_circle)
            binding.statusIcon.setBackgroundResource(R.drawable.bg_circle_light_green)
            binding.statusTextMain.text = "Registered Successfully"
            binding.statusTextSub.text = "You can now create a loan for this customer."

            binding.nextlayout.visibility = View.GONE
            binding.LoanCreatelayout.visibility = View.VISIBLE
        }
        else
        {
            // STEP 1: Not Registered
            binding.statusCardContainer.setBackgroundResource(R.drawable.bg_status_pending)
            binding.statusTitle.setTextColor(Color.parseColor("#7B1FA2")) // Purple
            binding.statusIcon.setImageResource(R.drawable.ic_person_add)
            binding.statusIcon.setBackgroundResource(R.drawable.bg_circle_light_purple)

            binding.nextlayout.visibility = View.VISIBLE
            binding.LoanCreatelayout.visibility = View.GONE
        }

    }


    private fun showSuccessPopup() {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_success)
        dialog.setCancelable(false)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            if (dialog.isShowing) {
                dialog.dismiss()
            }

        }, 3000)

    }


    private fun handleApiError(responseCode: Int, errorBody: String?) {
        if (ConstantClass.dialog?.isShowing == true) {
            ConstantClass.dialog.dismiss()
        }
        binding.nextlayout.isEnabled= true

        var message = when (responseCode) {
            400 -> "Bad request. Please check entered data (400)."
            401 -> "Session expired. Please login again (401)."
            403 -> "You are not authorized to perform this action (403)."
            404 -> "Service not found. Please try again later (404)."
            500 -> "Server error. Please try after some time (500)."
            else -> "Something went wrong. Please try again."
        }

        // Try to parse specific error message from body if available
        try {
            if (!errorBody.isNullOrBlank()) {
                val jsonObject = JSONObject(errorBody)
                if (jsonObject.has("message")) {
                    message = jsonObject.getString("message")
                } else if (jsonObject.has("Message")) {
                    message = jsonObject.getString("Message")
                }
            }
        } catch (e: Exception) {
            // Fallback to default message if parsing fails
        }

        Log.e("API_ERROR", "Code: $responseCode Body: $errorBody")

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    fun hitApiForRetailerCreatedLoan(requset: LoanCreatedReq) {

        Log.d("customerReq", Gson().toJson(requset))
        viewModel.getRetailerLoanCreatedReq(requset).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("customerres", Gson().toJson(response))

                                if (response.status?.toLowerCase().equals(ConstantClass.LoanSuccessStatus)) {
                                    loaneCode = response.data!!.loanCode!!
                                    ConstantClass.LoanRID = response.data!!.rid!!
                                    FirstName = CustFirstName
                                    MiddleName = CustMiddleName
                                    LastName = CustLastName
                                    CustomerCodeForEnach = response.data!!.customerCode!!
                                    LoanCodeForEnach = response.data!!.loanCode!!
                                    RetailerCodeForEnach = response.data!!.retailerCode!!

                                    LoanStartDate = response.data.startDate!!
                                    LoanEndDate = response.data.endDate!!

                                    val emiAmount = EmiAmount.toDouble().roundToInt()


                                    val request = EMandateRequest(
                                        categoryID = 7,
                                        collectionAmount = emiAmount,
                                        collectCollectionUntilCancle = false,
                                        seqType = "RCUR",
                                        iFSCCode = BankIFSCCode,
                                        frequncy = "MNTH",
                                        registrationID = if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                                            ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
                                        } else {
                                            ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
                                        },
                                        accountHolderName = ConstantClass.AccountHolderName,
                                        finalCollectionDate = LoanEndDate,
                                        loanNo = loaneCode,
                                        accountType = AccountType,
                                        emailAddress = CusteMailID,
                                        firstCollectionDate = LoanStartDate,
                                        mobileNumber = CustPrimaryMobileNumber,
                                        bankAccountNumberConfirmation = AccountNumber,
                                        addIn2 = BranchAddress,
                                        addIn3 = "",
                                        debitType = true,
                                        teleNumber = "",
                                        authType = "",
                                        bankID = BankID,
                                        bankAccountNumber = AccountNumber
                                    )

                                    hitApiForEnach(request,false)

                                }
                                else {
                                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    Toast.makeText(this,response!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }

                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                            ConstantClass.dialog.dismiss()
                        }

                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            // Network error or exception where response is null
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }

                }
            }
        }
        
    }


    fun String.toRequestBody(): RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), this)


    fun clearData() {
        CustFirstName = ""
        CustMiddleName = ""
        CustLastName = ""
        CustPrimaryMobileNumber = ""
        CustPrimaryOTP = ""
        CustPrimaryMobileVerified = ""
        CustAlternateMobileNumber = ""
        CustAlternateMobileOTP = ""
        CustAlternateMobileVerified = ""
        CusteMailID = ""
        CustFlatNo = ""
        CustAreaSector = ""
        CustPinCode = ""
        CustCurrentAddress = ""
        CustStateName = ""
        CustCityName = ""
        CustCountry = ""
        LoanStartDate = ""
        LoanEndDate= ""
        AadharNumber = ""
        PanNumber = ""
        BrandName = ""
        ConstantClass.ModelName = ""
        ConstantClass.ModelVarient = ""
        ConstantClass.ModelColor = ""
        loaneCode = ""
        ConstantClass.SellingPrice = ""
        ConstantClass.DownPayment = ""
        ConstantClass.Tenure = ""

        EmiAmount = ""

        ImeiNumber1 = ""
        ImeiNumber2 = ""

        AccountNumber = ""
        BankIFSCCode = ""
        BankName = ""
        AccountType = ""
        BranchName = ""

        RefName = ""
        RefRelationShip = ""
        RefmobileNo = ""
        RefAddress = ""
        ClickOnCardLowCibilScore = ""
        ConstantClass.ClickOnCardDashboard = ""
        CustCode = ""
        CibilResponse = ""
        userScore = 0f
        PanResponse = ""
        PanNumberVerified = ""
        PanNumber = ""
        AadharVerified = ""
        AadharNumber = ""
        CustPrimaryOTP = ""
        CustCode = ""
        iisAggrementVerified = false


    }


    fun hitApiForMemberShipFee() {
        val startTime = System.currentTimeMillis()
        var req = GetIsEligibleLoanReq(
            panNumber = PanNumber,
            aadharNumber = ""
        )

        Log.d("checkMemberShipReq", Gson().toJson(req))

        viewModel.getgetMemberShipReqeReq(req).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->
                                Log.d("API_TIME", "Response Time: ${System.currentTimeMillis() - startTime} ms")
                                val response = users?.body()
                                if (response != null) {
                                    Log.d("PanVerificationResp", Gson().toJson(response))
                                    if (response!!.statuss.equals("True")) {
                                        ConstantClass.dialog.dismiss()
                                        binding.membershipfee.text = "₹ ".plus(response.membershipFee?.toDouble())
                                        Log.d("membership", ":".plus(response.membershipFee))

                                        binding.downpayment.text = "₹ ".plus(DownPayment)
                                        val processingFee = (ToBePaidAmount.toDoubleOrNull() ?: 0.0) - (DownPayment.toDoubleOrNull() ?: 0.0)
                                        binding.processingfee.text = "₹ ${String.format("%.2f", processingFee)}"

                                        val amount = ToBePaidAmount.toDoubleOrNull() ?: 0.0
                                        Log.d("ToBePaidAmount", ":".plus(amount))
                                        val membership = response.membershipFee?.toDouble() ?: 0.0

                                        val totalamount = amount + membership

                                        binding.clientcode.text = "₹ ".plus(totalamount)
                                        Log.d("totalamt", ":".plus(totalamount))
                                        // downPayment = totalamount.toString() // downpayment+processing
                                        downPayment = DownPayment // downpayment+processing
                                        membershipAmt = "$membership"
                                    } else {
                                        ConstantClass.dialog.dismiss()
                                        finish()
                                    }
                                }
                                else {
                                    ConstantClass.dialog.dismiss()
                                    hitApiForMemberShipFee()
                                }

                            }

                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        Log.d("API_TIME", "Failed after: ${System.currentTimeMillis() - startTime} ms")
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }

    }



    @SuppressLint("SetTextI18n")
    fun OpenPopUpForVAlert() {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.signoutalert)


        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        dialog.setCanceledOnTouchOutside(false)

        val cancel = dialog.findViewById<Button>(R.id.btnCancel)
        val done = dialog.findViewById<Button>(R.id.btnLogout)
        val txt = dialog.findViewById<TextView>(R.id.dialog_message)
        val image = dialog.findViewById<ImageView>(R.id.imageview)

        image.visibility = View.VISIBLE

        done.text = "OK"

        txt.text = "Are you sure you want to go back?"

        done.setOnClickListener {
            finish()


        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    fun hitApiForLogin() {

        var sessionOutReq = SessionOutReq(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
        )

        Log.d("SessionOutReq", Gson().toJson(sessionOutReq))
        viewModel.getSessionReq(sessionOutReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("SessionOutResponse", Gson().toJson(response))
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                ConstantClass.checkActiveStatusAndLogout(
                                    this@QRCodePage,
                                    response.status,
                                    preference
                                )
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }


        var request = ValidateSessionRequest(
            preference.getStringValue(ConstantClass.RetailerCode, ""),
            preference.getStringValue(ConstantClass.DEVICEID, ""),
            preference.getStringValue(ConstantClass.FCMTOKEN, "")
        )

        Log.d("validaterequest", Gson().toJson(request))
        viewModel.getSessionExpiredReq(request).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("validateresp", Gson().toJson(response))
                                if (response.status == 0) {
                                    hitApiForRetailerLogout()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }


    fun hitApiForRetailerLogout() {
        var loginRequest = LogoutReq(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
        )

        Log.d("LogoutReq", Gson().toJson(loginRequest))

        viewModel.getLogout(loginRequest).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("LogoutResponse", Gson().toJson(response))
                                preference.setBooleanValue(ConstantClass.LoggedIn, false)
                                preference.setStringValue(ConstantClass.LoginType, "")
                                ConstantClass.ClickOnCardDashboard = ""
                                val intent = Intent(this@QRCodePage, ChooseYourRolePage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }


    fun hitApiForEnach(request: EMandateRequest,check: Boolean) {
        Log.d("eManadateReq", Gson().toJson(request))

        if(LoanMode.equals(ConstantClass.offline)) {

            panViewModel.getEMandateRequestReq(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data.let { users ->
                                users!!.body().let { response ->
                                    Log.d("eMandateRes", Gson().toJson(response))

                                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    binding.LoanCreatelayout.isEnabled= true

                                    if (response!!.data?.customer != null) {
                                        webUrl = response!!.data!!.url
                                        startActivity(Intent(this@QRCodePage, RetailerEMandateVerifyPage::class.java))
                                    }
                                    else {
                                        ConstantClass.dialog.dismiss()
                                        isEmandateVerified= "No"
                                        isEnachCancelled = true
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                    }

                                    if(!isEmandateVerified.isNullOrBlank()){
                                        var request = EnachDateUploadReq(
                                            isEmandateVerified = isEmandateVerified,
                                            emAccountType = AccountType,
                                            isPannydropVerified = isPannydropVerified,
                                            emAccountNumber = AccountNumber,
                                            customerCode = CustomerCodeForEnach,
                                            retailerCode= RetailerCodeForEnach,
                                            loanCode= loaneCode,
                                            emBankName=BankName,
                                            emIfscCode =BankIFSCCode
                                        )

                                        hitApiForUploadEnachMandateDataResponse(request)
                                    }

                                }

                            }

                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            val response = it.data
                            if (response != null) {
                                val errorBody = try {
                                    response.errorBody()?.string()
                                } catch (e: Exception) {
                                    null
                                }
                                handleApiError(response.code(), errorBody)
                            } else {
                                Toast.makeText(
                                    this,
                                    it.message ?: "Network error occurred. Please try again.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        ApiStatus.LOADING -> {
                            if(check){
                                ConstantClass.OpenPopUpForVeryfyOTP(this)
                            }
                        }

                    }

                }

            }
        }
        else{
            panViewModel.getEMandateOnlineRequest(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data.let { users ->
                                users!!.body().let { response ->
                                    Log.d("eMandateOnlineRes", Gson().toJson(response))

                                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                        ConstantClass.dialog.dismiss()
                                    }

                                    binding.LoanCreatelayout.isEnabled= true

                                    if (response!!.data?.customer != null) {
                                        webUrl = response!!.data!!.url
                                        startActivity(Intent(this@QRCodePage, RetailerEMandateVerifyPage::class.java))
                                    }
                                    else {
                                        ConstantClass.dialog.dismiss()
                                        isEmandateVerified= "No"
                                        isEnachCancelled = true
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                    }

                                    if(!isEmandateVerified.isNullOrBlank()){
                                        var request = EnachDateUploadReq(
                                            isEmandateVerified = isEmandateVerified,
                                            emAccountType = AccountType,
                                            isPannydropVerified = isPannydropVerified,
                                            emAccountNumber = AccountNumber,
                                            customerCode = CustomerCodeForEnach,
                                            retailerCode= RetailerCodeForEnach,
                                            loanCode= loaneCode,
                                            emBankName=BankName,
                                            emIfscCode =BankIFSCCode
                                        )

                                        hitApiForUploadEnachMandateDataResponse(request)
                                    }

                                }

                            }

                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            val response = it.data
                            if (response != null) {
                                val errorBody = try {
                                    response.errorBody()?.string()
                                } catch (e: Exception) {
                                    null
                                }
                                handleApiError(response.code(), errorBody)
                            } else {
                                Toast.makeText(
                                    this,
                                    it.message ?: "Network error occurred. Please try again.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        ApiStatus.LOADING -> {
                            if(check){
                                ConstantClass.OpenPopUpForVeryfyOTP(this)
                            }

                        }

                    }

                }

            }
        }

    }


    fun hitApiForCheckLoanCharge(loancreatedreq: LoanCreatedReq){

        var request = LoanChargeReq(
            registrationID =   if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                ConstantClass.PENNYDROP_REGISTRATION_ID
            } else {
                ConstantClass.PENNYDROP_REGISTRATION_ID_OFFLINE
            },
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, "")
        )

        Log.d("LoanChargeRequest", Gson().toJson(request))

        panViewModel.loanApplyChargesReq(request).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->
                                Log.d("LoanChargeRes", Gson().toJson(response))

                                if(response!!.status!!.lowercase().equals("true")){
                                    hitApiForRetailerCreatedLoan(loancreatedreq)
                                }
                                else {
                                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                        ConstantClass.dialog.dismiss()
                                    }
                                    binding.LoanCreatelayout.isEnabled= true
                                    if(response.message.isNullOrBlank()){
                                        Toast.makeText(this,"Loan charge failed", Toast.LENGTH_SHORT).show()
                                    }
                                    else{
                                        Toast.makeText(this,response!!.message.toString(), Toast.LENGTH_SHORT).show()
                                    }

                                }

                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        binding.LoanCreatelayout.isEnabled= true
                        if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                            ConstantClass.dialog.dismiss()
                        }
                        
                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }
    }


    fun  hitApiForUploadEnachMandateDataResponse(request:EnachDateUploadReq){

        Log.d("EmandateUploadreq", Gson().toJson(request))

        viewModel.UpdateEmandateDetails(request).observe(this){
            resources ->
            resources.let {

                when(it.apiStatus){
                    ApiStatus.SUCCESS ->{
                        it.data.let { users ->
                            users!!.body().let { response ->
                                Log.d("EmandateUploadRes", Gson().toJson(response))
                            }
                        }

                    }
                    ApiStatus.ERROR ->{
                        val response = it.data
                        if (response != null) {
                            val errorBody = try {
                                response.errorBody()?.string()
                            } catch (e: Exception) {
                                null
                            }
                            handleApiError(response.code(), errorBody)
                        } else {
                            Toast.makeText(
                                this,
                                it.message ?: "Network error occurred. Please try again.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }



                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }


    /* fun hitApiForEMandateStatus(request: ENachStatusReq) {
      Log.d("eManadateStatusReq", Gson().toJson(request))

      panViewModel.geteMandateSatusRequest(request).observe(this) { resources ->
          resources.let {
              when (it.apiStatus) {
                  ApiStatus.SUCCESS -> {
                      it.data.let { users ->
                          users!!.body().let { response ->
                              Log.d("eMandateStatusRes", Gson().toJson(response))
                              if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                  ConstantClass.dialog.dismiss()
                              }
                              if (response!!.statusCode.equals("NP000")) {
                                  CheckOnlineOrOffline =""
                                  Toast.makeText(this, "ENach Mandate is Active", Toast.LENGTH_SHORT).show()
                                  startActivity(Intent(this@QRCodePage, CongratulationPage::class.java))
                                  clearData()
                                  finish()

                              }
                              else {
                                  Toast.makeText(this, "ENach Mandate is not Active", Toast.LENGTH_SHORT).show()
                              }
                          }

                      }

                  }

                  ApiStatus.ERROR -> {
                      ConstantClass.dialog.dismiss()
                      // ✅ Print the full error details
                      Log.e("API_ERROR", "Status: ERROR")
                      Log.e("API_ERROR_CODE", resources.data?.code().toString())
                      Log.e("API_ERROR_MSG", resources.message ?: "Unknown Error")

                      Toast.makeText(this, "Server error occurred (Code: ${resources.data?.code() ?: "Unknown"})", Toast.LENGTH_LONG).show()

                      // Optional: Handle specific 500 error
                      if (resources.data?.code() == 500) {
                          Log.e("API_ERROR", "Internal Server Error from backend.")
                      }
                  }

                  ApiStatus.LOADING -> {

                  }

              }

          }

      }
  }*/

}
