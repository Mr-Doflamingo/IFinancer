package com.hobigibi.ifinancier.datapack.binance

import com.google.gson.annotations.SerializedName

data class PriceResponse(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("price") val price: String,
)
