package com.hobigibi.ifinancier.datapack.binance

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BinanceApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.binance.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun fetchCoinPrice(symbol: String, callback: (Double?) -> Unit) {
        val service = retrofit.create(BinanceApiService::class.java)
        val call = service.getCoinPrice(symbol)
        call.enqueue(object : Callback<PriceResponse> {
            override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
                if (response.isSuccessful) {
                    val priceResponse = response.body()
                    val price = priceResponse?.price?.toDoubleOrNull()
                    callback(price)
                } else {
                    println("Failed to retrieve price for $symbol: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
                println("Failed to execute request: ${t.message}")
                callback(null)
            }
        })
    }
}