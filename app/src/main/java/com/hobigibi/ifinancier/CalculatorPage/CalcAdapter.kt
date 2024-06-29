package com.hobigibi.ifinancier.CalculatorPage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hobigibi.ifinancier.databinding.CalculatorRecadapterBinding
import com.hobigibi.ifinancier.databinding.FragmentCalculatorBinding
import com.hobigibi.ifinancier.datapack.binance.PriceResponse

class CalcAdapter ( private var data: ArrayList<PriceResponse> , private val fragment: Fragment)  : RecyclerView.Adapter<CalcCoin>() {

    var total : Double= 0.00;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalcCoin {
        val binding = CalculatorRecadapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalcCoin(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CalcCoin, position: Int) {
        holder.binding.coinName.text = data[position].symbol
        holder.binding.coinPrice.text = data[position].price
        holder.binding.number.setText("0");

        holder.binding.addButton.setOnClickListener {
            if(myRegex(data[position].price) != 0.0) {
                val number = holder.binding.number.text.toString().toInt()
                val newnumber = number + 1
                holder.binding.number.text = newnumber.toString()
                total += myRegex(data[position].price)
                total = String.format("%.2f", total).toDouble()
                TextFragment();
            }
        }

        holder.binding.decreaseButton.setOnClickListener {
            if(myRegex(data[position].price) != 0.0) {
                val number = holder.binding.number.text.toString().toInt()
                if(number !=0) {
                    val newnumber = number - 1
                    holder.binding.number.text = newnumber.toString()
                    total -= myRegex(data[position].price)
                }
                total = String.format("%.2f", total).toDouble()
                TextFragment();
            }
        }

    }

        @SuppressLint("NotifyDataSetChanged")
        fun updateData(newData: ArrayList<PriceResponse>) {
            data = newData
            total = 0.00
            notifyDataSetChanged()
            TextFragment()
        }

    private fun TextFragment(){
        val fragmentBinding = FragmentCalculatorBinding.bind(fragment.requireView())
        fragmentBinding.calctext.text = total.toString()

    }

    private fun myRegex(value: String): Double {
        val regex = Regex("[^0-9.,]+")
        val result = regex.replace(value, "")
        val newResult = result.replace(",", "")
        return String.format("%.2f", newResult.toDoubleOrNull() ?: 0.0).toDouble()
    }
}
