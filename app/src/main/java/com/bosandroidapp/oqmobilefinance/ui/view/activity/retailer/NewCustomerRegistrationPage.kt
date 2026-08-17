package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bos.payment.appName.network.ApiInterface
import com.bos.payment.appName.network.RetrofitClient
import com.chaos.view.PinView
import com.bosandroidapp.bosmobilefinance.ui.slideshow.data.model.loginsignup.cibilscore.CibilScoreReq
import com.bosandroidapp.bosmobilefinance.ui.slideshow.ui.view.activity.retailer.cibilreportsfragment.BureauScore.Companion.userScore
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityNewCustomerRegistrationPageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadhaarName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadhaarResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharBackImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharFrontImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharHouse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharImage
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharLoc
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharPin
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharState
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharStreet
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharTransactionIdNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CheckOnlineOrOffline
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CibilResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileOTP
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAreaSector
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCityName
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber1SealPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber2SealPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumberPhotoPath
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Invoive_Path
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.OTPTYPE
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanBuilding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanCity
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanCountry
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanEmailId
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanFirstName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanFrontImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanLastName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanMiddleName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumberVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanPinCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanState
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.base64ToBitmap
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.bitmapToUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.createMultipartFromUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.getCityStateFromPincode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.iisAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isValidPinCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.scrollToView
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.uriToFile
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.validateLoginInput
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.VerifyCustomerReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.SendOtpReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.VerifyOTPReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.CibilRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CibilViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.CibilViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Locale


class NewCustomerRegistrationPage : BaseActivity() {
    lateinit var binding: ActivityNewCustomerRegistrationPageBinding

    lateinit var dialog: Dialog

    lateinit var api: ApiInterface
    lateinit var registerCustomerApiHolder: ApiInterface
    lateinit var countDownTimer: CountDownTimer
    private val CAMERA_REQUEST_CODE_FRONT = 1001
    private var photoUri: Uri? = null
    var mobileveryfied: Boolean = false
    var alternatemobileveryfied: Boolean = false
    var emailIdveryfied: Boolean = false

    lateinit var viewModel: AuthenticationViewModel
    private lateinit var viewCibilModel: CibilViewModel

    lateinit var preference: SharedPreference
    var clickmobile: Boolean = false
    var clickalternatemobile: Boolean = false
    var clickemailId: Boolean = false
    var EmailId: String = ""
    var customerImagePath: String? = ""
    var checkFirstName:Boolean = false
    var checkMiddleName:Boolean = false
    var checkLastName:Boolean = false
    var checkEmailId:Boolean = false
    var checkflathouse:Boolean = false
    var checkareasector:Boolean = false
    var checkcurrentaddress:Boolean = false
    var checkstatename:Boolean = false
    var checkcityName:Boolean = false

