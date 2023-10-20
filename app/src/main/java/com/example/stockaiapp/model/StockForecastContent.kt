package com.example.stockaiapp.model

data class StockForecastContent(
    val openPrice: String,
    val closePrice: String,
    val maxPrice: String,
    val minPrice: String,
    val totalVolume: String,
    val maxInThreeMonth: String,
    val minInThreeMonth: String,
    val averageVolume: String,
    val changeValue: Int,
    val changeRate: Float,
    val date: String
)

enum class Mode {
    GET_MAX,
    GET_MIN,
    GET_TOTAL
}
