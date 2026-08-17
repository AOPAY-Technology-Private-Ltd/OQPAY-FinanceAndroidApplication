package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.data.model.CustomerListDataItem
import com.bosandroidapp.oqmobilefinance.databinding.ItemCustomerShortcutLoanBinding
import com.bumptech.glide.Glide

class CustomerShortcutLoanAdapter(
    private var customerList: List<CustomerListDataItem>,
    private val context: Context,
    private val onItemClick: (CustomerListDataItem) -> Unit
) : RecyclerView.Adapter<CustomerShortcutLoanAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemCustomerShortcutLoanBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerShortcutLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = customerList[position]
        
        holder.binding.tvName.text = "${item.firstName} ${item.lastName}"
        holder.binding.tvMobile.text = "Mobile: ${item.primaryMobileNumber}"
        holder.binding.tvCode.text = "Code: ${item.customerCode}"
        holder.binding.tvActiveStatus.text = item.activeStatus ?: ""
        
        Glide.with(context)
            .load(item.custPhotoPath)
            .placeholder(R.drawable.userimagee)
            .error(R.drawable.userimagee)
            .into(holder.binding.ivCustomer)

        updateStatus(holder, item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }


    private fun updateStatus(holder: ViewHolder, item: CustomerListDataItem) {
        var statusText = ""
        var statusColor = R.color.orange

        when {
            item.loanCode.isNullOrBlank() -> {
                statusText = "Pending Loan"
                statusColor = R.color.red
            }
            item.isPannydropVerified.isNullOrBlank()|| item.isPannydropVerified.equals("false", true) -> {
                statusText = "Pennydrop Pending"
                statusColor = R.color.orange
            }
            item.isEmandateVerified.isNullOrBlank() || item.isEmandateVerified.equals("false", true) -> {
                statusText = "Emandate Pending"
                statusColor = R.color.blue
            }
            item.isrefKycVerified.isNullOrBlank() || item.isrefKycVerified.equals("no", true) || item.isrefKycVerified.equals("false", true) -> {
                statusText = "App Not Install"
                statusColor = R.color.grey
            }
            else -> {
                statusText = "Active"
                statusColor = R.color.green
            }

        }

        holder.binding.tvStatus.text = statusText
        holder.binding.tvStatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, statusColor))
    }


    override fun getItemCount(): Int = customerList.size


    fun updateData(newList: List<CustomerListDataItem>) {
        customerList = newList
        notifyDataSetChanged()
    }

}
