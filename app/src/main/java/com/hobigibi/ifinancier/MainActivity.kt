package com.hobigibi.ifinancier

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.hobigibi.ifinancier.CalculatorPage.CalculatorFragment
import com.hobigibi.ifinancier.FinancePage.FinanceFragment
import com.hobigibi.ifinancier.databinding.ActivityMainBinding
import com.hobigibi.ifinancier.datapack.binance.BinanceApiClient
import com.hobigibi.ifinancier.datapack.sqlitedb.DatabaseHelper
import com.hobigibi.ifinancier.datapack.sqlitedb.DataLists

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dataList = DataLists()
        val db = DatabaseHelper(this@MainActivity)
        val binanceApiClient = BinanceApiClient()

        dataList.moneyList.forEach { money ->
            db.createTable(money)
            dataList.symbols.forEach { symbol ->
                db.addColumn(money, symbol)
                binanceApiClient.fetchCoinPrice("$symbol$money") { price ->
                    db.editTable(money, symbol, "$price")
                }
            }
        }

        binding.manageAccountLayout.setOnClickListener(this@MainActivity)
        binding.financeLayout.setOnClickListener(this@MainActivity)
        binding.moneyboxLayout.setOnClickListener(this@MainActivity)
        binding.aiAdviceLayout.setOnClickListener(this@MainActivity)
        binding.calculatorLayout.setOnClickListener(this@MainActivity)

        fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val moneyboxFragment = MoneyboxFragment()
        fragmentTransaction.replace(binding.frameLayout.id, moneyboxFragment).commit()
    }

    override fun onClick(view: View?) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        when (view?.id) {
            binding.manageAccountLayout.id -> {
                val manageAccountFragment = ManageAccountFragment()
                fragmentTransaction.replace(binding.frameLayout.id, manageAccountFragment).commit()
            }
            binding.financeLayout.id -> {
                val financeFragment = FinanceFragment()
                fragmentTransaction.replace(binding.frameLayout.id, financeFragment).commit()
            }
            binding.moneyboxLayout.id -> {
                val moneyboxFragment = MoneyboxFragment()
                fragmentTransaction.replace(binding.frameLayout.id, moneyboxFragment).commit()
            }
            binding.aiAdviceLayout.id -> {
                val adviceFragment = AdviceFragment()
                fragmentTransaction.replace(binding.frameLayout.id, adviceFragment).commit()
            }
            binding.calculatorLayout.id -> {
                val calculatorFragment = CalculatorFragment()
                fragmentTransaction.replace(binding.frameLayout.id, calculatorFragment).commit()
            }
        }
    }
}
