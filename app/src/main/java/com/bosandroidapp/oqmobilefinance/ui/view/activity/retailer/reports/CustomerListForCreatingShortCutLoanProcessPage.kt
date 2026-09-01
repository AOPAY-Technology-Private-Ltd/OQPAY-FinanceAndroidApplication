package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosandroidapp.oqmobilefinance.data.model.EmandateOptionSelectetionReq
import com.bosandroidapp.oqmobilefinance.data.model.EmandateSelectDataItem
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.EmandateOptionAdapter
import com.bosandroidapp.bosmobilefinance.ui.slideshow.ui.view.activity.retailer.cibilreportsfragment.BureauScore.Companion.userScore
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadhaarResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountHolderName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankIFSCCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BrandName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CibilResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CreatedByCustomerShortCut
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileNumber
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustomerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DefaulterEmiDebitPending
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DownPayment
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.EmiAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber1
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber2
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.InterestAmt
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.InterestRate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.IsRetailerAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanEndDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanRID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanStartDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanStatus
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ModelColor
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ModelName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ModelVarient
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.SellingPrice
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Tenure
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ToBePaidAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.UPIMandate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.cacheImageAndGetUri
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.cacheImageAndGetUriForShortCutLoan
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.downloadImageToTemp
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAccessKeyVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isEmandateVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isPannydropVerified
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerSearchForShortCutLoanRequest
import com.bosandroidapp.oqmobilefinance.data.model.CustomerShortCutDataItem
import com.bosandroidapp.oqmobilefinance.data.model.CustomerStepDataItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.ShortCutCustomerRequest
import com.bosandroidapp.oqmobilefinance.data.model.ShortCutCustomerResponse
import com.bosandroidapp.oqmobilefinance.data.model.UPIMandateRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ManageCustomerStepWiseReq
import com.bosandroidapp.oqmobilefinance.data.pennydrop.BankListReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.PanViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerListForCreatingShortCutLoanProcessPageBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.CustomerPhotoPath
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.loaneCode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.IMEIDetailsPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MobileSelectionActivity
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.NewCustomerRegistrationPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.PaymentInformation
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.QRCodePage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.QRCodePage.Companion.isEnachCancelled
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage.Companion.webUrl
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.CustomerShortcutLoanAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.isNotEmpty
import kotlin.math.roundToInt

class CustomerListForCreatingShortCutLoanProcessPage : AppCompatActivity() {
    private lateinit var preference: SharedPreference
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var panViewModel: PanViewModel
    var bankList = mutableListOf<Pair<String, Int>>()
    private var customerList: MutableList<CustomerStepDataItem> = mutableListOf()

    private var FilterReportDataList: MutableList<CustomerStepDataItem> = mutableListOf()
    private lateinit var customerAdapter: CustomerShortcutLoanAdapter
    private lateinit var binding: ActivityCustomerListForCreatingShortCutLoanProcessPageBinding

    private var selectedRegistrationID: String = ""
    private var selectedAuthType: String = ""
    private var emandateSelectList: MutableList<Pair<String, List<EmandateSelectDataItem>>?> = mutableListOf()

    lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerListForCreatingShortCutLoanProcessPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface))
        )[AuthenticationViewModel::class.java]
        panViewModel = ViewModelProvider(this, PanViewModelFactory(PanRepository(RetrofitClient.apiInterfacePAN))
        )[PanViewModel::class.java]
        preference = SharedPreference(this)

        setonclickListner()
        setupRecyclerView()
        setview()


    }



    fun setonclickListner() {

        binding.searcCustomer.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val search = s.toString().trim()


            }

            override fun afterTextChanged(s: Editable?) {
                val search = s.toString().trim()

                if(search.isNotEmpty()){
                    var filterList = customerList.filter {
                                it.fullName!!.lowercase().contains(search.lowercase()) ||
                                it.customerCode!!.lowercase().contains(search.lowercase())||
                                it.mobileNumber!!.lowercase().contains(search.lowercase())

                    } as MutableList<CustomerStepDataItem>
                    customerDataAccordingToStatus(filterList)
                }
            }

        })




        binding.back.setOnClickListener {
            finish()
        }


    }


    fun hitApiForCustomerwiseDetails(customerCode: String?,step: String?) {
        var req = CustomerSearchForShortCutLoanRequest(
            searchText = customerCode
        )

        Log.d("SearchReq", Gson().toJson(req))

        viewModel.getCustomerDataForSearch(req).observe(this) { resource ->
            resource.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        ConstantClass.dialog.dismiss()
                        it.data?.let { users ->
                            if (users.isSuccessful) {
                                users.body()?.let { response ->
                                    if (response.code == 200 && response.status == true) {
                                        var getCustomerData = response.data!![0]
                                        handleCustomerClick(getCustomerData,step)
                                    }
                                    else {
                                        Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, response.message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, "Error: ${users.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, resource.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }

    }


    fun setview() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.customershortcutlist, R.layout.mobilenamelayout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.reporttype.adapter = adapter

        binding.reporttype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                val selectedItem = parent.getItemAtPosition(position).toString()

                if(selectedItem=="All"){
                    hitApiForGetReports()
                    binding.searcCustomer.setText("")
                }
                else {
                    var filterList = customerList.filter {
                        it.customerStatus!!.lowercase().contains(selectedItem.lowercase())
                    } as MutableList<CustomerStepDataItem>
                    customerDataAccordingToStatus(filterList)
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }
        }

    }


    private fun setupRecyclerView() {
        customerAdapter = CustomerShortcutLoanAdapter(customerList, this) { item ->
            var customer = item.customerCode
            var step = item.currentStep
            hitApiForCustomerwiseDetails(customer,step)
        }
        binding.showCustomerreports.layoutManager = LinearLayoutManager(this)
        binding.showCustomerreports.adapter = customerAdapter
    }


    private fun handleCustomerClick(item: CustomerShortCutDataItem,step : String?) {
        val customerDetails = item.customerDetails
        val productDetails = item.productDetails
        val bankDetails = item.bankDetails
        val eMandateDetails = item.eMandateDetails
        val referenceDetails = item.referenceDetails
        val imeiDetails = item.imeiDetails
        val createLoanDetails = item.createLoanDetails
        val invoiceAndAppVerification = item.invoiceAndAppVerification

        if (invoiceAndAppVerification?.isAccessKeyVerified.isNullOrBlank() || invoiceAndAppVerification?.isAccessKeyVerified.equals("no", ignoreCase = true)) {

            if (customerDetails != null) {
                val image = ConstantClass.BASE_URL_IMAGE + customerDetails.custPhotoPath

                lifecycleScope.launch(Dispatchers.IO) {

                    val imageFile = downloadImageToTemp(
                        this@CustomerListForCreatingShortCutLoanProcessPage,
                        image
                    )

                    withContext(Dispatchers.Main) {
                        if (imageFile != null) {
                            var filepath = imageFile!!.absolutePath
                            Log.d("IMAGE", "File saved: ${imageFile.absolutePath}")
                            Log.d("IMAGE", "Exists: ${imageFile.exists()}")
                        } else {
                            Log.e("IMAGE", "Download failed")
                        }
                    }

                }

                CustomerPhotoPath = image
                CustomerCodeForEnach = customerDetails.customerCode!!
                preference.setStringValue(ConstantClass.CustomerCode, CustomerCodeForEnach)
                CustFirstName = customerDetails.firstName!!
                CustMiddleName = customerDetails.middleName!!
                AccountHolderName = "${customerDetails.firstName} ${customerDetails.lastName}"
                CustLastName = customerDetails.lastName!!
                CustPrimaryMobileNumber = customerDetails.primaryMobileNumber!!
                CustPrimaryOTP = customerDetails.primaryOTP!!
                CustPrimaryMobileVerified = customerDetails.primaryMobileVerified!!
                CustAlternateMobileNumber = customerDetails.alternateMobileNumber!!
                CusteMailID = customerDetails.eMailID!!
                CustFlatNo = customerDetails.flatNo!!
                CustAreaSector = customerDetails.aearSector!!
                CustPinCode = customerDetails.pinCode!!
                CustCurrentAddress = customerDetails.currentAddress!!
                CustStateName = customerDetails.stateName!!
                CustCityName = customerDetails.cityName!!
                CustCountry = customerDetails.country!!
                AadharNumber = customerDetails.aadharNumber!!
                ConstantClass.AadharVerified = customerDetails.aadharNumberVerified!!
                PanNumber = customerDetails.panNumber!!
                PanNumberVerified = customerDetails.panNumberVerified!!
                CreatedByCustomerShortCut = customerDetails.createdBy!!
                userScore = customerDetails.cibilScore?.trim()?.toFloatOrNull() ?: 0f
                isAggrementVerified = customerDetails.isAggrementVerified!!
                ConstantClass.CustomerActiveStatus = customerDetails.activeStatus!!
                PanResponse = customerDetails.panApiResponse!!
                AadhaarResponse = customerDetails.aadhaarApiResponse!!
                CibilResponse = customerDetails.cibilApiResponse!!
                RetailerCodeForEnach = customerDetails.retailerCode!!
            }

            if (productDetails != null) {
                BrandName = productDetails.brandName!!
                ModelName = productDetails.modelName!!
                ModelVarient = productDetails.modelVariant!!
                ModelColor = productDetails.color!!
                SellingPrice = productDetails.sellingPrice!!
                DownPayment = productDetails.downPayment!!
                Tenure = productDetails.tenure!!
                EmiAmount = productDetails.emiAmount!!
                ConstantClass.LoanAmount = productDetails.loanAmount?.trim()?.toDoubleOrNull() ?: 0.0
                InterestRate = productDetails.interestRate!!
                ProcessingFees = productDetails.processingFees!!
                InterestAmt = productDetails.interestAmt!!
                val dp = DownPayment?.toDoubleOrNull() ?: 0.0
                val pf = ProcessingFees?.toDoubleOrNull() ?: 0.0
                val toBePaidNow = dp + pf
                ToBePaidAmount = "%.2f".format(toBePaidNow)
            }

            if (bankDetails != null) {
                AccountNumber = bankDetails.accountNumber!!
                BankIFSCCode = bankDetails.bankIFSCCode!!
                BankName = bankDetails.bankName!!
                AccountType = bankDetails.accountType!!
                BranchName = bankDetails.branchName!!
                BranchAddress = bankDetails.branchAddress!!
                isPannydropVerified = bankDetails.isPannydropVerified!!
            }

            if (eMandateDetails != null) {
                UPIMandate = eMandateDetails.upiMandate!!
            }

            if (referenceDetails != null) {
                RefName = referenceDetails.refName!!
                RefRelationShip = referenceDetails.refRelationShip!!
                RefmobileNo = referenceDetails.refmobileNo!!
                RefAddress = referenceDetails.refAddress!!
                ReferenceAadharVerified = referenceDetails.isrefKycVerified!!
                ReferenceAadharNumber = referenceDetails.refAdhaarNumber!!
            }

            if (imeiDetails != null) {
                ImeiNumber1 = imeiDetails.imeiNumber1!!
                ImeiNumber2 = imeiDetails.imeiNumber2!!
                IsRetailerAggrementVerified = imeiDetails.isRetailerAggrementVerified!!
            }

            if (createLoanDetails != null) {
                LoanMode = createLoanDetails.loanMode!!
                LoanStatus = createLoanDetails.loanStatus!!
                LoanRID = createLoanDetails.loanRID?.takeIf { it.isNotBlank() }?.toIntOrNull() ?: 0
                loaneCode = createLoanDetails.loanCode!!
                ConstantClass.DefaultEmidebit = createLoanDetails.defaulterEmiDebit!!
                if (createLoanDetails.loanStartDate != null && createLoanDetails.loanEndDate != null) {
                    LoanStartDate = createLoanDetails.loanStartDate!!
                    LoanEndDate = createLoanDetails.loanEndDate!!
                } else {
                    LoanStartDate = ""
                    LoanEndDate = ""
                }
                isEmandateVerified = createLoanDetails.isEmandateVerified!!
                ConstantClass.CheckOnlineOrOffline = createLoanDetails.loanMode ?: ConstantClass.online
            }

            if (invoiceAndAppVerification != null) {
                isAccessKeyVerified = invoiceAndAppVerification.isAccessKeyVerified!!
            }

            if (createLoanDetails?.loanCode.isNullOrBlank()) {
                showOnlineOfflineDialog(item, step!!)
            }
            else {
                proceedWithLoanLogic(item,step!!)
            }

        }

    }


    private fun showOnlineOfflineDialog(item: CustomerShortCutDataItem,step : String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_online_offline)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.setCanceledOnTouchOutside(false)

        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radioGroup)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnOk = dialog.findViewById<Button>(R.id.btnOk)


        btnCancel.setOnClickListener {
            dialog.dismiss()
        }


        btnOk.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId

            if (selectedId == -1) {
                Toast.makeText(this, "Please select mode", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ConstantClass.CheckOnlineOrOffline = if (selectedId == R.id.rbOnline) {
                ConstantClass.online
            } else {
                ConstantClass.offline
            }

            dialog.dismiss()

            navigateToNextStep(item,step)
        }

        dialog.show()
    }



    private fun navigateToNextStep(item: CustomerShortCutDataItem, step: String) {
        var customerDetails = item.customerDetails


        when(step) {
            "0"-> {
                startActivity(Intent(this, NewCustomerRegistrationPage::class.java))
            }
            // Step 1: Mobile Selection (Brand/EMI)
            "1" -> {
                startActivity(Intent(this, MobileSelectionActivity::class.java))
            }


            // Step 2: Payment Info - Bank/Pennydrop (EMandate is blank)
           "2" -> {
                startActivity(Intent(this, PaymentInformation::class.java).apply {
                    putExtra("EMandate", "")
                })
            }


            // Step 3: Payment Info - E-Mandate (Before Loan Created)
            "3"  -> {
                startActivity(Intent(this, PaymentInformation::class.java).apply {
                    putExtra("EMandate", "NO")
                })
            }


            // Step 4: Payment Info - Reference (Before Loan Created)
            "4"-> {
                startActivity(Intent(this, PaymentInformation::class.java).apply {
                    putExtra("EMandate", "Yes")
                })
            }


            // Step 5: IMEI Details
           "5"-> {
                startActivity(Intent(this, IMEIDetailsPage::class.java))
            }


            // Step 6: Create Loan (QR Code)
            "6"  -> {
                startActivity(Intent(this, QRCodePage::class.java))
            }

            else -> {
                Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage,"Customer ${customerDetails!!.customerCode} data is missing!!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun ShowPopUpForEnachProcess(item: CustomerShortCutDataItem) {
        val dialog = Dialog(this, R.style.FullScreenDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_enach_process)
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setCanceledOnTouchOutside(false)
        val nextLayout = dialog.findViewById<LinearLayout>(R.id.nextlayout)
        val accountnumber = dialog.findViewById<TextView>(R.id.accountnumber)
        val banificeryName = dialog.findViewById<TextView>(R.id.banificeryName)
        val ifsccode = dialog.findViewById<TextView>(R.id.ifsccode)
        val bankname = dialog.findViewById<TextView>(R.id.bankname)
        val branchname = dialog.findViewById<TextView>(R.id.branchname)
        val branchaddress = dialog.findViewById<TextView>(R.id.branchaddress)
        val acounttype = dialog.findViewById<TextView>(R.id.acounttype)
        val back = dialog.findViewById<ImageView>(R.id.back)


        var customerDetails = item.customerDetails
        var productDetails = item.productDetails
        var bankDetails = item.bankDetails
        var eMandateDetails = item.eMandateDetails
        var referenceDetails = item.referenceDetails
        var imeiDetails = item.imeiDetails
        var createLoanDetails = item.createLoanDetails

        accountnumber.text = AccountNumber
        banificeryName.text = "${customerDetails!!.firstName} ${customerDetails!!.lastName}"
        ifsccode.text = BankIFSCCode
        bankname.text = BankName
        branchname.text = BranchName
        branchaddress.text = BranchAddress
        acounttype.text = AccountType

        if (bankList.isNotEmpty()) {

            BankID = bankList.find { it.first == BankName }?.second!!
            Log.d("FetchBankList", Gson().toJson(bankList))
        }

        nextLayout.setOnClickListener {
            if (banificeryName.text.toString().isEmpty()) {
                Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, "Please enter banificery name", Toast.LENGTH_SHORT).show()
            } 
            else {
                val startDate = LoanStartDate
                val endDate = LoanEndDate
                val emiAmountVal = productDetails!!.emiAmount?.toDoubleOrNull()?.roundToInt() ?: 0

                val request = EMandateRequest(
                    categoryID = 7,
                    collectionAmount = emiAmountVal,
                    collectCollectionUntilCancle = false,
                    seqType = "RCUR",
                    iFSCCode = bankDetails!!.bankIFSCCode,
                    frequncy = "MNTH",
                    registrationID = if (LoanMode == ConstantClass.online) {
                            ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
                    }
                    else {
                            ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
                           },
                    accountHolderName = "${customerDetails.firstName} ${customerDetails.lastName}",
                    finalCollectionDate = endDate,
                    loanNo = createLoanDetails!!.loanCode,
                    accountType = bankDetails!!.accountType,
                    emailAddress = customerDetails.eMailID ?: CusteMailID,
                    firstCollectionDate = startDate,
                    mobileNumber = customerDetails.primaryMobileNumber,
                    bankAccountNumberConfirmation = bankDetails.accountNumber,
                    addIn2 = BranchAddress,
                    addIn3 = "",
                    debitType = true,
                    teleNumber = "",
                    authType = "",
                    bankID = BankID,
                    bankAccountNumber = bankDetails.accountNumber
                )
                hitApiForEnach(request, item, true)
            }
        }

        back.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun hitApiForBankList(item: CustomerShortCutDataItem) {
        bankList.clear()

        var req = BankListReq(
            registrationID = if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
            }
            else {
                ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
            }
        )
        Log.d("BankListReq", Gson().toJson(req))

        panViewModel.getBankListReq(req).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->

                                if (response!!.status!!.toLowerCase().equals("false")) {
                                    ConstantClass.dialog.dismiss()
                                    Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage,
                                        response.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                response?.data?.banks?.forEach {
                                    bankList.add(Pair(it!!.name!!, it.id) as Pair<String, Int>)
                                }

                                if (bankList.isNotEmpty()) {
                                    ConstantClass.dialog.dismiss()
                                    ShowPopUpForEnachProcess(item)
                                }

                                Log.d("List", Gson().toJson(response?.data?.banks))

                            }

                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(
                            this@CustomerListForCreatingShortCutLoanProcessPage,
                            resources.message ?: "Error occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }

            }

        }


    }


    private fun proceedWithLoanLogic(item: CustomerShortCutDataItem,step: String) {
        var loanData = item.createLoanDetails
        var invoiceAppVerification = item.invoiceAndAppVerification

        val isEmandateVerifiedStatus = loanData!!.isEmandateVerified.equals("yes", true)
        val isAccessKeyVerifiedStatus = invoiceAppVerification!!.isAccessKeyVerified.equals("yes", true)

        if (!isEmandateVerifiedStatus) {
            // Step 7: E-Mandate Process (After Loan Created)
            showEmandateSelectionDialog(item)

        } else if (!isAccessKeyVerifiedStatus ) {
            // Step 8: App Install
            startActivity(Intent(this, AppScanInstallPage::class.java))
        }
        else {
            Toast.makeText(this, "Loan process is already completed for this customer.", Toast.LENGTH_SHORT).show()
        }

    }


    private fun showEmandateSelectionDialog(item: CustomerShortCutDataItem) {
        val dialog = Dialog(this, R.style.FullScreenDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_emandate_selection)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.setCanceledOnTouchOutside(false)


        val btnProceed = dialog.findViewById<LinearLayout>(R.id.nextlayout)
        val back = dialog.findViewById<ImageView>(R.id.back)

        fetchEmandateOptionsForDialog(dialog)

        btnProceed.setOnClickListener {
            if (selectedRegistrationID.isNotBlank()) {
                dialog.dismiss()
                hitApiForBankList(item)
            } else {
                Toast.makeText(this, "Please select an E-Mandate service", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    private fun fetchEmandateOptionsForDialog(dialog: Dialog) {
        val req = EmandateOptionSelectetionReq(
            mode = LoanMode,
            registrationID = if (LoanMode == ConstantClass.online) {
                ConstantClass.Enach_Option_Online_REGISTRATION_ID
            } else {
                ConstantClass.Enach_Option_Offline_REGISTRATION_ID
            }
        )
        setupEmandateOptionsInDialog(req, dialog)
    }


    private fun setupEmandateOptionsInDialog(req: EmandateOptionSelectetionReq, dialog: Dialog) {
        val layout = dialog.findViewById<LinearLayout>(R.id.emandateOptionLayout)
        val rv = dialog.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.selectOptionForeMandate)

        Log.d("EmandateOptionReq", Gson().toJson(req))

        viewModel.geteMandateSelectOptionRequest(req).observe(this) { resources ->
            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    resources.data?.let { users ->
                        if (users.isSuccessful) {
                            users.body()?.let { response ->
                                if (response.code == 200) {
                                    val getdata = response.data
                                    if (!getdata.isNullOrEmpty()) {
                                        layout.visibility = View.VISIBLE
                                        emandateSelectList.clear()
                                        emandateSelectList.addAll(listOf(Pair(req.mode!!, getdata.filterNotNull()) as Pair<String, List<EmandateSelectDataItem>>?))

                                        val adapter = EmandateOptionAdapter(emandateSelectList) { selectedOption ->
                                            val option = selectedOption.second
                                            selectedRegistrationID = option.registrationID ?: ""
                                            selectedAuthType = option.apiName ?: ""
                                            Log.d("SelectedMandateDialog", "$selectedRegistrationID $selectedAuthType")
                                        }
                                        rv.adapter = adapter
                                        adapter.selectFirstOption()
                                    } else {
                                        layout.visibility = View.GONE
                                    }
                                } else {
                                    layout.visibility = View.GONE
                                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, resources.message ?: "EMandate Options Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(this, resources.message ?: "EMandate Options Error", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.LOADING -> {}
            }
        }

    }


    fun hitApiForEnach(request: EMandateRequest, customerDataForShortCut: CustomerShortCutDataItem, check: Boolean) {
        Log.d("eManadateReq", Gson().toJson(request))

        var bankData = customerDataForShortCut.bankDetails
        var customerData = customerDataForShortCut.customerDetails
        var loanData = customerDataForShortCut.createLoanDetails

        if (LoanMode == ConstantClass.offline) {

            panViewModel.getEMandateRequestReq(request).observe(this) { resources ->
                when (resources.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        ConstantClass.dialog.dismiss()
                        resources.data?.let { users ->
                            if (users.isSuccessful) {
                                users.body()?.let { response ->
                                    Log.d("eMandateRes", Gson().toJson(response))

                                    if (response.data?.customer != null) {
                                        webUrl = response.data.url
                                        startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, RetailerEMandateVerifyPage::class.java))
                                    }
                                    else
                                    {
                                        isEnachCancelled = true
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                    }

                                    var verifiedStatus = "No"

                                    if (response.data?.customer != null) {
                                        verifiedStatus = "Yes"
                                    }

                                    val uploadReq = EnachDateUploadReq(
                                        isEmandateVerified = verifiedStatus,
                                        emAccountType = bankData!!.accountType,
                                        isPannydropVerified = bankData.isPannydropVerified,
                                        emAccountNumber = bankData.accountNumber,
                                        customerCode = customerData!!.customerCode,
                                        retailerCode = customerData!!.retailerCode,
                                        loanCode = loanData!!.loanCode,
                                        emBankName = bankData.bankName,
                                        emIfscCode = bankData.bankIFSCCode,
                                        emumrn = ""
                                    )

                                    hitApiForUploadEnachMandateDataResponse(uploadReq)
                                }

                                val error = users.body()?.statusDesc ?: users.message()
                                ?: "Something went wrong"

                                Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                            } else {
                                var error = resources.data.toString()
                                Toast.makeText(
                                    this@CustomerListForCreatingShortCutLoanProcessPage,
                                    error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } ?: run {
                            resources.message?.let { message ->
                                showError(message)
                            }

                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(
                            this,
                            resources.message ?: "Server error occurred",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    ApiStatus.LOADING -> {
                        if (check) {
                            ConstantClass.OpenPopUpForVeryfyOTP(this)
                        }
                    }
                }
            }
        }
        else {

            if(selectedAuthType!!.contains(ConstantClass.UPIAUTOPAY)){
                var request = UPIMandateRequest(
                    registrationID = request.registrationID
                )
                Log.d("UPIMandateReq", Gson().toJson(request))
                panViewModel.getUpiMandateOnlineRequest(request).observe(this) { resources ->
                    resources.let {
                        when (it.apiStatus) {
                            ApiStatus.SUCCESS -> {
                                it.data.let { users ->

                                    if(users!!.isSuccessful){
                                        users!!.body().let { response ->
                                            Log.d("eMandateUpiOnlineRes", Gson().toJson(response))

                                            if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                                ConstantClass.dialog.dismiss()
                                            }

                                            if (response!!.code=="200" ) {
                                                webUrl = response!!.intentUrl
                                                Log.d("webUrl", webUrl.toString())
                                                startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, RetailerEMandateVerifyPage::class.java))
                                            }
                                            else {
                                                ConstantClass.dialog.dismiss()
                                                isEmandateVerified= "No"
                                                isEnachCancelled = true
                                                Toast.makeText(this, response.errorMessage.toString(), Toast.LENGTH_SHORT).show()
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
                                                    emIfscCode =BankIFSCCode,
                                                    emumrn = ""
                                                )

                                                hitApiForUploadEnachMandateDataResponse(request)
                                            }

                                        }

                                    }
                                    else {
                                        Toast.makeText(this, resources.message ?: "Server error occurred", Toast.LENGTH_LONG).show()
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
                    when (resources.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            ConstantClass.dialog.dismiss()
                            resources.data?.let { users ->
                                if (users.isSuccessful) {
                                    users.body()?.let { response ->
                                        Log.d("eMandateOnlineRes", Gson().toJson(response))

                                        if (response.data?.customer != null) {
                                            webUrl = response.data.url
                                            startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, RetailerEMandateVerifyPage::class.java))
                                        }
                                        else {
                                            isEnachCancelled = true
                                            Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                        }

                                        var verifiedStatus = "No"

                                        if (response.data?.customer != null) {
                                            verifiedStatus = "Yes"
                                        }

                                        val uploadReq = EnachDateUploadReq(
                                            isEmandateVerified = verifiedStatus,
                                            emAccountType = bankData!!.accountType,
                                            isPannydropVerified = bankData.isPannydropVerified,
                                            emAccountNumber = bankData.accountNumber,
                                            customerCode = customerData!!.customerCode,
                                            retailerCode = customerData.retailerCode,
                                            loanCode = loanData!!.loanCode,
                                            emBankName = bankData.bankName,
                                            emIfscCode = bankData.bankIFSCCode,
                                            emumrn = ""
                                        )

                                        hitApiForUploadEnachMandateDataResponse(uploadReq)
                                    }

                                    val error = users.body()?.statusDesc ?: users.message() ?: "Something went wrong"

                                    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
                                }
                                else {
                                    var error = resources.data.toString()
                                    Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, error, Toast.LENGTH_SHORT).show()
                                }

                            }
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            Toast.makeText(
                                this,
                                resources.message ?: "Server error occurred",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        ApiStatus.LOADING -> {
                            if (check) {
                                ConstantClass.OpenPopUpForVeryfyOTP(this)
                            }
                        }
                    }
                }
            }

        }
    }


    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    fun hitApiForUploadEnachMandateDataResponse(request: EnachDateUploadReq) {
        viewModel.UpdateEmandateDetails(request).observe(this) { resources ->
            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    Log.d("UploadEnachRes", "Success")
                }

                ApiStatus.ERROR -> {
                    Log.e("UploadEnachRes", "Error: ${resources.message}")
                }

                ApiStatus.LOADING -> {}
            }
        }
    }



    fun hitApiForGetReports(searchText: String = "", isAutoClick: Boolean = false) {

        val retailerCode = preference.getStringValue(ConstantClass.RetailerCode, "")

        val reportreq = ShortCutCustomerRequest(
            searchText =searchText,
            retailerCode = retailerCode
        )

        Log.d("RetailerCustomerListReq", Gson().toJson(reportreq))

        viewModel.getCustomerDataSummaryForShortCut(reportreq).observe(this) { resources ->
                when (resources.apiStatus) {

                    ApiStatus.SUCCESS -> {
                        ConstantClass.dialog.dismiss()
                        resources.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("RetailerCustomerListResponse", Gson().toJson(response))
                                customerList.clear()
                                var getdata = response.data
                                var errorCode = response.code
                                var status = response.success

                                if(errorCode==200 && status==true){
                                    customerList = response.data as MutableList<CustomerStepDataItem>
                                    if (customerList.isNotEmpty()) {
                                        binding.showCustomerreports.visibility = View.VISIBLE
                                        binding.notfoundimage.visibility = View.GONE
                                        customerAdapter.updateData(customerList)

                                    }
                                    else {
                                        binding.showCustomerreports.visibility = View.GONE
                                        binding.notfoundimage.visibility = View.VISIBLE
                                        if (isAutoClick) {
                                            Toast.makeText(this, "Customer not found", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                }
                                else {
                                    binding.showCustomerreports.visibility = View.GONE
                                    binding.notfoundimage.visibility = View.VISIBLE
                                    Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, response.message, Toast.LENGTH_SHORT).show()
                                }

                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@CustomerListForCreatingShortCutLoanProcessPage, resources.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
    }


    fun customerDataAccordingToStatus(customerList: MutableList<CustomerStepDataItem>){
        if (customerList.isNotEmpty()) {
            binding.showCustomerreports.visibility = View.VISIBLE
            binding.notfoundimage.visibility = View.GONE
            customerAdapter.updateData(customerList)
        }
        else {
            binding.showCustomerreports.visibility = View.GONE
            binding.notfoundimage.visibility = View.VISIBLE
        }
    }


}
