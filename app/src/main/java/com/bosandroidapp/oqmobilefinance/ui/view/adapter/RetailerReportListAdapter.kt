package com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.RetailersReportsLayoutBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankIFSCCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CusteMailID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustomerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.EmiAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanEndDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanStartDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RetailerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToFullMonth
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage.Companion.LoanId
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage.Companion.customerCode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.QRCodePage.Companion.isEnachCancelled
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerEMandateVerifyPage.Companion.webUrl
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports.RetailerCustomerReportsPage
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import kotlin.math.roundToInt

class RetailerReportListAdapter(private val ReportsDataList: MutableList<com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.ReportsDataItem> = mutableListOf(),
                                var context: Context,
                                var loginType : String,
                                private val panViewModel: PanViewModel,
                                private val authViewModel: AuthenticationViewModel,
                                private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<RetailerReportListAdapter.ViewHolder>() {

    lateinit var preference: SharedPreference

    class ViewHolder(val binding: RetailersReportsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var productDetails = binding.productDetails
        var emiAmount = binding.emiAmount
        var loancode = binding.loanCode
        var customername = binding.customername
        var customercode = binding.customercode
        var loanamount = binding.loanamount
        var downpayment = binding.downpayment
        var tenure = binding.tenure
        var paidemi = binding.paidEmi
        var dueemi = binding.duesEmi
        var duedate = binding.duedate
        var startdate = binding.loanstartdate
        var enddate = binding.loanenddate
        var dueMob = binding.custMob
        var customerImage = binding.customerImage
        var reportstatuslayout = binding.reportstatuslayout
        var reportstatus = binding.reportstatus
        var duedatelayout = binding.duedatelayout
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RetailersReportsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }



    override fun getItemCount(): Int = ReportsDataList.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ReportsDataList[position]
        val changeStatus = item.recordStatus
        var status = ""

        preference = SharedPreference(context)
        holder.reportstatuslayout.visibility= View.VISIBLE


        if(loginType.equals(ConstantClass.Customer)){
            if(changeStatus.equals("Disbursed")){
                status = "Approved"
            }
            else{
                status = changeStatus
            }

        }
        else{
            status = changeStatus
        }


        holder.reportstatus.text = status.replaceFirstChar { it.uppercase() }


        if(status.toLowerCase().equals("approved" ,ignoreCase = true)){
            holder.reportstatuslayout.backgroundTintList= context.resources.getColorStateList(R.color.green)
        }

        if(status.toLowerCase().equals("disbursed",ignoreCase = true)){
            holder.reportstatuslayout.backgroundTintList= context.resources.getColorStateList(R.color.blue)
        }

        if(status.toLowerCase().equals("pending",ignoreCase = true)){
          holder.reportstatuslayout.backgroundTintList= context.resources.getColorStateList(R.color.orange)
        }

        if(status.toLowerCase().equals("rejected",ignoreCase = true)||status.toLowerCase().equals("disbursed rejected",ignoreCase = true)){
            holder.reportstatuslayout.backgroundTintList= context.resources.getColorStateList(R.color.red)
        }

        if(status.toLowerCase().equals("close",ignoreCase = true)){
            holder.reportstatuslayout.backgroundTintList= context.resources.getColorStateList(R.color.darkpurple)
        }

        if(status.toLowerCase().equals("settlement",ignoreCase = true)){
            holder.reportstatuslayout.backgroundTintList= context.resources.getColorStateList(R.color.darkgrey)
        }


        holder.productDetails.text = item.productDetails
        holder.emiAmount.text = "₹".plus(item.emiAmount)
        holder.loancode.text = item.loanCode
        holder.customername.text = item.customerName
        holder.customercode.text = item.customerCode
        holder.loanamount.text = "Loan amount : ₹ ".plus(item.loanAmount)
        holder.downpayment.text = "Down payment : ₹ ".plus(item.downPayment)
        holder.tenure.text = "Tenure : ".plus(item.tenure)
        holder.paidemi.text = "Paid emi : ".plus(item.paidEmi)
        holder.dueemi.text = "Due emi : ".plus(item.duesEmi)
        holder.duedate.text = safeFormatDate(item.dueDate)
        holder.startdate.text = "Start Date : ${safeFormatDate(item.startDate)}"
        holder.enddate.text = "End Date : ${safeFormatDate(item.endDate)}"
        holder.dueMob.text = item.custerMob
        Glide.with(context).load(item.customerImage).into(holder.customerImage)


        if(item.paidEmi == item.tenure){
            holder.duedatelayout.visibility = View.GONE
        }
        else {
            holder.duedatelayout.visibility = View.VISIBLE
        }


        holder.itemView.setOnClickListener {
            if (context is RetailerCustomerReportsPage) {

                val isEmandateVerified = item.isEmandateVerified
                val isPennyDropVerified = item.isPannydropVerified

                if (status.equals("Disbursed")) {
                    preference.setStringValue(ConstantClass.CustomerCode, item.customerCode)
                    LoanId = item.loanCode
                    customerCode = item.customerCode
                    context.startActivity(Intent(context, EmiLoanDetailPage::class.java))
                }

                else{
                    if(isEmandateVerified.equals("No") && isPennyDropVerified.equals("Yes")){
                        val startDate = item.startDate
                        val endDate =  item.endDate

                        val emiAmountVal = item.emiAmount.roundToInt()

                        val request = EMandateRequest(
                            categoryID = 7,
                            collectionAmount = emiAmountVal,
                            collectCollectionUntilCancle = false,
                            seqType = "RCUR",
                            iFSCCode = item.bankIFSCCode,
                            frequncy = "MNTH",
                            registrationID =  if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                                ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
                            } else {
                                ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
                            },
                            accountHolderName = item.customerName,
                            finalCollectionDate = endDate,
                            loanNo = item.loanCode,
                            accountType = item.accountType,
                            emailAddress = CusteMailID, // Or from item if available
                            firstCollectionDate = startDate,
                            mobileNumber = item.custerMob,
                            bankAccountNumberConfirmation = item.accountNumber,
                            addIn2 = BranchAddress,
                            addIn3 = "",
                            debitType = true,
                            teleNumber = "",
                            authType = "",
                            bankID = BankID,
                            bankAccountNumber = item.accountNumber
                        )
                        hitApiForEnach(request, item, true)

                    }
                }


            }

        }


    }





    fun safeFormatDate(date: String?): String {
        return if (!date.isNullOrBlank()) formatDateToFullMonth(date) else "N/A"
    }


    interface onClickListner{
        fun onClick(item: com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.ReportsDataItem )
    }


    fun hitApiForEnach(request: EMandateRequest, item: com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.ReportsDataItem, check: Boolean) {
        Log.d("eManadateReq", Gson().toJson(request))

        if(LoanMode.equals(ConstantClass.offline)) {
            panViewModel.getEMandateRequestReq(request).observe(lifecycleOwner) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    Log.d("eMandateRes", Gson().toJson(response))

                                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                        ConstantClass.dialog.dismiss()
                                    }

                                    if (response.data?.customer != null) {
                                        webUrl = response.data!!.url
                                        context.startActivity(Intent(context, RetailerEMandateVerifyPage::class.java))
                                    }
                                    else {
                                        isEnachCancelled = true
                                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                                    }

                                    var verifiedStatus = "No"
                                    if (response.data?.customer != null) {
                                        verifiedStatus = "Yes" // Or based on logic
                                    }

                                    val uploadReq = EnachDateUploadReq(
                                        isEmandateVerified = verifiedStatus,
                                        emAccountType = item.accountType,
                                        isPannydropVerified = item.isPannydropVerified,
                                        emAccountNumber = item.accountNumber,
                                        customerCode = item.customerCode,
                                        retailerCode= item.retailerCode,
                                        loanCode= item.loanCode,
                                        emBankName= item.bankName,
                                        emIfscCode = item.bankIFSCCode
                                    )

                                    hitApiForUploadEnachMandateDataResponse(uploadReq)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            Log.e("API_ERROR", "Status: ERROR ${resources.message}")
                            Toast.makeText(context, "Server error occurred", Toast.LENGTH_LONG).show()
                        }

                        ApiStatus.LOADING -> {
                            if(check){
                                ConstantClass.OpenPopUpForVeryfyOTP(context)
                            }
                        }
                    }
                }
            }
        }
        else{
            panViewModel.getEMandateOnlineRequest(request).observe(lifecycleOwner) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data?.let { users ->
                                users.body()?.let { response ->
                                    Log.d("eMandateOnlineRes", Gson().toJson(response))

                                    if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                        ConstantClass.dialog.dismiss()
                                    }

                                    if (response.data?.customer != null) {
                                        webUrl = response.data!!.url
                                        context.startActivity(Intent(context, RetailerEMandateVerifyPage::class.java))
                                    }
                                    else {
                                        isEnachCancelled = true
                                        Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
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
                                        retailerCode= item.retailerCode,
                                        loanCode= item.loanCode,
                                        emBankName= item.bankName,
                                        emIfscCode = item.bankIFSCCode
                                    )

                                    hitApiForUploadEnachMandateDataResponse(uploadReq)
                                }
                            }
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            Log.e("API_ERROR", "Status: ERROR ${resources.message}")
                            Toast.makeText(context, "Server error occurred", Toast.LENGTH_LONG).show()
                        }

                        ApiStatus.LOADING -> {
                            if(check){
                                ConstantClass.OpenPopUpForVeryfyOTP(context)
                            }
                        }
                    }
                }
            }
        }
    }

    fun hitApiForUploadEnachMandateDataResponse(request: EnachDateUploadReq) {
        authViewModel.UpdateEmandateDetails(request).observe(lifecycleOwner) { resources ->
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
}
