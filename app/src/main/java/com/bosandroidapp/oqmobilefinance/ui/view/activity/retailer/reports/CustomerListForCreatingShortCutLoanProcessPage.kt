package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.app.Dialog
import android.content.Intent
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bos.payment.appName.network.RetrofitClient
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAccessKeyVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isEmandateVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isPannydropVerified
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerShortCutDataItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ManageCustomerStepWiseReq
import com.bosandroidapp.oqmobilefinance.data.pennydrop.BankListReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.PanViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerListForCreatingShortCutLoanProcessPageBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.loaneCode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.IMEIDetailsPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MobileSelectionActivity
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
import kotlin.collections.isNotEmpty
import kotlin.math.roundToInt

class CustomerListForCreatingShortCutLoanProcessPage : AppCompatActivity() {
    private lateinit var preference: SharedPreference
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var panViewModel: PanViewModel
    var bankList = mutableListOf<Pair<String, Int>>()
    private var customerList: MutableList<CustomerShortCutDataItem> = mutableListOf()

    private var FilterReportDataList: MutableList<CustomerShortCutDataItem> = mutableListOf()
    private lateinit var customerAdapter: CustomerShortcutLoanAdapter
    private lateinit var binding: ActivityCustomerListForCreatingShortCutLoanProcessPageBinding

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

        binding.searcMobile.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val search = s.toString().lowercase().trim()
                if (search.isEmpty()) {
                    customerAdapter.updateData(customerList)
                } else {
                    val result = customerList.filter {
                        val details = it.customerDetails
                        val loan = it.createLoanDetails
                        details?.customerCode?.lowercase()?.contains(search) == true ||
                                details?.firstName?.lowercase()?.contains(search) == true ||
                                details?.lastName?.lowercase()?.contains(search) == true ||
                                details?.primaryMobileNumber?.lowercase()?.contains(search) == true ||
                                loan?.loanCode?.lowercase()?.contains(search) == true
                    }
                    customerAdapter.updateData(result)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.back.setOnClickListener {
            finish()
        }
    }


    fun setDataInView(customerList: List<CustomerShortCutDataItem>) {
        Log.d("List", Gson().toJson(customerList))
        if (customerList.isNotEmpty()) {
            binding.showCustomerreports.visibility = View.VISIBLE
            binding.notfoundimage.visibility = View.GONE
            customerAdapter.updateData(customerList)
        } else {
            binding.showCustomerreports.visibility = View.GONE
            binding.notfoundimage.visibility = View.VISIBLE
        }
    }


    fun setview() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.customershortcutlist, R.layout.mobilenamelayout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.reporttype.adapter = adapter

