package com.example.stockaiapp.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockaiapp.model.CompanyItem
import com.example.stockaiapp.model.StockInfo
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ForecastViewModel : ViewModel() {

    private val _company = MutableLiveData<CompanyItem?>()
    val company: LiveData<CompanyItem?> = _company

    private val _stockInfo = MutableLiveData<StockInfo?>()
    val stockInfo: LiveData<StockInfo?> = _stockInfo

    fun updateStockCode(companyItem: CompanyItem) {
        viewModelScope.launch {
            _company.value = companyItem
        }
    }

    fun getStockInfo(stockInfoJsonString: String?) {
        viewModelScope.launch {
            _stockInfo.value =
                stockInfoJsonString?.let {
                    Gson().fromJson(
                        stockInfoJsonString,
                        StockInfo::class.java
                    )
                }
        }
    }
}
