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

    fun updateData(newList: List<CustomerEMIDataItem?>) {
        ledgerReportList = newList
        notifyDataSetChanged()
    }


    class ViewHolder(var binding: CustomerEmiStatusLayoutBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerEmiStatusAdapter.ViewHolder {
        val binding = CustomerEmiStatusLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ledgerReportList[position] ?: return
        val binding = holder.binding

        binding.tvReceiptNo.text = item.receiptNo
       // binding.tvPendingAmount.text = /*formatIndianAmount(*/item.pendingAmount.toString()/*)*/

        if (item.recordStatus?.toLowerCase().equals("paid", ignoreCase = true) == true) {
            binding.doneimage.setImageResource(com.bosandroidapp.oqmobilefinance.R.drawable.doneicon)
            binding.amounttitle.text = context.getString(com.bosandroidapp.oqmobilefinance.R.string.paid_amount)
            binding.tvRecordStatus.setTextColor(ContextCompat.getColor(context, com.bosandroidapp.oqmobilefinance.R.color.green))
            binding.tvPendingAmount.text = /*formatIndianAmount(*/item.emiAmount.toString()/*)*/
        }
        else {
            binding.amounttitle.text = context.getString(com.bosandroidapp.oqmobilefinance.R.string.due_amount)
            binding.tvRecordStatus.setTextColor(ContextCompat.getColor(context, com.bosandroidapp.oqmobilefinance.R.color.red))
            binding.doneimage.setImageResource(com.bosandroidapp.oqmobilefinance.R.drawable.crossicon)
            binding.tvPendingAmount.text = /*formatIndianAmount(*/item.pendingAmount.toString()/*)*/
        }

        binding.tvEmiAmount.text = /*formatIndianAmount(*/item.emiAmount?.toString().orEmpty()/*)*/
        binding.tvFineAmount.text = /*formatIndianAmount(*/item.fine?.toString().orEmpty()/*)*/
        binding.tvBounceAmount.text = /*formatIndianAmount(*/item.bouncingCharges?.toString().orEmpty()/*)*/
        binding.tvOtherAmount.text = /*formatIndianAmount(*/item.otherCharges?.toString().orEmpty()/*)*/
        binding.tvWaiveAmount.text = /*formatIndianAmount(*/item.waiveOffAmount?.toString().orEmpty()/*)*/
        binding.tvTotalChargesAmount.text = /*formatIndianAmount(*/item.totalCharges?.toString().orEmpty()/*)*/

        binding.tvPaymentMode.text = item.paymentMode
        binding.tvRecordStatus.text = item.recordStatus
        binding.tvPaymentDate.text = item.paymentDate
        binding.tvDueDate.text = "Due Date: ${item.emIDueDate}"
    }


    override fun getItemCount(): Int {
        return ledgerReportList.size
    }


}