package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.BankDetailsCardLayoutBinding
import com.bosandroidapp.oqmobilefinance.databinding.LoandetailesItemlayoutBinding
import com.bosandroidapp.oqmobilefinance.databinding.RetailerWalletReportsBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToReport
import com.bosandroidapp.oqmobilefinance.data.model.BankDataItem
import com.bosandroidapp.oqmobilefinance.data.model.DataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerDataItem
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage.Companion.LoanId

import com.bosandroidapp.oqmobilefinance.ui.view.model.ColorList

class BankDetailsListAdapter (var context: Context, var bankdetailslist : List<BankDataItem?>?): RecyclerView.Adapter<BankDetailsListAdapter.ViewHolder>() {

    lateinit var preference : SharedPreference

    class ViewHolder (private val binding: BankDetailsCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        var accountName = binding.accountName
        var accountNumber = binding.accountNumber
        var bankName = binding.bankName
        var ifscCode = binding.ifscCode
        var branchname = binding.branchName
        var branchAddress = binding.branchAddress
        var mobileNumber = binding.mobileNumber
        var emailId = binding.emailID
        var activeStatus = binding.activeStatus
        var retailerID = binding.retailerID
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BankDetailsCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = bankdetailslist!!.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        preference = SharedPreference(context)
        bankdetailslist?.let { list->
            holder.accountName.text = "Account Name: ${list[position]!!.accountName} "
            holder.accountNumber.text = "Account number :${list[position]!!.accountNumber}"
            holder.bankName.text = "Bank : ${list[position]!!.bankName}"
            holder.branchname.text = "Branch : ${list[position]!!.branchName}"
            holder.ifscCode.text = "IFSC : ${list[position]!!.ifscCode}"
            holder.branchAddress.text = "Address : ${list[position]!!.branchAddress!!}"
            holder.mobileNumber.text = "Mobile : ${list[position]!!.mobileNumber!!}"
            holder.emailId.text = "Email Id : ${list[position]!!.emailID}"
            holder.activeStatus.text = "Status : ${list[position]!!.activeStatus}"
            holder.retailerID.text = "Retailer ID : ${list[position]!!.retailerID}"
        }


    }


}