package com.bosandroidapp.oqmobilefinance.ui.view.activity.customer

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityMakePaymentPageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankIFSCCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BrandName
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Customer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.EmiAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber1
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber2
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoginType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefRelationShip
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefmobileNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.createMultipartFromUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToDDMMYYYY
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.getCurrentUtcTimestamp
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.saveImageToCache
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.uriToFile
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerDataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.ApiInterface
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.view.model.MakePaymentDataModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.bosandroidapp.oqmobilefinance.utils.MonthsAndPayables
import com.bosandroidapp.oqmobilefinance.utils.getCurrentLastPaidDueDate
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MakePaymentPage : BaseActivity() {
    lateinit var binding : ActivityMakePaymentPageBinding
    private val CAMERA_REQUEST_CODE_FRONT = 1001
    private var photoUri: Uri? = null
    lateinit var preference : SharedPreference
    lateinit var viewModel: AuthenticationViewModel
    lateinit var receiptUri : Uri
    lateinit var api : ApiInterface
    var imagepath: String? = ""
    var logintype: String? = ""
    var makepaymentList : MutableList<MakePaymentDataModel> = mutableListOf()
    var emiAmount :Double = 0.0
    var listOfDueWithGraceDate : ArrayList<MonthsAndPayables> = arrayListOf()
    var checktxnNumber : Boolean = false
    

/*    companion object{
        var makepaymentemicount : String = ""
        var makepaymentamount : String = ""
        var makepaymentloanid : String = ""
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // Handle the photoUri, e.g., show image in ImageView
                binding.receiptPhoto.visibility = View.VISIBLE
                binding.cameraicon.visibility = View.GONE
                binding.receiptPhoto.setImageURI(photoUri)
                binding.clicktosealphoto1.text="Re-Send"
                receiptUri= photoUri!!
                imagepath = saveImageToCache(this,receiptUri,"ReceiptPhoto")!!.absolutePath

            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakePaymentPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference = SharedPreference(this)
        api =  RetrofitClient.apiInterface

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        logintype = preference.getStringValue(ConstantClass.LoginType, "").orEmpty()

        if(logintype.equals(Customer)){
            if(isInternetAvailable(this@MakePaymentPage)) {
                HitApiForEmiList()
            }
        }
        else{
            val loanCodelist: Array<String> = arrayOf(makepaymentloanid)
            val companyAdapter = ArrayAdapter(this, R.layout.mobilenamelayout, loanCodelist )
            companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.loanid.adapter = companyAdapter

            val emiOptions: Array<String> = arrayOf(makepaymentemicount)
            val noOfEmiAdapter = ArrayAdapter(this, R.layout.mobilenamelayout, emiOptions )
            noOfEmiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.noofemi.adapter = noOfEmiAdapter

            binding.paidamount.text =  makepaymentamount


        }



        setOnClickListner()
        setView()

    }


    fun setView(){

        binding.txnNumber.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (containsEmoji(it.toString())) {
                        binding.txnNumber.error = "Emoji not allowed"
                        checktxnNumber = true
                    }else{
                        checktxnNumber = false
                    }
                }
            }

        })

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


    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataForSpinner(loanCodelist: Array<String>){
        val companyAdapter = ArrayAdapter(this, R.layout.mobilenamelayout, loanCodelist )
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.loanid.adapter = companyAdapter

        binding.loanid.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val loancode = parent.getItemAtPosition(position).toString()

                val dueemi = makepaymentList.firstOrNull { it.loanCode == loancode }?.due
                val emiamount = makepaymentList.firstOrNull { it.loanCode == loancode }?.emiAmount
                val startDate = makepaymentList.firstOrNull { it.loanCode == loancode }?.startDate
                val paidemi = makepaymentList.firstOrNull { it.loanCode == loancode }?.paid
                val Allgraceperiod = makepaymentList.firstOrNull { it.loanCode == loancode }?.allgracePeriod
                val Customergraceperiod = makepaymentList.firstOrNull { it.loanCode == loancode }?.customergracePeriod
                val latefine = makepaymentList.firstOrNull { it.loanCode == loancode }?.latefine


                lifecycleScope.launch {

                    listOfDueWithGraceDate = formatDateToDDMMYYYY(startDate!!).getCurrentLastPaidDueDate(this@MakePaymentPage,paidemi!!.toLong(),dueemi!!.toLong(),Allgraceperiod!!.toInt(),Customergraceperiod!!.toInt(),"")

                    Log.d("MakePaymentList", "Data". plus(listOfDueWithGraceDate))

                    val emiOptions = ConstantClass.generateEMIOptions(dueemi!!.toInt())
                    emiAmount = emiamount!!


                    setDataForEmi(emiOptions , emiAmount, latefine!!)

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }

        }


    }


    fun setDataForEmi(emiOptions:Array<String> , emiAmount:Double ,latefine:String ){
        val noOfEmiAdapter = ArrayAdapter(this, R.layout.mobilenamelayout, emiOptions )
        noOfEmiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.noofemi.adapter = noOfEmiAdapter

        binding.noofemi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val selectedItem = parent.getItemAtPosition(position).toString().toInt()

                var totalEmiAmount = 0.0   // use Double for calculation
                Log.d("ListDueGrace",":".plus(listOfDueWithGraceDate))
                if(listOfDueWithGraceDate.isNotEmpty()){

                    for (j in 1..selectedItem) {
                        val emiIndex = j - 1
                        val emiAmountWithFine = if (listOfDueWithGraceDate[emiIndex].lateFeesApplied) {
                            emiAmount.toDouble() + latefine.toDouble()
                        } else {
                            emiAmount.toDouble()
                        }
                        Log.d("totalamount", emiAmountWithFine.toString())
                        totalEmiAmount += emiAmountWithFine
                    }

                    binding.paidamount.text = "${totalEmiAmount}"

                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }

        }

    }


    fun setOnClickListner(){
        binding.back.setOnClickListener {
            if(logintype.equals(Customer)){
                finish()
            }
            else {

            }
        }

        binding.clicktosealphoto1.setOnClickListener {
            checkCameraPermissionAndOpenCamera()
        }

        binding.submitlayout.setOnClickListener {
            if(isInternetAvailable(this@MakePaymentPage)) {
                val paidAmountText = binding.paidamount.text.toString()

                // Validation for empty amount
                if (paidAmountText.isEmpty()) {
                    Toast.makeText(this,"Please enter paid amount",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


                if (imagepath!!.isBlank()) {
                    Toast.makeText(this, "Please upload receipt photo", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if(binding.txnNumber.text.toString().equals("")){
                    Toast.makeText(this, "Please enter transaction number", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(checktxnNumber){
                        Toast.makeText(this, "Please enter valid transaction number", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    if(binding.remarks.text.toString().trim().equals("")){
                        Toast.makeText(this, "Please enter remarks", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    HitApiForUploadReceipt(binding.txnNumber.text.toString())
                }

            }
        }

    }


    private fun checkCameraPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            clickCameraForUploadDocument()
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_REQUEST_CODE_FRONT)
        }
    }


    fun clickCameraForUploadDocument() {
        val photoFile = createImageFile()
        photoUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", photoFile)
        cameraLauncher.launch(photoUri!!)
    }


    private fun createImageFile(): File {
        val fileName = "IMG_${System.currentTimeMillis()}"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun HitApiForEmiList(){

        var loanemireq = GetCustomerLoanDetailsReq(
            loancode = "",
            customercode = preference.getStringValue(ConstantClass.CustomerCode,"")
        )

        Log.d("customerloanEmireq", Gson().toJson(loanemireq))

        viewModel.getCustomerLoanEmiDetailsReq(loanemireq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let {
                                    response ->
                                Log.d("customerLoanemiresp", response.toString())
                                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                    ConstantClass.dialog.dismiss()
                                    var LoanEmiList = response.data
                                    val loanCodelist: Array<String> = LoanEmiList!!.map { it!!.loanCode }.toTypedArray()

                                    LoanEmiList.forEach{ it->
                                        makepaymentList.add(MakePaymentDataModel(it!!.loanCode,it.tenure,it.emiAmount,it.paidEmi,it.duesEmi,it.startDate,it.gracePeriod,it.customergracePeriod,it.latefine))
                                    }

                                    if(loanCodelist.isNotEmpty()){
                                        setDataForSpinner(loanCodelist)
                                    }

                                }
                            }
                        }

                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }

    }


    fun HitApiForUploadReceipt(txnNumber : String){
        val custPhotoPart = createMultipartFromUri(this, receiptUri, "ReceiptImage_FileName","ReceiptPhoto")
        val file=saveImageToCache(this,receiptUri,"ReceiptPhoto")
        val firstName = preference.getStringValue(ConstantClass.FirstName, "").orEmpty()
        val lastName = preference.getStringValue(ConstantClass.LastName, "").orEmpty()
        val safeLastName = if (!lastName.isNullOrBlank() && lastName != "null") lastName else ""
        //var createdBy = firstName.plus(" ").plus(safeLastName)
        var customerCode =  preference.getStringValue(ConstantClass.CustomerCode, "")
        var createdBy=""
        if(logintype.equals(Customer)){
            createdBy = preference.getStringValue(ConstantClass.CustomerCode, "")
        }
        else{
            createdBy = preference.getStringValue(ConstantClass.RetailerCode, "")
        }

        var createdAt = getCurrentUtcTimestamp()
        val requestMap = hashMapOf(
            "CustomerCode" to customerCode.toRequestBody(),
            "LoanCode" to binding.loanid.selectedItem.toString().toRequestBody(),
            "PaidAmount" to binding.paidamount.text.toString().toRequestBody(),
            "ReceiptImage_Path" to file!!.name.toRequestBody(),
            "CreatedBy" to createdBy.toRequestBody(),
            "CreatedAt" to createdAt.toRequestBody(),
            "ActiveStatus" to "Active".toRequestBody(),
            "RecordStatus" to  "Pending".toRequestBody(),
            "PaidEMINo" to  binding.noofemi.selectedItem.toString().toRequestBody(),
            "TxnNumber" to txnNumber.toRequestBody(),
            "Remarks" to binding.remarks.text.toString().toRequestBody()
        )
        Log.d("RequestMakePayment",requestMap.toString())

        ConstantClass.OpenPopUpForVeryfyOTP(this)
        lifecycleScope.launch {
            try {
                val response = api.getCustomerReceiptUpload(
                    requestMap["CustomerCode"]!!,
                    requestMap["LoanCode"]!!,
                    requestMap["PaidAmount"]!!,
                    requestMap["ReceiptImage_Path"]!!,
                    requestMap["CreatedBy"]!!,
                    requestMap["CreatedAt"]!!,
                    requestMap["ActiveStatus"]!!,
                    requestMap["RecordStatus"]!!,
                    requestMap["PaidEMINo"]!!,
                    requestMap["TxnNumber"]!!,
                    requestMap["Remarks"]!!,
                    custPhotoPart!!
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("ResponseReceipt", body!!.message)
                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                        ConstantClass.dialog.dismiss()
                    }
                    binding.paidamount.text=""

                   // Toast.makeText(this@MakePaymentPage,body!!.message,Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@MakePaymentPage,"Your request has been submitted for approval",Toast.LENGTH_SHORT).show()

                    if(logintype.equals(Customer)){
                        finish()
                    }
                    else{
                        val intent = Intent(this@MakePaymentPage, DashBoard::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                }
                else {
                    // Handle error
                    Log.e("API ERROR", response.errorBody()?.string() ?: "Unknown error")
                }
            }
            catch (e: Exception) {
                Log.e("API EXCEPTION", e.toString())
            }
        }
    }*/


}