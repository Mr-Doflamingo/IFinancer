package com.hobigibi.ifinancier.FinancePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hobigibi.ifinancier.datapack.binance.PriceResponse
import com.hobigibi.ifinancier.databinding.FragmentFinanceBinding
import com.hobigibi.ifinancier.datapack.PriceDataFormatter
import com.hobigibi.ifinancier.datapack.sqlitedb.DataLists
import com.hobigibi.ifinancier.datapack.sqlitedb.DatabaseHelper

class FinanceFragment : Fragment() {

    private lateinit var binding: FragmentFinanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priceResponseArray = ArrayList<PriceResponse>()
        val moneyRecycvArray = ArrayList<String>()
        val databaseHelper = DatabaseHelper(requireContext())
        val dataList = DataLists()
        val formatValue = PriceDataFormatter()

        dataList.moneyList.forEach {money ->
            moneyRecycvArray.add(money)
        }

        val moneyAdapter = MoneyRecycv(moneyRecycvArray)
        binding.money.layoutManager = LinearLayoutManager(context)
        binding.money.adapter = moneyAdapter

        priceResponseArray.clear()
        dataList.symbols.forEach { symbol ->
            val formatPrice = formatValue.formatCoinValue(databaseHelper.getData("TRY", symbol))
            priceResponseArray.add(PriceResponse(symbol, "$formatPrice TRY"))
        }

        val coinAdapter = CoinAdapter(priceResponseArray)
        binding.binance.layoutManager = LinearLayoutManager(context)
        binding.binance.adapter = coinAdapter

        moneyAdapter.setOnItemClickListener(object : MoneyRecycv.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedMoney = dataList.moneyList[position]
                priceResponseArray.clear()
                dataList.symbols.forEach { symbol ->
                    val formatPrice = formatValue.formatCoinValue(databaseHelper.getData(selectedMoney, symbol))
                    priceResponseArray.add(PriceResponse(symbol, "$formatPrice $selectedMoney"))
                }
                coinAdapter.updateData(priceResponseArray)
            }
        })

    }
}
