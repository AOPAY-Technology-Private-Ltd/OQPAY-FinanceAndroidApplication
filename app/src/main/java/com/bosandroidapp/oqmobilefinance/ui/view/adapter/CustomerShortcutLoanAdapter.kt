package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CusteMailID
import com.bosandroidapp.oqmobilefinance.data.enach.EMandateRequest
import com.bosandroidapp.oqmobilefinance.data.model.CustomerShortCutDataItem
import com.bosandroidapp.oqmobilefinance.data.model.CustomerStepDataItem
import com.bosandroidapp.oqmobilefinance.databinding.ItemCustomerShortcutLoanBinding
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class CustomerShortcutLoanAdapter(
    private var customerList: List<CustomerStepDataItem>,
    private val context: Context,
    private val onItemClick: (CustomerStepDataItem) -> Unit
) : RecyclerView.Adapter<CustomerShortcutLoanAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemCustomerShortcutLoanBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerShortcutLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = customerList[position]

       /* var customerDetails = item.customerDetails*/
        
        holder.binding.tvName.text = "${item!!.fullName}"
        holder.binding.tvMobile.text = "Mobile: ${item.mobileNumber}"
        holder.binding.tvCode.text = "Code: ${item.customerCode}"
        holder.binding.tvActiveStatus.text = item!!.customerStatus ?: ""
        
        Glide.with(context)
            .load(ConstantClass.BASE_URL_IMAGE+item.customerImage)
            .placeholder(R.drawable.customer)
            .error(R.drawable.customer)
            .into(holder.binding.ivCustomer)

        updateStatus(holder, item)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }


    private fun updateStatus(holder: ViewHolder, item: CustomerStepDataItem) {
        var statusText = ""
        var statusColor = R.color.orange

        when (item.currentStep){
            "0" -> {
                statusText = "Pending Customer"
                statusColor = R.color.customerpendingcolor
            }

            "1"-> {
                statusText = "Pending Product"
                statusColor = R.color.colorPrimary
            }

            "2"->{
                statusText = "Pending Bank"
                statusColor = R.color.orange
            }

            "3" -> {
                statusText = "Pending UPIMandate"
                statusColor = R.color.lightpink
            }

            "4"-> {
                statusText = "Pending Reference"
                statusColor = R.color.yellow
            }

            "5"-> {
                statusText = "Pending IMEIDetails"
                statusColor = R.color.blue
            }

            "6"->{
                statusText = "Pending Loan"
                statusColor = R.color.red
            }

            "7"->{
                statusText = "App Not Install"
                statusColor = R.color.grey
            }

            "8"->{
                statusText = "Active"
                statusColor = R.color.green
            }

        }

        holder.binding.tvStatus.text = statusText
        holder.binding.tvStatus.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, statusColor))

    }


    override fun getItemCount(): Int = customerList.size


    fun updateData(newList: List<CustomerStepDataItem>) {
        customerList = newList
        notifyDataSetChanged()
    }



}
