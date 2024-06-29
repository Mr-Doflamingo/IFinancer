package com.hobigibi.ifinancier.datapack

import java.text.DecimalFormat

class PriceDataFormatter {
    fun formatCoinValue(value: String?): String {
        if (value == null) return ""

        val doubleValue = value.toDoubleOrNull() ?: return ""
        val decimalFormat = DecimalFormat("#,###.##")
        val formattedValue = decimalFormat.format(doubleValue)

        return if (formattedValue.endsWith(".00")) formattedValue.substring(0, formattedValue.length - 3)
        else formattedValue
    }
}