    private var verifyCustomerRetryCount = 0
    private val MAX_RETRY_COUNT = 3

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // Handle the photoUri, e.g., show image in ImageView
                AadharImage=""
                binding.userimage.visibility = View.VISIBLE
                binding.lockimage.visibility = View.GONE
                binding.userimage.setImageURI(photoUri)
                val imageFile = uriToFile(photoUri!!, this)
                customerImagePath = imageFile!!.absolutePath
                binding.imagenotuploaded.visibility = View.GONE
            }
            else{
                photoUri = null
                customerImagePath=""
            }

        }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewCustomerRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        viewCibilModel = ViewModelProvider(this, CibilViewModelFactory(CibilRepository(RetrofitClient.apiInterfacePAN)))[CibilViewModel::class.java]

        api = RetrofitClient.apiInterfaceSMS
        registerCustomerApiHolder = RetrofitClient.apiInterface
        preference = SharedPreference(this)
        setOnClickListner()
        setDataInUI()

    }


    override fun onResume() {
        super.onResume()

       if(CheckOnlineOrOffline.equals(ConstantClass.online)) {
           setDataIfGeetingPanVerification()
       }
        hitApiForLogin()
    }


    fun setDataInUI() {

        binding.firstName.addTextChangedListener(object :TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.firstName.error = "Emoji not allowed"
                        checkFirstName= false
                    }
                    else{
                        checkFirstName= true
                    }
                }
            }

        })

        binding.middleName.addTextChangedListener(object :TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.middleName.error = "Emoji not allowed"
                        checkMiddleName= false
                    }
                    else{
                        checkMiddleName= true
                    }
                }
            }

        })

        binding.lastName.addTextChangedListener(object :TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.lastName.error = "Emoji not allowed"
                        checkLastName= false
                    }
                    else{
                        checkLastName= true
                    }
                }
            }

        })

        binding.flathouseno.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.flathouseno.error = "Emoji not allowed"
                        checkflathouse= false
                    }
                    else{
                        checkflathouse= true
                    }
                }
            }

        })

        binding.areasector.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.areasector.error = "Emoji not allowed"
                        checkareasector= false
                    }
                    else{
                        checkareasector= true
                    }
                }
            }

        })

        binding.currentaddress.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.currentaddress.error = "Emoji not allowed"
                        checkcurrentaddress= false
                    }
                    else{
                        checkcurrentaddress= true
                    }
                }
            }

        })

        binding.statename.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.statename.error = "Emoji not allowed"
                        checkstatename= false
                    }
                    else{
                        checkstatename= true
                    }
                }
            }

        })

        binding.cityname.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.cityname.error = "Emoji not allowed"
                        checkcityName= false
                    }
                    else{
                        checkcityName= true
                    }
                }
            }

        })

        binding.mobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val primaryNumber = s.toString().trim()
                val altNumber = binding.alternatemobileNumber.text.toString().trim()

                if (primaryNumber.length == 10) {
                    // ✅ Check if alternate is same
                    if (primaryNumber == altNumber && altNumber.isNotEmpty()) {
                        binding.mobileNumber.error = "Primary mobile number should not be same as alternate number"
                        binding.verifymobilenumber.visibility = View.GONE
                        return
                    }

                    // ✅ Show verify if valid
                    binding.verifymobilenumber.visibility = View.VISIBLE

                } else {
                    binding.verifymobilenumber.visibility = View.GONE
                }
            }
        })

        binding.alternatemobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val altNumber = s.toString().trim()
                val primaryNumber = binding.mobileNumber.text.toString().trim()

                if (altNumber.length == 10) {

                    if (mobileveryfied) {
                        binding.verifymobilenumber.visibility = View.GONE
                    } else {
                        binding.verifymobilenumber.visibility = View.VISIBLE
                    }


                    if (altNumber == primaryNumber) {
                        binding.alternatemobileNumber.error = "Alternate mobile number should not be same as primary number"
                        binding.alternateverifymobilenumber.visibility = View.GONE
                        return
                    }

                    binding.alternateverifymobilenumber.visibility = View.VISIBLE

                } else {
                    binding.alternateverifymobilenumber.visibility = View.GONE
                }
            }
        })

        binding.emailId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                val altNumber = binding.alternatemobileNumber.text.toString().trim()
                val primaryNumber = binding.mobileNumber.text.toString().trim()

                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.emailId.error = "Emoji not allowed"
                        checkEmailId= false
                    }
                    else{
                        checkEmailId= true
                    }
                }

                if (s!!.length > 0) {

                    if (mobileveryfied) {
                        binding.verifymobilenumber.visibility = View.GONE
                    }
                    else {
                        if(altNumber == primaryNumber){
                            binding.verifymobilenumber.visibility = View.GONE
                        }else{
                            if(primaryNumber.isNullOrBlank()){
                                binding.verifymobilenumber.visibility = View.GONE
                            }else {
                                binding.verifymobilenumber.visibility = View.VISIBLE
                            }

                        }

                    }

                    if (alternatemobileveryfied) {
                        binding.alternateverifymobilenumber.visibility = View.GONE
                    }
                    else {
                        if(altNumber == primaryNumber){
                            binding.alternateverifymobilenumber.visibility = View.GONE
                        }else{
                            if(altNumber.isNullOrBlank()){
                                binding.alternateverifymobilenumber.visibility= View.GONE
                            }else {
                                binding.alternateverifymobilenumber.visibility = View.VISIBLE
                            }


                        }


                    }

                    binding.verifyEmailId.visibility = View.VISIBLE

                }
                else {
                    binding.verifyEmailId.visibility = View.GONE
                }
            }

        })

        binding.pincode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (isValidPinCode(s.toString())) {
                    getCityStateFromPincode(this@NewCustomerRegistrationPage, s.toString()) { city, state, country ->
                        if (city != null && state != null) {
                            binding.cityname.setText(city)
                            binding.statename.setText(state)
                            CustCountry = country
                        }
                        else {

                            binding.cityname.text.clear()
                            binding.statename.text.clear()
                            //Toast.makeText(this@NewCustomerRegistrationPage, "Invalid PIN or network error", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }

        })


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setOnClickListner() {

        binding.home.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            onBackPressed()
        }

        binding.acceptTermConditionCheck.setOnClickListener {

            OpenPopUpForTermCondition()

        }

        binding.cameraicon.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

        binding.back.setOnClickListener {
            OpenPopUpForVAlert()
        }

        binding.verifymobilenumber.setOnClickListener {
            clickemailId = false
            clickalternatemobile = false
            clickmobile = true
            var mobnumber = binding.mobileNumber.text.toString()

            if (validateLoginInput(mobnumber, this) && !binding.firstName.text.toString().isNullOrBlank() && !binding.lastName.text.toString().isNullOrBlank()) {
                if (isInternetAvailable(this@NewCustomerRegistrationPage)) {

                    //hitApiForVerifyCustomer()
                    hitApiForSendOTP(binding.mobileNumber.text.toString().trim(), OTPTYPE) //"Mobile"

                } else {
                    Toast.makeText(this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (binding.firstName.text.toString().isNullOrBlank() || binding.lastName.text.toString().isNullOrBlank()) {
                    Toast.makeText(this, "Please enter customer first or last name", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.alternateverifymobilenumber.setOnClickListener {
            clickemailId = false
            clickalternatemobile = true
            clickmobile = false
            var altmobnumber = binding.alternatemobileNumber.text.toString()
            if (validateLoginInput(altmobnumber, this) && !binding.firstName.text.toString().isNullOrBlank() && !binding.lastName.text.toString().isNullOrBlank()) {
                if (isInternetAvailable(this@NewCustomerRegistrationPage)) {
                    hitApiForSendOTP(altmobnumber, "Alter Mobile")
                } else {
                    Toast.makeText(this, "Please check your internet connection .", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (binding.firstName.text.toString().isNullOrBlank() || binding.lastName.text.toString().isNullOrBlank()) {
                    Toast.makeText(this, "Please enter customer first or last name .", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.verifyEmailId.setOnClickListener {
            clickemailId = true
            clickalternatemobile = false
            clickmobile = false
            var mailId = binding.emailId.text.toString()
            EmailId = mailId
            if (validateLoginInput(mailId, this)) {
                if (isInternetAvailable(this@NewCustomerRegistrationPage)) {
                    hitApiForSendOTP(mailId,OTPTYPE ) //"Email"
                }
                else {
                    Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.createaccount.setOnClickListener {

            val (isValid, errorMessage) = isValidForm(
                firstName = binding.firstName.text.toString().trim(),
                middleName = binding.middleName.text.toString().trim(),
                lastName = binding.lastName.text.toString().trim(),
                mobileNumber = binding.mobileNumber.text.toString().trim(),
                alternateMobile = binding.alternatemobileNumber.text.toString().trim(),
                houseNumber = binding.flathouseno.text.toString().trim(),
                areaSector = binding.areasector.text.toString().trim(),
                pinCode = binding.pincode.text.toString().trim(),
                currentAddress = binding.currentaddress.text.toString().trim(),
                state = binding.statename.text.toString(),
                city = binding.cityname.text.toString(),
                imagepath = customerImagePath,
                isAccepted = binding.acceptTermConditionCheck.isChecked,
                primarymobverified = CustPrimaryMobileVerified
            )

            if (!isValid) {
                Toast.makeText(this@NewCustomerRegistrationPage, errorMessage, Toast.LENGTH_SHORT).show()
            }
            else
               {
                CustPhotoPath = photoUri
                CustFirstName = binding.firstName.text.toString().trim()
                CustMiddleName = binding.middleName.text.toString().trim()
                CustLastName = binding.lastName.text.toString().trim()
                CustPrimaryMobileNumber = binding.mobileNumber.text.toString().trim()
                CustAlternateMobileNumber = binding.alternatemobileNumber.text.toString().trim()
                isAggrementVerified = "yes"
                CusteMailID = binding.emailId.text.toString().trim()
                CustFlatNo = binding.flathouseno.text.toString().trim()
                CustAreaSector = binding.areasector.text.toString().trim()
                CustCurrentAddress = binding.currentaddress.text.toString().trim()
                CustPinCode = binding.pincode.text.toString().trim()
                CustStateName = binding.statename.text.toString()
                CustCityName = binding.cityname.text.toString()
                ConstantClass.ClickOnCardDashboard = "Customer"

                if(CheckOnlineOrOffline.equals(ConstantClass.online)){
                    if (CustPrimaryMobileNumber.isNullOrBlank()) {
                        Toast.makeText(this@NewCustomerRegistrationPage, "Primary mobile number is mandatory.", Toast.LENGTH_SHORT).show()

                    } else if (CustPrimaryMobileNumber.length != 10) {
                        Toast.makeText(this@NewCustomerRegistrationPage, "Please enter a valid 10-digit primary mobile number.", Toast.LENGTH_SHORT).show()
                    }
                    else if (CustAlternateMobileNumber.isNullOrBlank()) {
                        Toast.makeText(this@NewCustomerRegistrationPage, "Alternate mobile number is mandatory.", Toast.LENGTH_SHORT).show()

                    }
                    else if (!CustAlternateMobileNumber.isNullOrBlank() && CustAlternateMobileNumber.length != 10) {

                        Toast.makeText(this@NewCustomerRegistrationPage, "Please enter a valid 10-digit alternate mobile number.", Toast.LENGTH_SHORT).show()

                    } else if (!CustAlternateMobileNumber.isNullOrBlank() && CustPrimaryMobileNumber == CustAlternateMobileNumber) {
                        Toast.makeText(this@NewCustomerRegistrationPage, "Primary and alternate mobile numbers should not be the same.", Toast.LENGTH_SHORT).show()

                    }
                    else {
                        hitApiForCibilReport()
                    }

                }

                else{

                    if(!CustAlternateMobileNumber.isNullOrBlank()){
                        startActivity(Intent(this@NewCustomerRegistrationPage, MobileSelectionActivity::class.java))
                    }
                    else {
                        binding.alternatemobileNumber.error= "Please enter alternate mobile number ."
                        scrollToView(binding.detaillayout,  binding.alternatemobileNumber)
                    }

                }

            }

        }


    }

    fun hitApiForVerifyCustomer(){
        var sendOtpReq = VerifyCustomerReq(
            primaryMobileNumber = binding.mobileNumber.text.toString().trim()
        )

        Log.d("verifycustomerreq", Gson().toJson(sendOtpReq))
        viewModel.getverifycustomerReq(sendOtpReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("verifycustomerresp", Gson().toJson(response))
                                ConstantClass.dialog.dismiss()

                                if(response.statuss!!.toLowerCase().equals("true", ignoreCase = true)){
                                    hitApiForSendOTP(binding.mobileNumber.text.toString().trim(), OTPTYPE) //"Mobile"
                                }
                                else{
                                    // if customer exist
                                    Toast.makeText(this@NewCustomerRegistrationPage,response.message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }


                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        hitApiForVerifyCustomer()
                    }


                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }


                }
            }
        }
    }


    fun OpenPopUpForVeryfyOTP(EmailID: String, otp: String) {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.verifyforgetpasswordotplayour)

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }


        dialog.setCanceledOnTouchOutside(false)


        val pinView=dialog.findViewById<PinView>(R.id.pinview)

        val verifyButton = dialog.findViewById<LinearLayout>(R.id.verifylayout)
        val cancel = dialog.findViewById<ImageView>(R.id.cancel)
        val resendlayout = dialog.findViewById<RelativeLayout>(R.id.resendlayout)
        val resendtxt = dialog.findViewById<TextView>(R.id.resendtxt)
        val timer = dialog.findViewById<TextView>(R.id.timer)
        val title = dialog.findViewById<TextView>(R.id.text_subtitle)

        startOtpTimer(resendtxt, timer)

        if (clickemailId) {
            title.text = "Enter four digit OTP send on your registered email id"
        } else {
            title.text = "Enter four digit OTP send on your registered mobile number"
        }


        cancel.setOnClickListener {
            dialog.dismiss()
        }

        resendlayout.setOnClickListener {

            if (validateLoginInput(EmailID, this)) {
                if (isInternetAvailable(this@NewCustomerRegistrationPage)) {
                    if (clickemailId) {
                        hitApiForReSendOTP(EmailID, OTPTYPE )
                    }
                    if (clickmobile) {
                        hitApiForReSendOTP(EmailID,OTPTYPE )
                    }
                    if (clickalternatemobile) {
                        hitApiForReSendOTP(EmailID, OTPTYPE)
                    }
                    startOtpTimer(resendtxt, timer)
                }
                else {
                    Toast.makeText(this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show()
                }

            }

        }


        verifyButton.setOnClickListener {
            val enteredOTP = pinView.getText().toString()
            if (enteredOTP.length == 4) {
                if (clickemailId) {
                    hitApiForOTPVerify(EmailID, enteredOTP, "Mail id verify")
                }
                if (clickmobile) {
                    hitApiForOTPVerify(EmailID, enteredOTP, "Mobile verify")
                }

                if (clickalternatemobile) {
                    hitApiForOTPVerify(EmailID, enteredOTP, "Alter Mobile verify")
                }

            } else {
                Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            }

        }

        dialog.show()

    }

    fun startOtpTimer(resendtxt: TextView, timer: TextView) {
        resendtxt.visibility = View.INVISIBLE
        timer.visibility = View.VISIBLE

        countDownTimer = object : CountDownTimer(5 * 60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                timer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                timer.text = "00:00"
                timer.visibility = View.INVISIBLE
                resendtxt.visibility = View.VISIBLE
            }
        }
        countDownTimer.start()
    }

    private fun createImageFile(): File {
        val fileName = "IMG_${System.currentTimeMillis()}"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }


    fun clickCameraForUploadDocument() {
        val photoFile = createImageFile()
        photoUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", photoFile)
        cameraLauncher.launch(photoUri!!)
    }


    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            clickCameraForUploadDocument()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE_FRONT
            )
        }
    }


    fun hitApiForReSendOTP(mailidormobile: String, type: String) {
        var sendOtpReq = SendOtpReq(
            mobileoremailId = mailidormobile,
            otpType = type
        )
        Log.d("SendOTPREQ", Gson().toJson(sendOtpReq))
        viewModel.sendOTPReq(sendOtpReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("SendRes", response.message)
                                if(response.statuss.equals("True")){
                                    if (clickemailId) {
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                    } else {
                                        if (clickmobile || clickalternatemobile) {
                                            var otp = response.value
                                            var firstName = binding.firstName.text.toString()
                                            var lastName = binding.lastName.text.toString()
                                            var customerName = firstName.plus(" ").plus(lastName)
                                            hitApiForResendMobVerify(mailidormobile, customerName, otp)
                                        }
                                    }
                                }
                                else{
                                Toast.makeText(this@NewCustomerRegistrationPage,response.message,Toast.LENGTH_SHORT).show()
                                ConstantClass.dialog.dismiss()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }
    }


    fun hitApiForSendOTP(mailidormobile: String, type: String) {
        var sendOtpReq = SendOtpReq(
            mobileoremailId = mailidormobile,
            otpType = type
        )
        Log.d("SendOTPREQ", Gson().toJson(sendOtpReq))

        viewModel.sendOTPReq(sendOtpReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("SendRes", response.message)
                                var otp = response.value
                                Log.d("OTP", otp)

                                if (response.statuss.equals("True")) {
                                    if (clickmobile || clickalternatemobile) {
                                        var firstName = binding.firstName.text.toString()
                                        var lastName = binding.lastName.text.toString()
                                        var customerName = firstName.plus(" ").plus(lastName)
                                        hitApiForMobVerify(mailidormobile, customerName, otp)
                                    }

                                    if (clickemailId) {
                                        ConstantClass.dialog.dismiss()
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                        OpenPopUpForVeryfyOTP(mailidormobile, "")
                                    }
                                }
                                else{
                                    Toast.makeText(this@NewCustomerRegistrationPage,response.message,Toast.LENGTH_SHORT).show()
                                    ConstantClass.dialog.dismiss()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }


                }
            }
        }
    }


    fun hitApiForOTPVerify(mobileOrEmailID: String, otp: String, message: String) {
        var verifyotpreq = VerifyOTPReq(
            mobileormailid = mobileOrEmailID,
            otp = otp,
            logintype = message
        )
        Log.d("VerifyOTPReq", Gson().toJson(verifyotpreq))
        viewModel.verifyOTPReq(verifyotpreq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("VerifyOTPRes", response.message)
                                if (response.statuss.equals("True")) {
                                    if (clickemailId) {
                                        EmailId = mobileOrEmailID
                                        emailIdveryfied = true
                                        binding.emailId.isEnabled = false
                                        binding.verifyiconemailId.visibility = View.VISIBLE
                                        binding.verifyEmailId.visibility = View.GONE
                                    }

                                    if (clickmobile) {
                                        CustPrimaryOTP = otp
                                        CustPrimaryMobileVerified = "yes"
                                        mobileveryfied = true
                                        binding.mobileNumber.isEnabled = false
                                        binding.verifyiconphonenumber.visibility = View.VISIBLE
                                        binding.verifymobilenumber.visibility = View.GONE
                                    }

                                    if (clickalternatemobile) {
                                        CustAlternateMobileOTP = otp
                                        CustAlternateMobileVerified = "yes"
                                        alternatemobileveryfied = true
                                        binding.alternatemobileNumber.isEnabled = false
                                        binding.alternateverifyiconphonenumber.visibility = View.VISIBLE
                                        binding.alternateverifymobilenumber.visibility = View.GONE
                                    }

                                    if (dialog != null && dialog.isShowing) {
                                        dialog.dismiss()
                                    }
                                } else {
                                    binding.emailId.isEnabled = true
                                    binding.verifyiconemailId.visibility = View.GONE
                                }

                                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }

        }

    }


    fun isValidForm(
        firstName: String,
        middleName: String,
        lastName: String,
        mobileNumber: String,
        alternateMobile: String,
        houseNumber: String,
        areaSector: String,
        pinCode: String,
        currentAddress: String,
        state: String,
        city: String,
        imagepath: String?,
        isAccepted: Boolean,
        primarymobverified: String,
    ): Pair<Boolean, String?> {

        if (firstName.isBlank()) {
            binding.firstName.error= "Please enter your first name."
            scrollToView(binding.detaillayout,  binding.firstName)
            return Pair(false, "Please enter your first name.")
        }
        else{
            binding.firstName.error = null
        }


        if (!checkFirstName){
            return Pair(false, "Please enter a valid first name.")
        }


        if (lastName.isBlank()) {
            binding.lastName.error= "Please enter your last name."
            scrollToView(binding.detaillayout,  binding.lastName)
            return Pair(false, "Please enter your last name.")
        }
        else {
            binding.lastName.error = null
        }


        if (!checkLastName) return Pair(false, "Please enter a valid last name.")


        if (!mobileNumber.matches(Regex("^[6-9]\\d{9}$"))) {
            binding.mobileNumber.error= "Please enter a valid 10-digit mobile number."
            scrollToView(binding.detaillayout,  binding.mobileNumber)
            return Pair(false, "Please enter a valid mobile number.")
        }
        else{
            binding.mobileNumber.error = null
        }


        if (alternateMobile.isNullOrBlank() && !alternateMobile.matches(Regex("^[6-9]\\d{9}$"))){
            binding.alternatemobileNumber.error= "Please enter a valid alternate mobile number."
            scrollToView(binding.detaillayout,  binding.alternatemobileNumber)
            return Pair(false, "Please enter a valid alternate mobile number.")
        }
        else{
            binding.alternatemobileNumber.error = null
        }

        if(binding.emailId.text.isNotEmpty()){
            if (!binding.emailId.text.toString().matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")))
                return Pair(false, "Enter valid email address")

            if (!checkEmailId)
                return Pair(false, "Enter valid email address")
        }

        if (houseNumber.isBlank()) {
            binding.flathouseno.error= "Please enter your house number."
            scrollToView(binding.detaillayout,  binding.flathouseno)
            return Pair(false, "Please enter your house number.")
        }
        else {
            binding.flathouseno.error = null
        }


        if (!checkflathouse) return Pair(false, "Please enter a valid house number.")


        if (areaSector.isBlank()){
            binding.areasector.error= "Please enter your area or sector."
            scrollToView(binding.detaillayout,  binding.areasector)
            return Pair(false, "Please enter your area or sector.")
        }
        else {
            binding.areasector.error = null
        }


        if (!checkareasector) return Pair(false, "Please enter a valid area or sector.")


        if (!pinCode.matches(Regex("^[1-9][0-9]{5}$"))) {
            binding.pincode.error= "Please enter a valid 6-digit PIN code."
            scrollToView(binding.detaillayout,  binding.pincode)
            return Pair(false, "Please enter a valid 6-digit PIN code.")
        }
        else{
            binding.pincode.error = null
        }


        if (currentAddress.isBlank()){
            binding.currentaddress.error= "Please enter your current address."
            scrollToView(binding.detaillayout,  binding.currentaddress)
            return Pair(false, "Please enter your current address.")
        }
        else {
            binding.currentaddress.error = null

        }

        if (!checkcurrentaddress) return Pair(false, "Please enter a valid current address.")

        if (state.isBlank()) {
            binding.statename.error= "Please select your state."
            scrollToView(binding.detaillayout,  binding.statename)
            return Pair(false, "Please select your state.")
        }
        else{
            binding.statename.error = null
        }

        if (!checkstatename) return Pair(false, "Please enter a valid state.")


        if (city.isBlank()) {
            binding.cityname.error= "Please select your city."
            scrollToView(binding.detaillayout,  binding.cityname)
            return Pair(false, "Please select your city.")
        }
        else{
            binding.cityname.error = null
        }

        if (!checkcityName) return Pair(false, "Please enter a valid city.")

        if (primarymobverified.isBlank()|| primarymobverified.isNotBlank() && !primarymobverified.equals("yes")){
            binding.mobileNumber.error= "Please verify your primary mobile number first."
            scrollToView(binding.detaillayout,  binding.mobileNumber)
            return Pair(false, "Please verify your primary mobile number first.")
        }else{
            binding.mobileNumber.error = null
        }

        if (imagepath!!.isBlank()){
            binding.imagenotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.imagenotuploaded)
            return Pair(false, "Please upload the customer photo.")
        }
        else{
            binding.imagenotuploaded.visibility = View.GONE
        }

        if (!isAccepted) return Pair(false, "Please accept the terms and conditions.")



        return Pair(true, null)
    }


    fun OpenPopUpForTermCondition() {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.term_condition_layout)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        dialog.setCanceledOnTouchOutside(false)

        var tvTermsContent = dialog.findViewById<TextView>(R.id.tvTermsContent)

        tvTermsContent.text = Html.fromHtml(getString(R.string.sample_terms_text_customer), Html.FROM_HTML_MODE_LEGACY)


        val verifyButton = dialog.findViewById<LinearLayout>(R.id.btnAccept)

        verifyButton.setOnClickListener {
            iisAggrementVerified = true
            binding.acceptTermConditionCheck.isChecked = true
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            // Called when dialog is dismissed by back press or programmatically
            if (iisAggrementVerified) {

            } else {
                binding.acceptTermConditionCheck.isChecked = false
                iisAggrementVerified = false
            }


        }

        dialog.show()

    }


    fun hitApiForMobVerify(mobnumber: String, customerName: String, OTP: String) {
        // hint: Dear  Naim Khan, Your OTP for Verification is 1234. Please Do Not Share the OTP With Anyone. Thanks For Using BOSOQ BOS CENTER
        var message = "Dear $customerName, Your OTP for Verification is $OTP. Please Do Not Share the OTP With Anyone. Thanks For Using BOSOQ BOS CENTER"

        lifecycleScope.launch {
            try {
                val response = api.sendSMSForVerifyMob(
                    apikey = ConstantClass.SMS_API_KEY,
                    senderid = ConstantClass.SMS_SENDER_ID,
                    templateid = ConstantClass.SMS_TEMPLATE_ID,
                    mobnumber = mobnumber,
                    message = message,
                )

                if (response!!.isSuccessful) {
                    Toast.makeText(this@NewCustomerRegistrationPage, "Otp sent on your mobile number!!", Toast.LENGTH_SHORT).show()
                    val loanData = response.body()

                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                        ConstantClass.dialog.dismiss()
                        OpenPopUpForVeryfyOTP(mobnumber, OTP)
                    }

                    Log.d("API_SUCCESS", loanData.toString())
                } else {
                    Log.e("API_ERROR", response.errorBody()?.string() ?: "Unknown error")
                }

            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.toString())
            }
        }

    }


    fun hitApiForResendMobVerify(mobnumber: String, customerName: String, OTP: String) {
        // hint: Dear  Naim Khan, Your OTP for Verification is 1234. Please Do Not Share the OTP With Anyone. Thanks For Using BOSOQ BOS CENTER
        var message = "Dear $customerName, Your OTP for Verification is $OTP. Please Do Not Share the OTP With Anyone. Thanks For Using BOSOQ BOS CENTER "

        lifecycleScope.launch {
            try {
                val response = api.sendSMSForVerifyMob(
                    apikey = ConstantClass.SMS_API_KEY,
                    senderid = ConstantClass.SMS_SENDER_ID,
                    templateid = ConstantClass.SMS_TEMPLATE_ID,
                    mobnumber = mobnumber,
                    message = message
                )

                if (response!!.isSuccessful) {
                    Toast.makeText(this@NewCustomerRegistrationPage, "Otp sent on your mobile number!!", Toast.LENGTH_SHORT).show()
                    val loanData = response.body()

                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                        ConstantClass.dialog.dismiss()
                    }
                    Log.d("API_SUCCESS", loanData.toString())
                } else {
                    Log.e("API_ERROR", response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                ConstantClass.dialog?.takeIf { it.isShowing }?.dismiss()

                val message = when (e) {
                    is java.net.SocketTimeoutException -> "The request timed out. Please try again."

                    is java.net.UnknownHostException -> "No internet connection. Please check your network and try again."

                    is java.net.ConnectException -> "Unable to connect to the server. Please try again later."

                    else -> "Something went wrong. Please try again."
                }

                Toast.makeText(this@NewCustomerRegistrationPage, message, Toast.LENGTH_LONG).show()

                Log.e("API_EXCEPTION", e.toString())
            }
        }

    }



    override fun onBackPressed() {
        PanNumber=""
        PanFrontImageUri= null
        ConstantClass.AadharVerified=""
        CheckOnlineOrOffline =""
        AadharTransactionIdNo =""
        iisAggrementVerified = false
        isAggrementVerified = ""
        super.onBackPressed()
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
            onBackPressed()
        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }



    fun containsEmoji(text: String): Boolean {
        for (char in text) {
            val type = Character.getType(char)
            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                return true
            }
        }
        return false
    }

    fun setDataIfGeetingPanVerification(){

        Log.d("PanVerification", "PanFirstName: $PanFirstName")
        Log.d("PanVerification", "PanMiddleName: $PanMiddleName")
        Log.d("PanVerification", "PanLastName: $PanLastName")
        Log.d("PanVerification", "PanMobileNumber: $PanMobileNumber")
        Log.d("PanVerification", "PanEmailId: $PanEmailId")
        Log.d("PanVerification", "PanBuilding: $PanBuilding")
        Log.d("PanVerification", "PanAddress: $PanAddress")
        Log.d("PanVerification", "PanPinCode: $PanPinCode")
        Log.d("PanVerification", "PanState: $PanState")
        Log.d("PanVerification", "PanCity: $PanCity")
        Log.d("PanVerification", "PanCountry: $PanCountry")
        Log.d("PanVerification", "AadhaarName: $AadhaarName")
        Log.d("PanVerification", "AadharHouse: $AadharHouse")
        Log.d("PanVerification", "AadharStreet: $AadharStreet")
        Log.d("PanVerification", "AadharPin: $AadharPin")
        Log.d("PanVerification", "AadharState: $AadharState")
        Log.d("PanVerification", "AadharLoc: $AadharLoc")
        Log.d("PanVerification", "AadharImage: ${AadharImage.take(30)}...")

        if(PanFirstName.isNotEmpty()){
            binding.firstName.setText(PanFirstName)
        }
        else {
            binding.firstName.setText(AadhaarName)
        }

        binding.middleName.setText(PanMiddleName)
        binding.lastName.setText(PanLastName)

        if(binding.verifyiconphonenumber.visibility==View.GONE){
            binding.mobileNumber.setText(PanMobileNumber)
        }

        binding.emailId.setText(PanEmailId)

        if(PanBuilding.isNotEmpty()){
            binding.flathouseno.setText(PanBuilding)
        }else{
            binding.flathouseno.setText(AadharHouse)
        }

        if(PanAddress.isNotEmpty()){
            binding.areasector.setText(PanAddress)
        }
        else{
            binding.areasector.setText(AadharStreet)
        }

        if(PanPinCode.isNotEmpty()){
            binding.pincode.setText(PanPinCode)
        }else{
            binding.pincode.setText(AadharPin)
        }

        if(PanState.isNotEmpty()){
            binding.statename.setText(PanState)
        }else{
            binding.statename.setText(AadharState)
        }


        if(PanAddress.isNotEmpty()){
            binding.statename.setText(PanAddress)
            binding.currentaddress.setText(PanAddress)
        }else{
            binding.statename.setText(AadharLoc)
            binding.currentaddress.setText(AadharLoc)
        }

        binding.cityname.setText(PanCity)
        CustCountry= PanCountry


        if(PanMobileNumber.isNotEmpty()){
            binding.mobileNumber.isEnabled= false
        }
        else{
            binding.mobileNumber.isEnabled= true
        }

        if(PanEmailId.isNotEmpty()){
            binding.emailId.isEnabled= false
        }
        else{
            binding.emailId.isEnabled= true
        }


        if(!AadharImage.isNullOrBlank()){
            val bitmap = base64ToBitmap(AadharImage)
            if (bitmap != null) {
                binding.lockimage.visibility = View.GONE
                binding.userimage.visibility = View.VISIBLE
                binding.userimage.setImageBitmap(bitmap)
                photoUri = bitmapToUri(this@NewCustomerRegistrationPage,bitmap)
                if(photoUri!=null){
                    val imageFile = uriToFile(photoUri!!, this)
                    customerImagePath = imageFile!!.absolutePath
                }
            }
        }
        else {
            customerImagePath = ""
        }


        if(binding.firstName.text.toString().isNotEmpty()){
           binding.firstName.isEnabled = false
        }
        else {
            binding.firstName.isEnabled = true
        }

        if(binding.middleName.text.toString().isNotEmpty()){
            binding.middleName.isEnabled = false
        }
        else {
            binding.middleName.isEnabled = true
        }

        if(binding.lastName.text.toString().isNotEmpty()){
            binding.lastName.isEnabled = false
        }
        else {
            binding.lastName.isEnabled = true
        }

        if(binding.mobileNumber.text.toString().isNotEmpty()){
            binding.mobileNumber.isEnabled = false
        }
        else {
            binding.mobileNumber.isEnabled = true
        }



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
                                ConstantClass.checkActiveStatusAndLogout(this@NewCustomerRegistrationPage, response.status, preference)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {

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
        viewModel.getSessionExpiredReq(request).observe(this){resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("validateresp", Gson().toJson(response))
                                if(response.status==0){
                                    hitApiForRetailerLogout()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {

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
                                val intent = Intent(this@NewCustomerRegistrationPage, ChooseYourRolePage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    ApiStatus.ERROR -> {

                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }


    fun hitApiForCibilReport() {
        if (isInternetAvailable(this@NewCustomerRegistrationPage)) {
            hitApiForSendOTPCibileCheck(CustPrimaryMobileNumber, "Mobile")

        }
        else {
            Toast.makeText(this@NewCustomerRegistrationPage, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
        }

    }


    fun hitApiForSendOTPCibileCheck(mailidormobile: String, type: String) {
        var sendOtpReq = SendOtpReq(
            mobileoremailId = mailidormobile,
            otpType = type
        )

        Log.d("SendOTPREQ", Gson().toJson(sendOtpReq))

        viewModel.sendOTPReq(sendOtpReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("SendRes", response.message)
                                var otp = response.value
                                Log.d("OTP", otp)

                                if (response.statuss.equals("True")) {
                                    var firstName = "Customer"
                                    var customerName = firstName
                                    hitApiForCibilScore(otp)
                                }
                                else {
                                    Toast.makeText(this@NewCustomerRegistrationPage, response.message, Toast.LENGTH_SHORT).show()
                                    ConstantClass.dialog.dismiss()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }


                }
            }
        }

    }


    fun hitApiForCibilScore(otp: String) {
        var pannumber = ConstantClass.PanNumber.trim()
        var dob = ConstantClass.AadharDOB!!.trim()

        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(dob)
        val formattedDate = outputFormat.format(date)
        var emailID = ""

        if(CusteMailID.isNullOrBlank()){
            emailID = "bos.centerpvtltd@gmail.com"
        }else{
            emailID = CusteMailID
        }

        var cibilReq = CibilScoreReq(
            firstName = CustFirstName,
            lastName = CustLastName,
            mobilenumber = CustPrimaryMobileNumber,
            dob = formattedDate,
            mailid = emailID,
            pannumber = pannumber,
            otp = otp,
            consentmessage = "I agree to share my data for verification purposes",
            consentacceptence = "yes",
            registrationID = ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
        )

        Log.d("CibilReq", Gson().toJson(cibilReq))
        viewCibilModel.getCibilReq(cibilReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                ConstantClass.dialog.dismiss()
                                var otp = response.value

                                Log.d("cibilresp", Gson().toJson(response))

                                if(response.status.toLowerCase().equals("false")&& !response.resultCode.equals("101")){
                                    PopOpForCibileScoreRequestToAdmin(response.message.toString(), "Mismatch Details",false)
                                }

                                if (!response.httpResponseCode.isNullOrBlank() && response.httpResponseCode.equals("200")) {
                                    if(response.result!=null){
                                        var data = response.result.resultJson.inProfileResponse
                                        userScore = data.score.bureauScore?.toFloatOrNull() ?: 0f
                                        CibilResponse = Gson().toJson(response)
                                        if(userScore >= 500f){
                                            startActivity(Intent(this@NewCustomerRegistrationPage, MobileSelectionActivity::class.java))
                                        }
                                        else{
                                            GlobalScope.launch(Dispatchers.Main) {
                                                hitApiForCustomerRegister("Your CIBIL score is below 500. Please contact your Admin for approval before applying for a loan.","Low CIBIL Score")
                                            }

                                        }
                                    }
                                    else{
                                        PopOpForCibileScoreRequestToAdmin(response.message.toString(), "Mismatch Details",false)
                                    }
                                }
                                else {
                                    Toast.makeText(this@NewCustomerRegistrationPage, response.message, Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                    }

                    ApiStatus.LOADING -> {

                    }


                }
            }
        }

    }


    fun hitApiForCustomerRegister(cibilScoremsg: String, title: String) {
        ConstantClass.OpenPopUpForVeryfyOTP(this)

        if(CustCountry.isNullOrBlank()){
            CustCountry ="India"
        }

        var retailercode = preference.getStringValue(ConstantClass.RetailerCode, "")
        // Safely create multipart parts (won’t crash if null)
        val custPhotoPart = CustPhotoPath?.let { createMultipartFromUri(this, it, "CustPhoto_File", "CustomerPhoto") }
        val imei1SealPart = ImeiNumber1SealPhotoPath?.let { createMultipartFromUri(this, it, "IMEINumber1_SealPhotoFile", "IMEINumber1Image") }
        val imei2SealPart = ImeiNumber2SealPhotoPath?.let { createMultipartFromUri(this, it, "IMEINumber2_SealPhotoFile", "IMEINumber2Image") }
        val imeiPhotoPart = ImeiNumberPhotoPath?.let { createMultipartFromUri(this, it, "IMEINumberPhotoFile", "IMEINumberImage") }
        val invoicePart = Invoive_Path?.let { createMultipartFromUri(this, it, "InvoiceFile", "InvoiceImage") }
        val aadharFrontPart = AadharFrontImageUri?.let { createMultipartFromUri(this, it, "CustAadharPhoto_File", "AadharFrontImage") }
        val aadharBackPart = AadharBackImageUri?.let { createMultipartFromUri(this, it, "CustAadharBackPhoto_File", "AadharBackImage") }
        val panFrontPart = PanFrontImageUri?.let { createMultipartFromUri(this, it, "CustPanNumberPhoto_File", "PanFrontImage") }

        // Map safely (avoid !!)
        val requestMap = hashMapOf(
            "Mode" to "INSERT".toRequestBody(),
            "FirstName" to (CustFirstName ?: "").toRequestBody(),
            "MiddleName" to (CustMiddleName ?: "").toRequestBody(),
            "LastName" to (CustLastName ?: "").toRequestBody(),
            "PrimaryMobileNumber" to (CustPrimaryMobileNumber ?: "").toRequestBody(),
            "PrimaryOTP" to (CustPrimaryOTP ?: "").toRequestBody(),
            "PrimaryMobileVerified" to (CustPrimaryMobileVerified ?: "").toRequestBody(),
            "AlternateMobileNumber" to (CustAlternateMobileNumber ?: "").toRequestBody(),
            "AlternateMobileOTP" to "".toRequestBody(),
            "PAlternateMobileVerified" to "".toRequestBody(),
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
            "BrandName" to "".toRequestBody(),
            "ModelName" to "".toRequestBody(),
            "ModelVariant" to "".toRequestBody(),
            "Color" to "".toRequestBody(),
            "SellingPrice" to "".toRequestBody(),
            "DownPayment" to "".toRequestBody(),
            "Tenure" to "".toRequestBody(),
            "EMIAmount" to "".toRequestBody(),
            "IMEINumber1" to "".toRequestBody(),
            "IMEINumber2" to "".toRequestBody(),
            "AccountNumber" to "".toRequestBody(),
            "BankIFSCCode" to "".toRequestBody(),
            "BankName" to "".toRequestBody(),
            "AccountType" to "".toRequestBody(),
            "BranchName" to "".toRequestBody(),
            "RefName" to "".toRequestBody(),
            "MemberShipFees" to "".toRequestBody(),
            "PanApiResponse" to (PanResponse ?: "").toRequestBody(),
            "AadhaarApiResponse" to (AadhaarResponse ?: "").toRequestBody(),
            "CibilApiResponse" to (CibilResponse ?: "").toRequestBody(),
            "CustomerCodes" to "".toRequestBody(),
            "RefRelationShip" to "".toRequestBody(),
            "RefmobileNo" to "".toRequestBody(),
            "RefAddress" to "".toRequestBody(),
            "DebitOrCreditCard" to "".toRequestBody(),
            "UPIMandate" to "yes".toRequestBody(),
            "CreatedBy" to "".toRequestBody(),
            "RetailerCode" to  (retailercode ?: "").toRequestBody(),
            "CibilScore" to userScore.toInt().toString().toRequestBody(),
            "IsAggrementVerified" to "".toRequestBody(),
            "IsRetailerAggrementVerified" to "".toRequestBody(),
        )

        // Debug log full request
        Log.e("API_REQ_MAP", Gson().toJson(requestMap))

        Log.e("API_REQ_IMAGES", "CustPhoto=$CustPhotoPath | AadharFront=$AadharFrontImageUri | PanFront=$PanFrontImageUri")

        lifecycleScope.launch {
            try {
                val response = registerCustomerApiHolder.getCustomerCibilApprovedReq(
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
                    invoicePart,
                    aadharFrontPart,
                    aadharBackPart,
                    panFrontPart
                )

                withContext(Dispatchers.Main) {
                    ConstantClass.dialog?.takeIf { it.isShowing }?.dismiss()
                    Log.e("API_RESPONSE_CODE", response.code().toString())

                    if (response.isSuccessful) {
                        val body = response.body()
                        Log.e("API_RESPONSE_SUCCESS", Gson().toJson(body))
                        PopOpForCibileScoreRequestToAdmin(cibilScoremsg, title,true)
                    }
                    else {
                        val errorBody = response.errorBody()?.string()

                        Log.e("API_RESPONSE_ERROR", errorBody ?: "Unknown error")

                        val errorMessage = when (response.code()) {

                            400 -> "Bad request"

                            401 -> "Unauthorized access"

                            403 -> "Access forbidden"

                            404 -> "Data not found"

                            405 -> "Method not allowed"

                            408 -> "Request timeout"

                            409 -> "Conflict occurred"

                            422 -> "Validation failed"

                            429 -> "Too many requests"

                            500 -> "Internal server error"

                            502 -> "Bad gateway"

                            503 -> "Service unavailable"

                            504 -> "Gateway timeout"

                            else -> "Something went wrong"
                        }

                        PopOpForCibileScoreRequestToAdmin(errorMessage, "Error", false)

                    }
                }
            }
            catch (e: SocketTimeoutException) {

                withContext(Dispatchers.Main) {
                    ConstantClass.dialog?.dismiss()
                    PopOpForCibileScoreRequestToAdmin("Connection timeout", "Error", false)
                }

            } catch (e: UnknownHostException) {

                withContext(Dispatchers.Main) {
                    ConstantClass.dialog?.dismiss()
                    PopOpForCibileScoreRequestToAdmin("No internet connection", "Error", false)
                }

            } catch (e: IOException) {

                withContext(Dispatchers.Main) {
                    ConstantClass.dialog?.dismiss()
                    PopOpForCibileScoreRequestToAdmin("Network error", "Error", false)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    ConstantClass.dialog?.dismiss()
                    PopOpForCibileScoreRequestToAdmin(e.message ?: "Something went wrong", "Error", false)

                }
            }

        }

    }


    fun PopOpForCibileScoreRequestToAdmin(cibilScore : String,title:String,check:Boolean){
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
        val titletxt = dialog.findViewById<TextView>(R.id.title)
        val image = dialog.findViewById<ImageView>(R.id.imageview)


        cancel.visibility=View.GONE
        titletxt.visibility = View.VISIBLE
        image.visibility = View.VISIBLE

        done.text = "OK"

        titletxt.text = title
        txt.text = cibilScore


        done.setOnClickListener {
            if(check){
                PanNumber = ""
                PanFrontImageUri = null
                ConstantClass.AadharVerified = ""
                CheckOnlineOrOffline = ""
                AadharTransactionIdNo = ""
                val intent = Intent(this, DashBoard::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                onBackPressed()
            }
            else{
                CustPrimaryOTP = ""
                CustPrimaryMobileVerified = ""
                mobileveryfied = false
                binding.mobileNumber.isEnabled = true
                binding.verifyiconphonenumber.visibility = View.GONE
                binding.verifymobilenumber.visibility = View.VISIBLE
                dialog.dismiss()
            }

        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}