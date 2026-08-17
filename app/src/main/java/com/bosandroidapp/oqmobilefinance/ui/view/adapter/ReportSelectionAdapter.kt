package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosandroidapp.oqmobilefinance.data.model.ReportSelectionItem
import com.bosandroidapp.oqmobilefinance.databinding.ItemReportSelectionBinding

class ReportSelectionAdapter(private val items: List<ReportSelectionItem>, private val onItemClick: (ReportSelectionItem) -> Unit) : RecyclerView.Adapter<ReportSelectionAdapter.ViewHolder>() {


    class ViewHolder(val binding: ItemReportSelectionBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReportSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.ivIcon.setImageResource(item.icon)

        // Apply purple tint for specific reports: Loan Settlement (id 2) and Ledger Report (id 6)
        if (item.id == 2 || item.id == 6) {
            holder.binding.ivIcon.imageTintList = androidx.core.content.ContextCompat.getColorStateList(
                holder.itemView.context, 
                com.bosandroidapp.oqmobilefinance.R.color.purple
            )
        }
        else {
            holder.binding.ivIcon.imageTintList = null
        }
        
        holder.binding.rootLayout.setOnClickListener {
            onItemClick(item)
        }

    }

    override fun getItemCount(): Int = items.size
}
