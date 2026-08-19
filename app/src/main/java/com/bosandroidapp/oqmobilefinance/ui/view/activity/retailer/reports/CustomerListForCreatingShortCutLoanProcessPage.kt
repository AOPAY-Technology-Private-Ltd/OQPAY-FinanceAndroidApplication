package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPinCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryOTP
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustStateName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CusteMailID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustomerCodeForEnach
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.SellingPrice
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Tenure
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.UPIMandate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAccessKeyVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isEmandateVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isPannydropVerified
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.CustomerListDataItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.ManageCustomerStepWiseReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.PanViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerListForCreatingShortCutLoanProcessPageBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MobileSelectionActivity
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.PaymentInformation
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.QRCodePage.Companion.isEnachCancelled
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage.Companion.webUrl
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.CustomerShortcutLoanAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import kotlin.math.roundToInt

class CustomerListForCreatingShortCutLoanProcessPage : AppCompatActivity() {

    private lateinit var preference : SharedPreference
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var panViewModel: PanViewModel
    private var customerList : List<CustomerListDataItem> = mutableListOf()
    private lateinit var customerAdapter: CustomerShortcutLoanAdapter
    private lateinit var binding : ActivityCustomerListForCreatingShortCutLoanProcessPageBinding
    lateinit var dialog : Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerListForCreatingShortCutLoanProcessPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        panViewModel = ViewModelProvider(this, PanViewModelFactory(PanRepository(RetrofitClient.apiInterfacePAN)))[PanViewModel::class.java]
        preference = SharedPreference(this)

        setupRecyclerView()
        setview()

