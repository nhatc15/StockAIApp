package com.example.stockaiapp.model

data class StockInfoItem(
    val Close: Int,
    val Date: String,
    val Difference: Int,
    val High: Int,
    val Low: Int,
    val Open: Int,
    val Volume: Int,
    val ticker: String
)
