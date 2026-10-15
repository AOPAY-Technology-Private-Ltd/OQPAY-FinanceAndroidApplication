package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bosandroidapp.oqmobilefinance.data.model.EmandateSelectDataItem
import com.bosandroidapp.oqmobilefinance.databinding.EmandateOptionItemLayoutBinding

class EmandateOptionAdapter(
    private val options: MutableList<Pair<String, List<EmandateSelectDataItem>>?>,
    private val onOptionSelected: (Pair<String, EmandateSelectDataItem>) -> Unit
) : RecyclerView.Adapter<EmandateOptionAdapter.ViewHolder>() {

    private var selectedPosition = -1

    class ViewHolder(val binding: EmandateOptionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EmandateOptionItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = options[0]!!.second.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[0]!!.second[position]
        holder.binding.radiobutton.text = "${option?.vender} ${option?.apiName}"
        holder.binding.radiobutton.isChecked = position == selectedPosition

        holder.binding.radiobutton.setOnClickListener {
            if (selectedPosition != holder.adapterPosition) {
                val lastSelected = selectedPosition
                selectedPosition = holder.adapterPosition
                notifyItemChanged(lastSelected)
                notifyItemChanged(selectedPosition)
                onOptionSelected(Pair(options[0]!!.first, option))
            }
        }
    }

    fun selectFirstOption() {
        if (options.isNotEmpty() && selectedPosition == -1) {
            val list = options[0]?.second
            if (!list.isNullOrEmpty()) {
                selectedPosition = 0
                notifyItemChanged(0)
                onOptionSelected(Pair(options[0]!!.first, list[0]))
            }
        }
    }

}
