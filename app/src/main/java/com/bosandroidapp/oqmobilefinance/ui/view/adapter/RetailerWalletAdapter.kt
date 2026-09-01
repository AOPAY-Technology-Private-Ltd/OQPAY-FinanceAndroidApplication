package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.databinding.LoandetailesItemlayoutBinding
import com.bosandroidapp.oqmobilefinance.databinding.RetailerWalletReportsBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToReport
import com.bosandroidapp.oqmobilefinance.data.model.WalletReportDataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerDataItem
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage.Companion.LoanId
import com.bosandroidapp.oqmobilefinance.ui.view.model.ColorList

class RetailerWalletAdapter (var context: Context, var retailerWalletReportList :List<WalletReportDataItem?>?): RecyclerView.Adapter<RetailerWalletAdapter.ViewHolder>() {

    lateinit var preference : SharedPreference

    class ViewHolder (private val binding: RetailerWalletReportsBinding): RecyclerView.ViewHolder(binding.root) {
        var status = binding.status
        var transactionid = binding.transactionid
        var withdrawAmount = binding.withdrawAmount
        var retailercode = binding.retailercode
        var transactiondate = binding.transactiondate
        var remarksmsg = binding.remarksmsg
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RetailerWalletReportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = retailerWalletReportList!!.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(retailerWalletReportList!![position]!!.transactionStatus!!.toLowerCase().equals("pending")){
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.orange))
        }

        if(retailerWalletReportList!![position]!!.transactionStatus!!.toLowerCase().equals("approved")){
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.green))
        }

        if(retailerWalletReportList!![position]!!.transactionStatus!!.toLowerCase().equals("rejected")){
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        preference = SharedPreference(context)
        holder.status.text = retailerWalletReportList!![position]!!.transactionStatus!!.substring(0, 1).toUpperCase()+retailerWalletReportList!![position]!!.transactionStatus!!.substring(1).toLowerCase()
        holder.transactionid.text = retailerWalletReportList!![position]!!.transactionID
        holder.withdrawAmount.text = "₹ " .plus(retailerWalletReportList!![position]!!.amount   )
        holder.retailercode.text = retailerWalletReportList!![position]!!.retailerID
        holder.transactiondate.text = formatDateToReport(retailerWalletReportList!![position]!!.transactionDate!!)
        holder.remarksmsg.text = retailerWalletReportList!![position]!!.remarks
    }



}