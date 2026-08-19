package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivitySignupPageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadhaarDOB
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass. ReferenceAadharImage
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ENTEREDCUSTOMERDOB
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.IsAadhaarVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.IsGSTVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.IsPanVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanBuilding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanCity
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanCountry
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanDOB
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanEmailId
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanFirstName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanFrontImageUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanLastName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanMiddleName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumberVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanState
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefAadharTransactionIdNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ReferenceAadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Retailer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.base64ToBitmap
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.bitmapToUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.disableCopyPaste
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.iisAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.loginType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.saveImageToCache
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.uriToFile
import com.bosandroidapp.oqmobilefinance.data.gst.GstRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RegistrationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AadharVerificationReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.PanVerificationReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.LoginPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AadharCardReferenceWebViewDIGILockerPage.Companion.checkAdharForRef
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.PaymentInformation.Companion.checkKYC
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SignupPage : BaseActivity() {
    lateinit var binding : ActivitySignupPageBinding
    lateinit var viewModel: AuthenticationViewModel
    lateinit var preference : SharedPreference
    lateinit var dialog: Dialog
    var checkFirstNameValidation : Boolean = false
    var checkLastNameValidation : Boolean = false
    private  val CAMERA_REQUEST_CODE_FRONT = 1001
    private  var profilePhotoUri: Uri? = null
    private  var aadhaarBackhotoUri: Uri? = null
    private  var aadhaarFronthotoUri: Uri? = null
    private  var pancardphotoUri: Uri?=null
    private  var storePhotoUri: Uri? = null
    private  var companyCodePhotoUri: Uri?=null
    private  var chequePhotoUri: Uri?=null
    var customerImagePath: String? = ""
    var aadhaarFrontImagePath: String? = ""
    var aadhaarBackImagePath: String? = ""
    var panImagePath: String? = ""
    var storeImagePath: String? = ""
    var companydocImagePath: String? = ""
    var cancelChequeImagePath: String? = ""
    var PanFirstName: String? = ""
    var PanAadhaarNumber: String? = ""
    var PANDOB : String? = ""
    private var currentCaptureMode: String? = null // "PROFILE", "AADHAAR_FRONT", etc.
    lateinit var panViewModel: PanViewModel


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("capture_mode", currentCaptureMode)
        outState.putParcelable("profile_uri", profilePhotoUri)
        // Save other URIs as well
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentCaptureMode = savedInstanceState.getString("capture_mode")
        profilePhotoUri = savedInstanceState.getParcelable("profile_uri")
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // We use currentCaptureMode because booleans can reset to false if the OS kills the app in the background
            when (currentCaptureMode) {

                "PROFILE" -> {
                    ReferenceAadharImage=""

                    profilePhotoUri?.let {
                        binding.profileimage.visibility = View.VISIBLE
                        binding.lockimage.visibility = View.GONE
                        binding.imagenotuploaded.visibility = View.GONE

                        Glide.with(this)
                            .load(profilePhotoUri)
                            .centerCrop()
                            .into(binding.profileimage)

                        profilePhotoUri?.let { uri ->
                            customerImagePath = compressImageAndGetPath(uri)
                        }

                    }
                }

                "AADHAAR_FRONT" -> {
                    aadhaarFronthotoUri?.let {
                        binding.aadhaarfront.visibility = View.VISIBLE
                        binding.aadhaarfronticon.visibility = View.GONE
                        binding.aadhaarfrontnotuploaded.visibility = View.GONE
                        Glide.with(this)
                            .load(aadhaarFronthotoUri)
                            .centerCrop()
                            .into(binding.aadhaarfront)

                        aadhaarFronthotoUri?.let { uri ->
                            aadhaarFrontImagePath = compressImageAndGetPath(uri)
                        }
                    }
                }

                "AADHAAR_BACK" -> {
                    aadhaarBackhotoUri?.let {
                        binding.aadhaarback.visibility = View.VISIBLE
                        binding.aadhaarbackicon.visibility = View.GONE
                        binding.aadhaarbanknotuploaded.visibility = View.GONE
                        Glide.with(this)
                            .load(aadhaarBackhotoUri)
                            .centerCrop()
                            .into(binding.aadhaarback)

                        aadhaarBackhotoUri?.let { uri ->
                            aadhaarBackImagePath = compressImageAndGetPath(uri)
                        }
                    }
                }

                "PANCARD" -> {
                    pancardphotoUri?.let {
                        binding.pancardPhoto.visibility = View.VISIBLE
                        binding.pancardicon.visibility = View.GONE
                        binding.pancardfrontnotuploaded.visibility = View.GONE
                        Glide.with(this)
                            .load(pancardphotoUri)
                            .centerCrop()
                            .into(binding.pancardPhoto)

                        pancardphotoUri?.let { uri ->
                            panImagePath = compressImageAndGetPath(uri)
                        }
                    }
                }

                "STORE" -> {
                    storePhotoUri?.let {
                        binding.storePhoto.visibility = View.VISIBLE
                        binding.storeicon.visibility = View.GONE
                        binding.storefrontnotuploaded.visibility = View.GONE
                        Glide.with(this)
                            .load(storePhotoUri)
                            .centerCrop()
                            .into(binding.storePhoto)
                        storePhotoUri?.let { uri ->
                            storeImagePath = compressImageAndGetPath(uri)
                        }
                    }
                }

                "COMPANYDOCUMENT" -> {
                    companyCodePhotoUri?.let {
                        binding.companydocumentPhoto.visibility = View.VISIBLE
                        binding.companydocumenticon.visibility = View.GONE
                        binding.companydocumentfrontnotuploaded.visibility = View.GONE
                        Glide.with(this)
                            .load(companyCodePhotoUri)
                            .centerCrop()
                            .into(binding.companydocumentPhoto)
                        companyCodePhotoUri?.let { uri ->
                            companydocImagePath = compressImageAndGetPath(uri)
                        }
                    }
                }

                "CANCELCHEQUE" -> {
                    chequePhotoUri?.let {
                        binding.cancelchequePhoto.visibility = View.VISIBLE
                        binding.cancelchequeicon.visibility = View.GONE
                        binding.cancelchequefrontnotuploaded.visibility = View.GONE
                        Glide.with(this)
                            .load(chequePhotoUri)
                            .centerCrop()
                            .into(binding.cancelchequePhoto)
                        chequePhotoUri?.let { uri ->
                            cancelChequeImagePath = compressImageAndGetPath(uri)
                        }
                    }
                }

            }

        }
        else {
            // Optional: Handle failure or cancellation
            when (currentCaptureMode) {

                "PROFILE" -> {
                    profilePhotoUri=null
                }

                "AADHAAR_FRONT" -> {
                    aadhaarFronthotoUri=null
                }

                "AADHAAR_BACK" -> {
                    aadhaarBackhotoUri = null
                }

                "PANCARD" -> {
                    pancardphotoUri = null
                }

                "STORE" -> {
                    storePhotoUri = null
                }

                "COMPANYDOCUMENT" -> {
                    companyCodePhotoUri = null
                }

                "CANCELCHEQUE" -> {
                    chequePhotoUri= null
                }

            }
            Log.d("BOS_CAMERA", "Capture failed or cancelled for mode: $currentCaptureMode")
        }
    }


    private fun compressImageAndGetPath(uri: Uri): String {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }

        // Define destination for compressed file
        val compressedFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "compressed_${System.currentTimeMillis()}.jpg")
        val out = FileOutputStream(compressedFile)

        // 80 is a good balance between file size and readability for documents
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        out.flush()
        out.close()

        return compressedFile.absolutePath
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        setclickListner()

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        panViewModel = ViewModelProvider(this, com.bosandroidapp.oqmobilefinance.data.viewModelFactory.PanViewModelFactory(PanRepository(RetrofitClient.apiInterfacePAN)))[PanViewModel::class.java]

        preference = SharedPreference(this)
        DisableCopyPaste()

    }



    override fun onResume() {
        super.onResume()

        if (ConstantClass.ReferenceAadhaarName.isNotEmpty()) {
            binding.aadharnumber.text?.clear()
            binding.aadharnumber.setText(ReferenceAadharNumber)
            binding.aadharnumber.isEnabled = ConstantClass.ReferenceAadhaarName.isEmpty()
            binding.verifyaadharnumber.visibility= View.VISIBLE
            binding.verifyAadhaarnumberlayout.visibility = View.GONE

            if(!ReferenceAadharImage.isNullOrBlank()){
                val bitmap = base64ToBitmap( ReferenceAadharImage)
                if (bitmap != null) {
                    binding.profileimage.visibility = View.VISIBLE
                    binding.lockimage.visibility = View.GONE
                    binding.imagenotuploaded.visibility = View.GONE

                    Glide.with(this)
                        .load(bitmap)
                        .centerCrop()
                        .into(binding.profileimage)

                    profilePhotoUri = bitmapToUri(this@SignupPage,bitmap)

                    if(profilePhotoUri!=null){
                        val imageFile = uriToFile(profilePhotoUri!!, this)
                        customerImagePath = imageFile!!.absolutePath
                    }

                }
            }

            Log.d("ReferenceName"," ${ConstantClass.ReferenceAadharNumber}" )
        }
        else{
            binding.aadharnumber.text?.clear()
            binding.aadharnumber.isEnabled = ConstantClass.ReferenceAadhaarName.isEmpty()
            binding.verifyaadharnumber.visibility= View.GONE
        }

    }


    fun setclickListner(){

        binding.cameraicon.setOnClickListener {
            openCameraFor("PROFILE")
       }

        binding.aadhaarFronttxt.setOnClickListener {
            openCameraFor("AADHAAR_FRONT")
        }


        binding.aadhaartxt.setOnClickListener {
            openCameraFor("AADHAAR_BACK")

        }

        binding.pancardtxt.setOnClickListener {
            openCameraFor("PANCARD")

        }

        binding.cancelchequetxt.setOnClickListener {
            openCameraFor("CANCELCHEQUE")

        }

        binding.storetxt.setOnClickListener {
            openCameraFor("STORE")

        }

        binding.companydocumenttxt.setOnClickListener {
            openCameraFor("COMPANYDOCUMENT")
        }

        binding.loginText.setOnClickListener {
            finish()
        }

        binding.panEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val panNumber = s.toString().trim().uppercase()

                val panRegex = Regex("^[A-Z]{5}[0-9]{4}[A-Z]$")

                binding.panEditText.error = null

                when {
                    panNumber.isEmpty() -> {
                        binding.verifyPannumberlayout.visibility = View.GONE
                    }

                    panNumber.length != 10 -> {
                        binding.verifyPannumberlayout.visibility = View.GONE
                    }

                    !panRegex.matches(panNumber) -> {
                        binding.panEditText.error = "Invalid PAN format"
                        binding.verifyPannumberlayout.visibility = View.GONE
                    }

                    else -> {
                        binding.verifyPannumberlayout.visibility = View.VISIBLE
                    }
                }
            }

        })


        binding.aadharnumber.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val aadhaarNumber = s.toString().trim()
                binding.aadharnumber.error = null

                when {
                    aadhaarNumber.isEmpty() -> {
                        binding.verifyAadhaarnumberlayout.visibility = View.GONE
                    }

                    aadhaarNumber.length != 12 -> {
                        binding.verifyAadhaarnumberlayout.visibility = View.GONE
                    }

                    else -> {
                        binding.verifyAadhaarnumberlayout.visibility = View.VISIBLE
                    }
                }
            }
        })


        binding.gstEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val gstNumber = s.toString().trim().uppercase()

                // GST Format: 22AAAAA0000A1Z5
                val gstRegex = Regex("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z][1-9A-Z]Z[0-9A-Z]$")

                binding.gstEditText.error = null

                when {
                    gstNumber.isEmpty() -> {
                        binding.verifyGSTlayout.visibility = View.GONE
                    }

                    gstNumber.length != 15 -> {
                        binding.verifyGSTlayout.visibility = View.GONE
                    }

                    !gstRegex.matches(gstNumber) -> {
                        binding.gstEditText.error = "Invalid GST format"
                        binding.verifyGSTlayout.visibility = View.GONE
                    }

                    else -> {
                        binding.verifyGSTlayout.visibility = View.VISIBLE
                    }
                }
            }
        })


        binding.verifyGSTlayout.setOnClickListener {
            hitApiForGSTVerification()
        }


        binding.createaccount.setOnClickListener {
            var firstName = binding.firstName.text.toString().trim()
            var lastName = binding.lastName.text.toString().trim()
            var mobilenumber = binding.mobileNumber.text.toString().trim()
            var emailId = binding.emailId.text.toString().trim()
            var password = binding.password.text.toString().trim()
            var confirmpass = binding.confirmpassword.text.toString().trim()
            var pannumber = binding.panEditText.text.toString().trim()
            var aadharnumber = binding.aadharnumber.text.toString().trim()
            var address = binding.address.text.toString().trim()
            var storeName = binding.storeName.text.toString().trim()
            var storeaddress = binding.storeaddresstxt.text.toString().trim()
            var gstNumber = binding.gstEditText.text.toString().trim()
            var legalName = binding.storeLegalName.text.toString().trim()
            var tradeName = binding.tradeName.text.toString().trim()
            var constitutionOfBusiness = binding.constitutionOfBusiness.text.toString().trim()

            if(validateForm(firstName,lastName,aadharnumber,pannumber,gstNumber,mobilenumber,emailId,address,password,confirmpass,storeName,legalName,tradeName,constitutionOfBusiness,storeaddress,profilePhotoUri,aadhaarFronthotoUri,aadhaarBackhotoUri,pancardphotoUri,chequePhotoUri,storePhotoUri,companyCodePhotoUri,this@SignupPage)){
              if(isInternetAvailable(this@SignupPage))  {
                  hitApiForRegistration(firstName,lastName,mobilenumber,emailId,password,confirmpass,pannumber,aadharnumber,gstNumber,address,storeName,legalName,tradeName,constitutionOfBusiness,storeaddress)
              }
              else{
                  Toast.makeText(this,"Please check your internet connection!!",Toast.LENGTH_SHORT).show()
              }

            }

        }


        binding.verifyPannumberlayout.setOnClickListener {
            val panNumber =  binding.panEditText.text.toString().trim().uppercase()

            val panRegex = Regex("^[A-Z]{5}[0-9]{4}[A-Z]$")

            if(!panRegex.matches(panNumber)){
                binding.panEditText.error = "Invalid PAN format"
                return@setOnClickListener
            }
            else{
                binding.panEditText.error = null
            }

            if(IsAadhaarVerified.equals("1")){
                hitApiForPanVerification(binding.panEditText.text.toString().trim())
            }
            else{
                Toast.makeText(this@SignupPage,"Please verify the Aadhaar first.",Toast.LENGTH_SHORT).show()
            }

        }


        binding.verifyAadhaarnumberlayout.setOnClickListener {
            var firstName = binding.firstName.text.toString().trim()
            var lastName = binding.lastName.text.toString().trim()
            var mobilenumber = binding.mobileNumber.text.toString().trim()
            var  aadhaarNumber = binding.aadharnumber.text.toString().trim()
            if(aadhaarNumber.length!=12){
                binding.aadharnumber.error = "Enter a valid 12-digit Aadhaar number"
                return@setOnClickListener
            }
            else{
                binding.aadharnumber.error = null
            }

            if (validateadhaarForm(firstName, lastName, mobilenumber,this@SignupPage)) {
                if (isInternetAvailable(this@SignupPage)) {
                    hitApiForAadharVerification()
                }
                else {
                    Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }



    fun hitApiForGSTVerification() {
        var gstInReuest = GstRequest(
            gSTNumber = binding.gstEditText.text.toString().trim(),
            registrationID = ConstantClass.PAN_VERIFICATION_REGISTRATION_ID,
        )
        Log.d("gstInReuest", Gson().toJson(gstInReuest))

        panViewModel.getGstNumberVerify(gstInReuest).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("gstInResponse", Gson().toJson(response))
                                 if(response!!.resultCode==101&& response.result!=null)
                                 {
                                     IsGSTVerified= "1"
                                     var getData= response.result!!.taxpayerDetails
                                     var lgnm = getData!!.lgnm
                                     var trdnm = getData!!.tradeNam
                                     var adrs = getData!!.pradr!!.adr
                                     binding.storeLegalName.setText(lgnm)
                                     binding.tradeName.setText(trdnm)
                                     binding.storeaddresstxt.setText(adrs)
                                     binding.gstEditText.isEnabled=false
                                     binding.verifyGSTlayout.visibility=View.GONE
                                     binding.verifygst.visibility=View.VISIBLE

                                     binding.tradeName.isEnabled = trdnm.isNullOrEmpty()
                                     binding.storeLegalName.isEnabled = lgnm.isNullOrEmpty()
                                     binding.storeaddresstxt.isEnabled = adrs.isNullOrEmpty()

                                 }
                                 else {
                                     IsGSTVerified= ""
                                     binding.tradeName.isEnabled = true
                                     binding.storeLegalName.isEnabled = true
                                     binding.storeaddresstxt.isEnabled = true
                                     binding.gstEditText.isEnabled=true
                                     binding.verifyGSTlayout.visibility=View.VISIBLE
                                     binding.verifygst.visibility=View.GONE
                                     Toast.makeText(this@SignupPage, "The GST number you entered does not exist.!!", Toast.LENGTH_SHORT).show()
                                 }

                            }

                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        IsGSTVerified= ""
                        binding.tradeName.isEnabled = true
                        binding.storeLegalName.isEnabled = true
                        binding.storeaddresstxt.isEnabled = true
                        binding.gstEditText.isEnabled=true
                        binding.verifyGSTlayout.visibility=View.VISIBLE
                        binding.verifygst.visibility=View.GONE
                        Toast.makeText(this@SignupPage, resources.message ?: "GST verification failed", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }

            }
        }

    }


    fun hitApiForPanVerification(pannumber: String) {
        var panverificationreq = PanVerificationReq(
            panNumber = pannumber,
            registrationId = ConstantClass.PAN_VERIFICATION_REGISTRATION_ID,
        )
        Log.d("PanVerificationreq", Gson().toJson(panverificationreq))

        panViewModel.getPanVerificationReq(panverificationreq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("PanVerificationResp", Gson().toJson(response))
                                if (response!!.httpResponseCode == 203) {
                                    IsPanVerified =""
                                    binding.panEditText.isEnabled= true
                                    binding.verifyPannumber.visibility=View.GONE
                                    binding.verifyPannumberlayout.visibility=View.VISIBLE
                                    Toast.makeText(this@SignupPage, "Please enter valid pan number!!", Toast.LENGTH_SHORT).show()
                                }
                                if (response!!.httpResponseCode == 205) {
                                    IsPanVerified =""
                                    binding.panEditText.isEnabled= true
                                    binding.verifyPannumber.visibility=View.GONE
                                    binding.verifyPannumberlayout.visibility=View.VISIBLE
                                    Toast.makeText(this@SignupPage, "Please enter valid pan number!!", Toast.LENGTH_SHORT).show()
                                }

                                if (response!!.httpResponseCode == 0) {
                                    IsPanVerified =""
                                    binding.panEditText.isEnabled= true
                                    binding.verifyPannumber.visibility=View.GONE
                                    binding.verifyPannumberlayout.visibility=View.VISIBLE
                                    Toast.makeText(this@SignupPage, response.message, Toast.LENGTH_SHORT).show()
                                }

                                if (response!!.httpResponseCode == 200) {
                                    var DOB = response.result!!.dob!! // 10/07/1997 dd/mm/yyyy
                                    var apiSdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    var apiDate: Date = apiSdf.parse(DOB)!!
                                    PANDOB = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(apiDate)

                                    if (PANDOB != ConstantClass.AadhaarDOB) {
                                        Toast.makeText(this@SignupPage,"DOB does not match",Toast.LENGTH_SHORT).show()
                                        IsPanVerified = ""
                                        binding.panEditText.isEnabled= true
                                        binding.verifyPannumber.visibility=View.GONE
                                        binding.verifyPannumberlayout.visibility=View.VISIBLE
                                    }
                                    else{
                                        binding.panEditText.setText(response.result!!.pan!!)
                                        IsPanVerified = "1"
                                        PanFirstName = response.result!!.fullname!!
                                        PanAadhaarNumber = response.result!!.aadhaarNumber
                                        binding.firstName.setText(response.result!!.fullname!!)
                                        binding.panEditText.isEnabled= false
                                        Log.d("PANNUMBER",response.result!!.pan!!)
                                        binding.verifyPannumber.visibility=View.VISIBLE
                                        binding.verifyPannumberlayout.visibility=View.GONE
                                    }

                                }
                                else {
                                    IsPanVerified = ""
                                    binding.panEditText.isEnabled= true
                                    binding.verifyPannumber.visibility=View.GONE
                                    binding.verifyPannumberlayout.visibility=View.VISIBLE
                                    Toast.makeText(this@SignupPage, "Please enter valid pan number!!", Toast.LENGTH_SHORT).show()
                                }

                            }

                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@SignupPage, resources.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                         ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }

            }

        }

    }


    fun hitApiForAadharVerification() {
        val firstName = binding.firstName.text.toString().trim().orEmpty()
        val lastName = binding.lastName.text.toString().trim().orEmpty()
        val emailId = "bos.centerpvtltd@gmail.com"
        val mob = binding.mobileNumber.text.toString().trim().orEmpty()

        var aadharverificationreq = AadharVerificationReq(
            firstName = firstName,
            lastName = lastName,
            mobileNumber = mob,
            emailId = emailId,
            registrationId = ConstantClass.PAN_VERIFICATION_REGISTRATION_ID,
        )

        Log.d("AadharVerificationreq", Gson().toJson(aadharverificationreq))

        panViewModel.getAadharVerificationReq(aadharverificationreq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("AadharVerificationResp", Gson().toJson(response))

                                if (response!!.code == null) {
                                    Toast.makeText(this@SignupPage, response.message, Toast.LENGTH_SHORT).show()
                                }
                                if (response!!.code.equals("200")) {
                                    AadharCardReferenceWebViewDIGILockerPage.digilockerLink = response!!.model.kycUrl
                                    RefAadharTransactionIdNo = response.model.transactionId
                                    preference.setStringValue(ConstantClass.RetailerCode, "")
                                    Log.d("Referencedigilockeurl",  AadharCardReferenceWebViewDIGILockerPage.digilockerLink)
                                    startActivity(Intent(this@SignupPage, AadharCardReferenceWebViewDIGILockerPage::class.java))
                                }
                                else {
                                    Toast.makeText(this@SignupPage, response.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@SignupPage, resources.message ?: "Aadhar verification failed", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }

    }


    private fun openCameraFor(mode: String) {
        currentCaptureMode = mode
        checkCameraPermissionAndOpenCamera()
    }


    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            clickCameraForUploadDocument(currentCaptureMode!!)
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE_FRONT)
        }
    }


    fun clickCameraForUploadDocument(mode: String) {
        val photoFile = createImageFile()
        val providerAuthority = "${packageName}.fileprovider"

        // Initialize the specific URI based on the mode passed from the click listener
        when (mode) {
            "PROFILE" -> {
                profilePhotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(profilePhotoUri!!)
            }
            "AADHAAR_FRONT" -> {
                aadhaarFronthotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(aadhaarFronthotoUri!!)
            }
            "AADHAAR_BACK" -> {
                aadhaarBackhotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(aadhaarBackhotoUri!!)
            }
            "PANCARD" -> {
                pancardphotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(pancardphotoUri!!)
            }
            "CANCELCHEQUE" -> {
                chequePhotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(chequePhotoUri!!)
            }
            "STORE" -> {
                storePhotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(storePhotoUri!!)
            }
            "COMPANYDOCUMENT" -> {
                companyCodePhotoUri = FileProvider.getUriForFile(this, providerAuthority, photoFile)
                cameraLauncher.launch(companyCodePhotoUri!!)
            }
        }
    }


    private fun createImageFile(): File {
        val fileName = "IMG_${System.currentTimeMillis()}"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }


    fun DisableCopyPaste(){

        binding.firstName.disableCopyPaste()
        binding.lastName.disableCopyPaste()

        binding.firstName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.firstName.error = "Emoji not allowed"
                        checkFirstNameValidation= false
                    }else{
                        checkFirstNameValidation= true
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.lastName.addTextChangedListener(object :TextWatcher{

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.lastName.error = "Emoji not allowed"
                        checkLastNameValidation= false
                    }
                    else{
                        checkLastNameValidation= true
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

       /* binding.aadharnumber.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            val newText = StringBuilder(dest).apply {
                replace(dstart, dend, source?.substring(start, end) ?: "")
            }.toString()

            // Block if non-digit characters are present
            if (source != null && source.any { !it.isDigit() }) {
                return@InputFilter ""
            }

            // If user pastes while field is empty, only allow exactly 12 digits
            if (dest.isEmpty() && source != null && source.length > 1) {
                return@InputFilter if (source.length == 12 && source.all { it.isDigit() }) source else ""
            }

            // Prevent length > 12
            if (newText.length > 12) {
                return@InputFilter ""
            }

            null // Accept input
        })*/

        val panPattern = Regex("^[A-Z]{5}[0-9]{4}[A-Z]{1}$")

        binding.panEditText.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            val newText = StringBuilder(dest).apply {
                replace(dstart, dend, source?.substring(start, end) ?: "")
            }.toString().uppercase() // Always uppercase

            // Block invalid characters
            if (source != null && source.any { !(it.isDigit() || it.isLetter()) }) {
                return@InputFilter ""
            }

            // Force uppercase input
            if (source != null && source.any { it.isLowerCase() }) {
                return@InputFilter source.toString().uppercase()
            }

            // Limit to 10 chars
            if (newText.length > 10) {
                return@InputFilter ""
            }

            // Paste handling
            if (source != null && source.length > 1) {
                return@InputFilter if (panPattern.matches(newText)) newText else ""
            }

            null // Accept valid input
        })

        binding.address.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            val allowedPattern = Regex("[A-Za-z0-9 ,./#\\-\\n]+") // \n = allow line breaks
            if (source.isNullOrEmpty()) {
                null // allow deletes
            } else if (allowedPattern.matches(source)) {
                null // allow valid input
            } else {
                "" // block invalid chars (including from paste)
            }
        })


    }


    fun hitApiForRegistration(firstName:String,lastName:String,mobNumber:String,emailId:String,password:String,confrmPassword:String,panNumber:String,aadharNumber:String,gstNumber:String,address:String,storename:String,legalName:String,tradeName:String,constitutionOfBusiness:String,storeaddress:String){
        binding.createaccount.isEnabled=false
        val profilePhoto = saveImageToCache(this, profilePhotoUri!!, "profile.jpg")
        val aadhaarfront = saveImageToCache(this, aadhaarFronthotoUri!!, "aadhaarfront.jpg")
        val aadhaarback = saveImageToCache(this,aadhaarBackhotoUri!!, "aadhaarback.jpg")
        val pancard =    saveImageToCache(this, pancardphotoUri!!, "pancard.jpg")
        val storePhoto = saveImageToCache(this, storePhotoUri!!, "storefront.jpg")
        val companydoc = saveImageToCache(this, companyCodePhotoUri!!, "companydoc.jpg")
        val cancelcheque = saveImageToCache(this, chequePhotoUri!!, "cancelcheque.jpg")


        var registationRequest = RegistrationReq(
            firstName = firstName,
            lastName = lastName,
            mobileNumber = mobNumber,
            emailId = emailId,
            password = password,
            confrmpassword = confrmPassword,
            address = address,
            aadharnumber = aadharNumber,
            panNumber = panNumber,
            storeName = storename,
            storeAddress = storeaddress,
            gstNumber = gstNumber,
            legalName = legalName,
            tradeName = tradeName,
            GSTStatus = "Active",
            constitutionOfBusiness = constitutionOfBusiness,
            verificationStatus = "",
            verificationMessage = "",
            isGSTVerified = IsGSTVerified,
            isPanVerified = IsPanVerified,
            isAadhaarVerified = IsAadhaarVerified,
            profilePhoto,
            aadhaarfront,
            aadhaarback,
            pancard,
            cancelcheque,
            storePhoto,
            companydoc
        )

        Log.d("RegisReq", Gson().toJson(registationRequest))

        viewModel.getRegistration(registationRequest).observe(this){
            resources->resources.let {
                when(it.apiStatus){
                    ApiStatus.SUCCESS ->{
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("AirPortListResponse", Gson().toJson(response))
                                if(response.statuss!!.toLowerCase().equals("true",ignoreCase = true)){
                                    preference.setStringValue(ConstantClass.CustomerCode, response.customerCode.toString())
                                    preference.setStringValue(ConstantClass.CustomerMobileNumber, response.mobileNumber.toString())
                                    preference.setStringValue(ConstantClass.CustomerEmailID, response.emailID.toString())
                                    Toast.makeText(this, response?.message, Toast.LENGTH_LONG).show()
                                    loginType = Retailer
                                    startActivity(Intent(this, LoginPage::class.java))
                                    finish()
                                    emptyAboveField()
                                }
                                else{
                                    binding.createaccount.isEnabled=true
                                    emptyAboveField()
                                    Toast.makeText(this, response?.message, Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                    }
                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        binding.createaccount.isEnabled=true
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }

        }


    }


    fun validateForm(firstname:String, lastname:String, aadharNumber: String, panNumber: String, gstNumber : String, mobile: String, email: String, address: String, password: String, confirmPassword: String,
                     storeName: String, legalName:String, tradeName: String, constitutionOfBusiness: String, storeAddress: String, profilephotouri: Uri?, aadhaarfronturi: Uri?, aadhaarbackuri: Uri?, panuri: Uri?, cancelchequeuri: Uri?, storephotouri: Uri?, companydocuri: Uri?, context: Context): Boolean {

        if (firstname.isNullOrBlank() ) {
            binding.firstName.error= "Enter first name"
            scrollToView(binding.detaillayout,  binding.firstName)
            Toast.makeText(context, "Enter first name", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.firstName.error = null
        }

        if(!checkFirstNameValidation){
            Toast.makeText(context, "Enter Valid first name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (lastname.isNullOrBlank() ) {
            binding.lastName.error= "Enter last name"
            scrollToView(binding.detaillayout,  binding.lastName)
            Toast.makeText(context, "Enter last name", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.lastName.error = null
        }

        if(!checkLastNameValidation){
            Toast.makeText(context, "Please enter a valid last name.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!mobile.matches(Regex("^[6-9]\\d{9}$"))) {
            binding.mobileNumber.error = "Please enter a valid 10-digit mobile number"
            scrollToView(binding.detaillayout, binding.mobileNumber)
            return false
        } else {
            binding.mobileNumber.error = null
        }


        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailId.error= "Enter a valid email address"
            scrollToView(binding.detaillayout,  binding.emailId)
            Toast.makeText(context, "Enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        else{

            binding.emailId.error = null
        }


        if (address.isNullOrBlank() ) {
            binding.address.error= "Please enter address"
            scrollToView(binding.detaillayout,  binding.address)
            Toast.makeText(context, "Please enter address", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.address.error = null
        }

        // .............................................................for testing purpose............................................

        val aadhaarRegex = Regex("^[2-9][0-9]{11}$")
        val maskedAadhaarRegex = Regex("^[xX]{8}[0-9]{4}$")


        // Aadhaar validation
     /*   val isValid = aadharNumber.matches(Regex("\\d{12}")) || aadharNumber.matches(maskedAadhaarRegex)

        if (aadharNumber.isBlank() || aadharNumber.length != 12 ||  !isValid) {
            binding.aadharnumber.error= "Enter a valid 12-digit Aadhaar number"
            scrollToView(binding.detaillayout,  binding.aadharnumber)
            Toast.makeText(context, "Enter a valid 12-digit Aadhaar number", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.aadharnumber.error = null
        }*/


      /*  if(IsAadhaarVerified.isNullOrBlank()){
            binding.aadharnumber.error= "Please verify aadhaar number first"
            scrollToView(binding.detaillayout,  binding.aadharnumber)
            Toast.makeText(context, "Please verify aadhaar number first", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.aadharnumber.error = null
        }*/


      /*  // PAN validation (Regex: 5 letters, 4 digits, 1 letter)
        val panRegex = Regex("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        if (panNumber.isBlank() || !panRegex.matches(panNumber.uppercase())) {
            binding.panEditText.error= "Enter a valid PAN number (e.g., ABCDE1234F)"
            scrollToView(binding.detaillayout,  binding.panEditText)
            Toast.makeText(context, "Enter a valid PAN number (e.g., ABCDE1234F)", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.panEditText.error = null
        }


        if(IsPanVerified.isNullOrBlank()){
            binding.panEditText.error= "Please verify pan number first"
            scrollToView(binding.detaillayout,  binding.panEditText)
            Toast.makeText(context, "Please verify pan number first", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.panEditText.error = null
        }*/

      /*  if(!PanFirstName!!.toLowerCase().contains(ConstantClass.ReferenceAadhaarName.toLowerCase()) && !isSameMaskedAadhaar(ReferenceAadharNumber, PanAadhaarNumber!!) ){
            binding.firstName.error= "Data mismatch. Please enter the valid data as per your PAN and Aadhaar records."
            scrollToView(binding.detaillayout,  binding.firstName)
            Toast.makeText(context, "Data mismatch. Please enter the name as per your PAN and Aadhaar records.", Toast.LENGTH_SHORT).show()
            return false
        }
        else {
            binding.firstName.error = null
        }*/

        ///................................as per Guru teting flow....................................................................................


        /*if (gstNumber.isBlank() || !ConstantClass.isValidGST(gstNumber)) {
            binding.gstEditText.error= "Enter a valid GST number (e.g., 07ABCDE1234F1Z5)"
            scrollToView(binding.detaillayout,  binding.gstEditText)
            Toast.makeText(context, "Enter a valid GST number (e.g., 07ABCDE1234F1Z5)", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.gstEditText.error = null
        }

        if(IsGSTVerified.isNullOrBlank()){
            binding.gstEditText.error= "Please verify gst number first."
            scrollToView(binding.detaillayout,  binding.panEditText)
            Toast.makeText(context, "Please verify gst number first.", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.gstEditText.error = null
        }*/


        if (storeName.isNullOrBlank() ) {
            binding.storeName.error= "Please enter store name"
            scrollToView(binding.detaillayout,  binding.storeName)
            Toast.makeText(context, "Please enter store name", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.storeName.error = null
        }


        if (legalName.isNullOrBlank() ) {
            binding.storeLegalName.error= "Please enter legal store name"
            scrollToView(binding.detaillayout,  binding.storeLegalName)
            Toast.makeText(context, "Please enter legal store name", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.storeLegalName.error = null
        }


        if (tradeName.isNullOrBlank() ) {
            binding.tradeName.error= "Please enter trade  name"
            scrollToView(binding.detaillayout,  binding.tradeName)
            Toast.makeText(context, "Please enter trade  name", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.tradeName.error = null
        }



        if (constitutionOfBusiness.isNullOrBlank() ) {
            binding.constitutionOfBusiness.error= "Please enter nature of business"
            scrollToView(binding.detaillayout,  binding.constitutionOfBusiness)
            Toast.makeText(context, "Please enter nature of business", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.constitutionOfBusiness.error = null
        }


        if (storeAddress.isNullOrBlank() ) {
            binding.storeaddresstxt.error= "Please enter store address"
            scrollToView(binding.detaillayout,  binding.storeaddresstxt)
            Toast.makeText(context, "Please enter store address", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.storeaddresstxt.error = null
        }


        if (password.length < 6) {
            binding.password.error= "Password must be at least 6 characters"
            scrollToView(binding.detaillayout,  binding.password)
            Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        } else{
            binding.password.error = null
        }


        if (confirmPassword != password) {
            binding.confirmpassword.error= "Passwords do not match"
            scrollToView(binding.detaillayout,  binding.confirmpassword)
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }else{
            binding.confirmpassword.error = null
        }


        if(profilephotouri==null){
            Toast.makeText(context, "Upload profile photo", Toast.LENGTH_SHORT).show()
            binding.imagenotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.imagenotuploaded)
            return false
        }
        else{
            binding.imagenotuploaded.visibility = View.GONE
        }


        if(aadhaarfronturi==null){
            Toast.makeText(context, "Upload aadhaar front photo", Toast.LENGTH_SHORT).show()
            binding.aadhaarfrontnotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.aadhaarfrontlayout)
            return false
        }else{
            binding.aadhaarfrontnotuploaded.visibility = View.GONE
        }

        if(aadhaarbackuri==null){
            Toast.makeText(context, "Upload aadhaar back photo", Toast.LENGTH_SHORT).show()
            binding.aadhaarbanknotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.aadhaarbacklayout)
            return false
        }else{
            binding.aadhaarbanknotuploaded.visibility = View.GONE
        }

        if(panuri==null){
            Toast.makeText(context, "Upload pan card photo", Toast.LENGTH_SHORT).show()
            binding.pancardfrontnotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.pancardphotolayout)

            return false
        }else{
            binding.pancardfrontnotuploaded.visibility = View.GONE
        }

        if(cancelchequeuri==null){
            Toast.makeText(context, "Upload cancel cheque photo", Toast.LENGTH_SHORT).show()
            binding.cancelchequefrontnotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.cancelchequelayout)

            return false
        }else{
            binding.cancelchequefrontnotuploaded.visibility = View.GONE
        }

        if(storephotouri==null){
            Toast.makeText(context, "Upload store front photo ", Toast.LENGTH_SHORT).show()
            binding.storefrontnotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.storelayout)

            return false
        }else{
            binding.storefrontnotuploaded.visibility = View.GONE
        }


        if(companydocuri==null){
            Toast.makeText(context, "Upload GST document photo", Toast.LENGTH_SHORT).show()
            binding.companydocumentfrontnotuploaded.visibility = View.VISIBLE
            scrollToView(binding.detaillayout, binding.companydocumentlayout)
            return false
        }else{
            binding.companydocumentfrontnotuploaded.visibility = View.GONE
        }

        return true // All fields are valid
    }

    fun validateadhaarForm(firstname:String, lastname:String,  mobile: String, context: Context): Boolean {

        if (firstname.isNullOrBlank() ) {
            binding.firstName.error= "Enter first name"
            scrollToView(binding.detaillayout,  binding.firstName)
            Toast.makeText(context, "Enter first name", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.firstName.error = null
        }

        if(!checkFirstNameValidation){
            Toast.makeText(context, "Enter Valid first name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (lastname.isNullOrBlank() ) {
            binding.lastName.error= "Enter last name"
            scrollToView(binding.detaillayout,  binding.lastName)
            Toast.makeText(context, "Enter last name", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            binding.lastName.error = null
        }

        if(!checkLastNameValidation){
            Toast.makeText(context, "Please enter a valid last name.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!mobile.matches(Regex("^[6-9]\\d{9}$"))) {
            binding.mobileNumber.error = "Please enter a valid 10-digit mobile number"
            scrollToView(binding.detaillayout, binding.mobileNumber)
            return false
        } else {
            binding.mobileNumber.error = null
        }

        return true // All fields are valid
    }




    fun isSameMaskedAadhaar(customerMasked: String, referenceMasked: String): Boolean {
        val customerLast4 = customerMasked.takeLast(4)
        val referenceLast4 = referenceMasked.takeLast(4)
        val isAadhaarMatched = customerLast4 == referenceLast4
        Log.d("validateAadhaarnumber" , "${isAadhaarMatched}")
        return isAadhaarMatched
    }



    fun emptyAboveField(){
        clearReferenceAadhaarData()
        binding.profileimage.visibility = View.GONE
        binding.lockimage.visibility = View.VISIBLE
        binding.firstName.text!!.clear()
        binding.lastName.text.clear()
        binding.mobileNumber.text!!.clear()
        binding.emailId.text.clear()
        binding.password.text.clear()
        binding.confirmpassword.text.clear()
        binding.address.text.clear()
        binding.panEditText.text.clear()
        binding.aadharnumber.text!!.clear()
        binding.gstEditText.text!!.clear()
        binding.storeLegalName.text!!.clear()
        binding.tradeName.text!!.clear()
        binding.constitutionOfBusiness.text!!.clear()
        binding.cameraicon.isEnabled=ConstantClass.ReferenceAadhaarName.isEmpty()
        IsGSTVerified=""
        IsPanVerified=""
        IsAadhaarVerified=""
    }


    private fun clearReferenceAadhaarData() {
        checkKYC = false
        checkAdharForRef = "no"
        ReferenceAadharImage = ""
        AadhaarDOB = ""
        ConstantClass.ReferenceAadharVerified = "no"
        profilePhotoUri=null
        ReferenceAadharNumber = ""
        ConstantClass.ReferenceAadhaarName = ""
        ConstantClass.ReferenceAadharHouse = ""
        ConstantClass.ReferenceAadharStreet = ""
        ConstantClass.ReferenceAadharLoc = ""
        ConstantClass.ReferenceAadhardist = ""
        ConstantClass.ReferenceAadharPin = ""
        ConstantClass.ReferenceAadharState = ""
        ConstantClass.ReferenceAadharCountry = ""
        RefAadharTransactionIdNo=""
        binding.panEditText.isEnabled= true
        binding.verifyaadharnumber.visibility=View.GONE
        binding.verifyPannumber.visibility=View.GONE
        binding.verifygst.visibility=View.GONE
        binding.aadharnumber.isEnabled = ConstantClass.ReferenceAadhaarName.isEmpty()
        profilePhotoUri=null
        aadhaarFronthotoUri=null
        aadhaarBackhotoUri = null
        pancardphotoUri = null
        storePhotoUri = null
        companyCodePhotoUri = null
        chequePhotoUri= null

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

    fun scrollToView(scrollView: NestedScrollView, targetView: View) {
        scrollView.post {
            /*scrollView.smoothScrollTo(0, targetView.top - offset)*/

            val location = IntArray(2)
            targetView.getLocationOnScreen(location)

            val scrollLocation = IntArray(2)
            scrollView.getLocationOnScreen(scrollLocation)

            val y = location[1] - scrollLocation[1]
            scrollView.smoothScrollBy(0, y)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        lifecycleScope.launch {
            delay(300)
            emptyAboveField()
        }

    }

}