        binding.reporttype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if(selectedItem=="All"){
                    hitApiForGetReports()
                    binding.searcMobile.setText("")
                }
                else {
                    var filterList = customerList.filter {
                        var customerDetailsData = it.customerDetails
                        customerDetailsData!!.activeStatus!!.lowercase().contains(selectedItem.lowercase())
                    } as MutableList<CustomerShortCutDataItem>
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
            handleCustomerClick(item)
        }
        binding.showCustomerreports.layoutManager = LinearLayoutManager(this)
        binding.showCustomerreports.adapter = customerAdapter
    }

    /* Search for a customer by code, name, or loan code and automatically trigger click logic if found.*/

    fun searchAndHandleCustomer(query: String) {
        val search = query.lowercase().trim()
        val foundItem = customerList.find {
            val details = it.customerDetails
            val loan = it.createLoanDetails
            details?.customerCode?.lowercase() == search ||
                    details?.firstName?.lowercase() == search ||
                    details?.lastName?.lowercase() == search ||
                    "${details?.firstName?.lowercase()} ${details?.lastName?.lowercase()}" == search ||
                    loan?.loanCode?.lowercase() == search
        }

        if (foundItem != null) {
            handleCustomerClick(foundItem)
        } else {
            Toast.makeText(this, "Customer not found with: $query", Toast.LENGTH_SHORT).show()
        }
    }
    

    private fun handleCustomerClick(item: CustomerShortCutDataItem) {
        val customerDetails = item.customerDetails
        val productDetails = item.productDetails
        val bankDetails = item.bankDetails
        val eMandateDetails = item.eMandateDetails
        val referenceDetails = item.referenceDetails
        val imeiDetails = item.imeiDetails
        val createLoanDetails = item.createLoanDetails
        val invoiceAndAppVerification = item.invoiceAndAppVerification

        if (invoiceAndAppVerification?.isAccessKeyVerified.isNullOrBlank() ||
            invoiceAndAppVerification?.isAccessKeyVerified.equals("no", ignoreCase = true)
        ) {

            if (customerDetails != null) {
                val image = ConstantClass.BASE_URL_IMAGE + customerDetails.custPhotoPath
                CustPhotoPath = cacheImageAndGetUri(this, image)
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
                showOnlineOfflineDialog(item)
            } else {
                proceedWithLoanLogic(item)
            }
        }
    }


    private fun showOnlineOfflineDialog(item: CustomerShortCutDataItem) {
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

            navigateToNextStep(item)
        }

        dialog.show()
    }



    private fun navigateToNextStep(item: CustomerShortCutDataItem) {
        var customerDetails = item.customerDetails
        var productDetails = item.productDetails
        var bankDetails = item.bankDetails
        var eMandateDetails = item.eMandateDetails
        var referenceDetails = item.referenceDetails
        var imeiDetails = item.imeiDetails
        var loanData = item.createLoanDetails

        when {
            // Step 1: Mobile Selection (Brand/EMI)
            productDetails!!.brandName.isNullOrBlank() -> {
                startActivity(Intent(this, MobileSelectionActivity::class.java))
            }


            // Step 2: Payment Info - Bank/Pennydrop (EMandate is blank)
            bankDetails!!.bankName.isNullOrBlank()|| bankDetails!!.isPannydropVerified!!.equals("no", ignoreCase = true)&&productDetails.tenure!!.isNotEmpty() -> {
                startActivity(Intent(this, PaymentInformation::class.java).apply {
                    putExtra("EMandate", "")
                })
            }


            // Step 3: Payment Info - E-Mandate (Before Loan Created)
            bankDetails!!.isPannydropVerified!!.equals("yes", ignoreCase = true)&& eMandateDetails!!.upiMandate.isNullOrBlank() && productDetails.tenure!!.isNotEmpty()  -> {
                startActivity(Intent(this, PaymentInformation::class.java).apply {
                    putExtra("EMandate", "NO")
                })
            }


            // Step 4: Payment Info - Reference (Before Loan Created)
            eMandateDetails!!.upiMandate!!.isNotEmpty()&& referenceDetails!!.refName.isNullOrBlank()-> {
                startActivity(Intent(this, PaymentInformation::class.java).apply {
                    putExtra("EMandate", "Yes")
                })
            }


            // Step 5: IMEI Details
            imeiDetails!!.imeiNumber1.isNullOrBlank()&& productDetails.tenure!!.isNotEmpty() -> {
                startActivity(Intent(this, IMEIDetailsPage::class.java))
            }


            // Step 6: Create Loan (QR Code)
            loanData!!.loanCode.isNullOrBlank()&& productDetails.tenure!!.isNotEmpty()  -> {
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
                Toast.makeText(
                    this@CustomerListForCreatingShortCutLoanProcessPage,
                    "Please enter banificery name",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
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
                    } else {
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
                                    Toast.makeText(
                                        this@CustomerListForCreatingShortCutLoanProcessPage,
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


    private fun proceedWithLoanLogic(item: CustomerShortCutDataItem) {
        var loanData = item.createLoanDetails
        var invoiceAppVerification = item.invoiceAndAppVerification

        val isEmandateVerifiedStatus = loanData!!.isEmandateVerified.equals("yes", true)
        val isAccessKeyVerifiedStatus = invoiceAppVerification!!.isAccessKeyVerified.equals("yes", true)

        if (!isEmandateVerifiedStatus) {
            // Step 7: E-Mandate Process (After Loan Created)
            hitApiForBankList(item)

        } else if (!isAccessKeyVerifiedStatus) {
            // Step 8: App Install
            startActivity(Intent(this, AppScanInstallPage::class.java))
        }
        else {
            Toast.makeText(this, "Loan process is already completed for this customer.", Toast.LENGTH_SHORT).show()
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
                                        startActivity(
                                            Intent(
                                                this@CustomerListForCreatingShortCutLoanProcessPage,
                                                RetailerEMandateVerifyPage::class.java
                                            )
                                        )
                                    } else {
                                        isEnachCancelled = true
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT)
                                            .show()
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
                                        emIfscCode = bankData.bankIFSCCode
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
        } else {
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
                                        startActivity(
                                            Intent(
                                                this@CustomerListForCreatingShortCutLoanProcessPage,
                                                RetailerEMandateVerifyPage::class.java
                                            )
                                        )
                                    } else {
                                        isEnachCancelled = true
                                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT)
                                            .show()
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
                                        emIfscCode = bankData.bankIFSCCode
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

        val reportreq = RetailerPerCustomerListShortCutForLoanReq(
            retailerCode = retailerCode,
            searchText = searchText
        )

        Log.d("RetailerCustomerListReq", Gson().toJson(reportreq))

        viewModel.getCustomerListForShortCutLoanCreateProcess(reportreq)
            .observe(this) { resources ->
                when (resources.apiStatus) {

                    ApiStatus.SUCCESS -> {
                        ConstantClass.dialog.dismiss()
                        resources.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("RetailerCustomerListResponse", Gson().toJson(response))
                                customerList = (response.data ?: mutableListOf()) as MutableList<CustomerShortCutDataItem>

                                if (customerList.isNotEmpty()) {
                                    binding.showCustomerreports.visibility = View.VISIBLE
                                    binding.notfoundimage.visibility = View.GONE
                                    customerAdapter.updateData(customerList)

                                    if (isAutoClick && customerList.size == 1) {
                                        handleCustomerClick(customerList[0])
                                    }
                                }
                                else {
                                    binding.showCustomerreports.visibility = View.GONE
                                    binding.notfoundimage.visibility = View.VISIBLE
                                    if (isAutoClick) {
                                        Toast.makeText(this, "Customer not found", Toast.LENGTH_SHORT).show()
                                    }
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

    fun customerDataAccordingToStatus(customerList: MutableList<CustomerShortCutDataItem>){
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
