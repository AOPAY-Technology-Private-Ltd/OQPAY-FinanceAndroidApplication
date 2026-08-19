package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.R
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatIndianAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.twoDecimal
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEMIDataItem
import com.bosandroidapp.oqmobilefinance.data.model.LedgerReportDataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.DataItem
import com.bosandroidapp.oqmobilefinance.databinding.CustomerEmiStatusLayoutBinding
import com.bosandroidapp.oqmobilefinance.databinding.LedgerLayoutBinding
import com.bosandroidapp.oqmobilefinance.databinding.LoanSettlementItemLayoutBinding
import com.bosandroidapp.oqmobilefinance.databinding.LoandetailesItemlayoutBinding
import com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter.CustomerEMIDetailsAdapter.ViewHolder


class CustomerEmiStatusAdapter(var context: Context, var ledgerReportList: List<CustomerEMIDataItem?>) : Adapter<CustomerEmiStatusAdapter.ViewHolder>() {


    class ViewHolder(var binding: CustomerEmiStatusLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerEmiStatusAdapter.ViewHolder {
        val binding = CustomerEmiStatusLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvReceiptNo.text = ledgerReportList[position]!!.receiptNo
        holder.binding.tvPendingAmount.text = formatIndianAmount(ledgerReportList[position]!!.pendingAmount.toString())

        if (ledgerReportList[position]!!.recordStatus!!.toLowerCase().equals("paid")) {
            holder.binding.doneimage.setImageDrawable(ContextCompat.getDrawable(context, com.bosandroidapp.oqmobilefinance.R.drawable.doneicon))
            holder.binding.amounttitle.text = context.getString(com.bosandroidapp.oqmobilefinance.R.string.paid_amount)
            holder.binding.tvRecordStatus.setTextColor(context.resources.getColor(com.bosandroidapp.oqmobilefinance.R.color.green))
        }
        else {
            holder.binding.amounttitle.text = context.getString(com.bosandroidapp.oqmobilefinance.R.string.due_amount)
            holder.binding.tvRecordStatus.setTextColor(context.resources.getColor(com.bosandroidapp.oqmobilefinance.R.color.red))
            holder.binding.doneimage.setImageDrawable(ContextCompat.getDrawable(context, com.bosandroidapp.oqmobilefinance.R.drawable.crossicon))
        }

        holder.binding.tvEmiAmount.text = formatIndianAmount(ledgerReportList[position]?.emiAmount?.toString().takeIf { !it.isNullOrBlank() } ?: "0")

        holder.binding.tvFineAmount.text = formatIndianAmount(ledgerReportList[position]?.fine?.toString().takeIf { !it.isNullOrBlank() } ?: "0")

        holder.binding.tvBounceAmount.text = formatIndianAmount(ledgerReportList[position]?.bouncingCharges?.toString().takeIf { !it.isNullOrBlank() } ?: "0")

        holder.binding.tvOtherAmount.text = formatIndianAmount(ledgerReportList[position]?.otherCharges?.toString().takeIf { !it.isNullOrBlank() } ?: "0")

        holder.binding.tvWaiveAmount.text = formatIndianAmount(ledgerReportList[position]?.waiveOffAmount?.toString().takeIf { !it.isNullOrBlank() } ?: "0")

        holder.binding.tvTotalChargesAmount.text = formatIndianAmount(ledgerReportList[position]?.totalCharges?.toString().takeIf { !it.isNullOrBlank() } ?: "0")

        holder.binding.tvPaymentMode.text = ledgerReportList[position]!!.paymentMode
        holder.binding.tvRecordStatus.text = ledgerReportList[position]!!.recordStatus
        holder.binding.tvPaymentDate.text = ledgerReportList[position]!!.paymentDate
        holder.binding.tvDueDate.text = "Due Date: ${ ledgerReportList[position]!!.emIDueDate}"

    }


    override fun getItemCount(): Int {
        return ledgerReportList.size
    }


}