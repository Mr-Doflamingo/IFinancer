package com.hobigibi.ifinancier.datapack.sqlitedb

data class DataLists (
    val moneyList: List<String> = listOf("TRY", "USDT"),
    val symbols: List<String> = listOf("BTC", "ETH", "BNB")
)