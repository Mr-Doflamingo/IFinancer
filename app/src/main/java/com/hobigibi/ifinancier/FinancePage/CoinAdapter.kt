package com.hobigibi.ifinancier.FinancePage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hobigibi.ifinancier.databinding.FinanceRecycvAdapterBinding
import com.hobigibi.ifinancier.datapack.binance.PriceResponse

class CoinAdapter(private var data: ArrayList<PriceResponse>) : RecyclerView.Adapter<CoinHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHolder {
        val binding = FinanceRecycvAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CoinHolder, position: Int) {
        holder.binding.coinName.text = data[position].symbol
        holder.binding.coinPrice.text = data[position].price
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: ArrayList<PriceResponse>) {
        data = newData
        notifyDataSetChanged()
    }

}