        binding.back.setOnClickListener {
            finish()
        }
    }
    

    fun setview(){
        val adapter = ArrayAdapter.createFromResource(this,  R.array.customershortcutlist, R.layout.mobilenamelayout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.reporttype.adapter = adapter

        binding.reporttype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                hitApiForGetReports(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }
        }


    }


    private fun setupRecyclerView() {

        customerAdapter = CustomerShortcutLoanAdapter(customerList, this) { item ->
            // Handle item click if needed
            CustFirstName = item.firstName!!
            CustMiddleName= item.middleName!!
            CustLastName = item.lastName!!
            CustPrimaryMobileNumber= item.primaryMobileNumber!!
            CustPrimaryOTP=item.primaryOTP!!
            CustPrimaryMobileVerified=item.primaryMobileVerified!!
            CustAlternateMobileNumber=item.alternateMobileNumber!!
            CusteMailID=item.eMailID!!
            CustFlatNo=item.flatNo!!
            CustAreaSector=item.aearSector!!
            CustPinCode=item.pinCode!!
            CustCurrentAddress=item.currentAddress!!
            CustStateName=item.stateName!!
            CustCityName=item.cityName!!
            CustCountry=item.country!!
            AadharNumber=item.aadharNumber!!
            ConstantClass.AadharVerified= item.aadharNumberVerified!!
            PanNumber=item.panNumber!!
            PanNumberVerified=item.panNumberVerified!!
            CreatedByCustomerShortCut=item.createdBy!!
            userScore = item.cibilScore?.trim()?.toFloatOrNull() ?: 0f
            isAggrementVerified=item.isAggrementVerified!!
            UPIMandate = item.upiMandate!!
            ConstantClass.CustomerActiveStatus = item.activeStatus!!
            BrandName=item.brandName!!
            ModelName = item.modelName!!
            ModelVarient = item.modelVariant!!
            ModelColor = item.color!!
            SellingPrice = item.sellingPrice!!
            DownPayment = item.downPayment!!
            Tenure = item.tenure!!
            EmiAmount = item.emiAmount!!
            ImeiNumber1 = item.imeiNumber1!!
            ImeiNumber2 = item.imeiNumber2!!
            AccountNumber = item.accountNumber!!
            BankIFSCCode = item.bankIFSCCode!!
            BankName = item.bankName!!
            AccountType= item.accountNumber!!
            BranchName = item.branchName!!
            // BranchAddress= item.branchAddress!!
            RefName = item.refName!!
            RefRelationShip = item.retailerCode!!
            RefmobileNo = item.refmobileNo!!
            RefAddress = item.refAddress!!
            PanResponse = item.panApiResponse!!
            AadhaarResponse = item.aadhaarApiResponse!!
            CibilResponse = item.cibilApiResponse!!
            // LoanMode = item.loanMode!!
            IsRetailerAggrementVerified = item.isRetailerAggrementVerified!!
            ReferenceAadharVerified = item.isrefKycVerified!!
            ReferenceAadharNumber = item.refAdhaarNumber!!
            //  ConstantClass.LoanAmount= item.
            // InterestRate = item.interestRate!!
            //ProcessingFees = item.processingFees!!
            // InterestAmt = item.interestAmt!!
            // LoanStatus =
            // LoanRID = item.loanRID!!
            CustomerCodeForEnach = item.customerCode!!
            // ConstantClass.DefaultEmidebit =
            // ConstantClass.BankID =
            // LoanStartDate= item.startDate!!
            // LoanEndDate = item.endDate!!
            isEmandateVerified = item.isEmandateVerified!!
            isPannydropVerified= item .isPannydropVerified!!
            // isAccessKeyVerified =
            preference.setStringValue(ConstantClass.CustomerCode,CustomerCodeForEnach)
            if(item.loanCode.isNullOrBlank()){
                showOnlineOfflineDialog(item)
            }else{
                 proceedWithLoanLogic(item)
            }

        }
        binding.showCustomerreports.layoutManager = LinearLayoutManager(this)
        binding.showCustomerreports.adapter = customerAdapter
    }


    private fun showOnlineOfflineDialog(item: CustomerListDataItem) {
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

            if (selectedId == R.id.rbOnline) {
                ConstantClass.CheckOnlineOrOffline = ConstantClass.online
            } else {
                ConstantClass.CheckOnlineOrOffline = ConstantClass.offline
            }
            dialog.dismiss()
            startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, MobileSelectionActivity::class.java))

        }

        dialog.show()
    }


    private fun proceedWithLoanLogic(item: CustomerListDataItem) {

        if(item.isPannydropVerified.isNullOrBlank()||item.isPannydropVerified.lowercase().equals("no",ignoreCase = true)){
            startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, PaymentInformation::class.java))
        }


        /*if(item.isEmandateVerified.isNullOrBlank()||item.isEmandateVerified.lowercase().equals("no",ignoreCase = true)){
            val startDate = LoanStartDate
            val endDate = LoanEndDate
            val emiAmountVal = item.emiAmount?.toDoubleOrNull()?.roundToInt() ?: 0

            val request = EMandateRequest(
                categoryID = 7,
                collectionAmount = emiAmountVal,
                collectCollectionUntilCancle = false,
                seqType = "RCUR",
                iFSCCode = item.bankIFSCCode,
                frequncy = "MNTH",
                registrationID = if (LoanMode == ConstantClass.online) {
                    ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
                } else {
                    ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
                },
                accountHolderName = "${item.firstName} ${item.lastName}",
                finalCollectionDate = endDate,
                loanNo = item.loanCode,
                accountType = item.accountType,
                emailAddress = item.eMailID ?: CusteMailID,
                firstCollectionDate = startDate,
                mobileNumber = item.primaryMobileNumber,
                bankAccountNumberConfirmation = item.accountNumber,
                addIn2 = BranchAddress,
                addIn3 = "",
                debitType = true,
                teleNumber = "",
                authType = "",
                bankID = item.BankID,
                bankAccountNumber = item.accountNumber
            )
            hitApiForEnach(request, item, true)

        }*/


        if(isAccessKeyVerified.isNullOrBlank() || isAccessKeyVerified.lowercase().equals("no",ignoreCase = true)){
            startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, AppScanInstallPage::class.java))
        }


    }


    fun hitApiForEnach(request: EMandateRequest, item: CustomerListDataItem, check: Boolean) {
        Log.d("eManadateReq", Gson().toJson(request))

        if (LoanMode == ConstantClass.offline) {
            panViewModel.getEMandateRequestReq(request).observe(this) { resources ->
                when (resources.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        ConstantClass.dialog.dismiss()
                        resources.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("eMandateRes", Gson().toJson(response))

                                if (response.data?.customer != null) {
                                    webUrl = response.data.url
                                    startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, RetailerEMandateVerifyPage::class.java))
                                } else {
                                    isEnachCancelled = true
                                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                }

                                var verifiedStatus = "No"

                                if (response.data?.customer != null) {
                                    verifiedStatus = "Yes"
                                }

                                val uploadReq = EnachDateUploadReq(
                                    isEmandateVerified = verifiedStatus,
                                    emAccountType = item.accountType,
                                    isPannydropVerified = item.isPannydropVerified,
                                    emAccountNumber = item.accountNumber,
                                    customerCode = item.customerCode,
                                    retailerCode = item.retailerCode,
                                    loanCode = item.loanCode,
                                    emBankName = item.bankName,
                                    emIfscCode = item.bankIFSCCode
                                )

                                hitApiForUploadEnachMandateDataResponse(uploadReq)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this, resources.message ?: "Server error occurred", Toast.LENGTH_LONG).show()
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
            panViewModel.getEMandateOnlineRequest(request).observe(this) { resources ->
                when (resources.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        ConstantClass.dialog.dismiss()
                        resources.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("eMandateOnlineRes", Gson().toJson(response))

                                if (response.data?.customer != null) {
                                    webUrl = response.data.url
                                    startActivity(Intent(this@CustomerListForCreatingShortCutLoanProcessPage, RetailerEMandateVerifyPage::class.java))
                                } else {
                                    isEnachCancelled = true
                                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                                }

                                var verifiedStatus = "No"
                                if (response.data?.customer != null) {
                                    verifiedStatus = "Yes"
                                }

                                val uploadReq = EnachDateUploadReq(
                                    isEmandateVerified = verifiedStatus,
                                    emAccountType = item.accountType,
                                    isPannydropVerified = item.isPannydropVerified,
                                    emAccountNumber = item.accountNumber,
                                    customerCode = item.customerCode,
                                    retailerCode = item.retailerCode,
                                    loanCode = item.loanCode,
                                    emBankName = item.bankName,
                                    emIfscCode = item.bankIFSCCode
                                )

                                hitApiForUploadEnachMandateDataResponse(uploadReq)
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this, resources.message ?: "Server error occurred", Toast.LENGTH_LONG).show()
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


    fun hitApiForGetReports(reporttype: String) {
        val retailerCode = preference.getStringValue(ConstantClass.RetailerCode, "")
        
        val reportreq = RetailerPerCustomerListShortCutForLoanReq(
            retailerCode = retailerCode,
            searchText = "",
            recordStatus = reporttype
        )

        Log.d("RetailerCustomerListReq", Gson().toJson(reportreq))

        viewModel.getCustomerListForShortCutLoanCreateProcess(reportreq).observe(this) { resources ->
            when (resources.apiStatus) {

                ApiStatus.SUCCESS -> {
                    ConstantClass.dialog.dismiss()
                    resources.data?.let { users ->
                        users.body()?.let { response ->
                            Log.d("RetailerCustomerListResponse", Gson().toJson(response))
                            customerList = (response.data ?: mutableListOf()) as List<CustomerListDataItem>

                            if (customerList.isNotEmpty()) {
                                binding.showCustomerreports.visibility = View.VISIBLE
                                binding.notfoundimage.visibility = View.GONE
                                customerAdapter.updateData(customerList)
                            } else {
                                binding.showCustomerreports.visibility = View.GONE
                                binding.notfoundimage.visibility = View.VISIBLE
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

}
