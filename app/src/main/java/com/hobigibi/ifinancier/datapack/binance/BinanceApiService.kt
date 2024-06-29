package com.hobigibi.ifinancier.datapack.binance

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceApiService {
    @GET("ticker/price")
    fun getCoinPrice(@Query("symbol") symbol: String): Call<PriceResponse>
}