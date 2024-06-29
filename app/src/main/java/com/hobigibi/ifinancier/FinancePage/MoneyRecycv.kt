package com.hobigibi.ifinancier.FinancePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hobigibi.ifinancier.databinding.MoneyRecycvAdapterBinding

class MoneyRecycv(private var data: ArrayList<String>) : RecyclerView.Adapter<MoneyHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyHolder {
        val binding = MoneyRecycvAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoneyHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MoneyHolder, position: Int) {
        holder.binding.coinName.text = data[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener { fun onItemClick(position: Int) }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}

class MoneyHolder(val binding: MoneyRecycvAdapterBinding) : RecyclerView.ViewHolder(binding.root)