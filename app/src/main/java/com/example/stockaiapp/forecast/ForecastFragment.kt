package com.example.stockaiapp.forecast

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stockaiapp.R
import com.example.stockaiapp.databinding.FragmentForecastBinding
import com.example.stockaiapp.model.Company
import com.example.stockaiapp.model.Mode
import com.example.stockaiapp.model.StockForecastContent
import com.example.stockaiapp.model.StockInfo
import com.example.stockaiapp.util.hideKeyboard
import com.example.stockaiapp.util.Const
import com.example.stockaiapp.util.readJSONFromAssets
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.abs

class ForecastFragment : Fragment() {
    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ForecastViewModel
    private lateinit var companyData: Company

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ForecastViewModel::class.java]

        viewModel.company.observe(viewLifecycleOwner) {
            it?.let {
                binding.apply {
                    tvStockCodeInfoContent.text = it.info
                    val stockInfoJsonString: String? =
                        readJSONFromAssets(requireContext(), "data/data_${it.ticker}.json")
                    viewModel.getStockInfo(stockInfoJsonString)
                }
            }
        }

        viewModel.stockInfo.observe(viewLifecycleOwner) { stockInfo ->
            binding.apply {
                val content = getStockForecastContent(stockInfo)
                val textColor = when {
                    content.changeValue > 0 -> Color.GREEN
                    content.changeValue < 0 -> Color.RED
                    else -> Color.rgb(255, 215, 0)
                }
                tvLastPrice.apply {
                    text = content.closePrice
                    setTextColor(textColor)
                }
                tvProfitabilityRate.apply {
                    text = String.format(
                        "%s(%.2f)",
                        abs(content.changeValue).toString().formatValueString(),
                        abs(content.changeRate)
                    ) + "%"
                    setTextColor(textColor)
                }
                tvLastDay.text = content.date
                tvOpenValue.apply {
                    text = content.openPrice
                    setTextColor(textColor)
                }
                tvCloseValue.apply {
                    text = content.closePrice
                    setTextColor(textColor)
                }
                tvMaxValue.apply {
                    text = content.maxPrice
                    setTextColor(textColor)
                }
                tvMinValue.apply {
                    text = content.minPrice
                    setTextColor(textColor)
                }
                tvLastDay.text = content.date
                tvLastTimeUpdated.text = Const.UPDATE_LAST_TIME + content.date
                tvMaxInThreeMonthValue.text = content.maxInThreeMonth
                tvMinInThreeMonthValue.text = content.minInThreeMonth
                tvWeightValue.text = content.totalVolume
                tvAverageVolumeValue.text = content.averageVolume
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val companyJsonString = readJSONFromAssets(requireContext(), Const.COMPANY_JSON_FILE_NAME)
        companyData = Gson().fromJson(companyJsonString, Company::class.java)

        viewModel.updateStockCode(companyData.first())
        binding.apply {
            autoCompleteTextView.apply {
                setText(getString(R.string.stock_code, companyData.first().ticker))
                setOnItemClickListener { parent, view, position, id ->
                    viewModel.updateStockCode(companyData[position])
                    autoCompleteTextView.clearFocus()
                    this@ForecastFragment.hideKeyboard()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, companyData.map { it.ticker })
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getValue(dataList: List<Int>, mode: Mode): Int = runBlocking {
        val chunkSize = 10
        var totalVolume = 0
        val values = mutableListOf<Int>()
        val chunks = dataList.chunked(chunkSize)
        val jobs = mutableListOf<Job>()
        for (chunk in chunks) {
            jobs += launch {
                when (mode) {
                    Mode.GET_MAX -> {
                        val maxValue = chunk.maxOrNull() ?: 0
                        values.add(maxValue)
                    }

                    Mode.GET_MIN -> {
                        val minValue = chunk.minOrNull() ?: 0
                        values.add(minValue)
                    }

                    Mode.GET_TOTAL -> {
                        chunk.forEach {
                            totalVolume = it
                        }
                    }
                }
            }
        }
        jobs.joinAll()
        return@runBlocking when (mode) {
            Mode.GET_MAX -> values.maxOrNull<Int>() ?: 0
            Mode.GET_MIN -> values.minOrNull<Int>() ?: 0
            Mode.GET_TOTAL -> totalVolume
        }
    }

    private fun getStockForecastContent(stockInfo: StockInfo?): StockForecastContent {
        val stockInfoInThreeMonth = stockInfo?.takeLast(90)

        return StockForecastContent(
            openPrice = stockInfo?.last()?.Open?.toString()?.formatValueString() ?: "",
            closePrice = stockInfo?.last()?.Close?.toString()?.formatValueString() ?: "",
            maxPrice = stockInfo?.last()?.High?.toString()?.formatValueString() ?: "",
            minPrice = stockInfo?.last()?.Low?.toString()?.formatValueString() ?: "",
            date = stockInfo?.last()?.Date ?: "",
            maxInThreeMonth = stockInfoInThreeMonth?.let { data ->
                getValue(data.map { it.High }, Mode.GET_MAX)
                    .toString()
            }?.formatValueString() ?: "",
            minInThreeMonth = stockInfoInThreeMonth?.let { data ->
                getValue(
                    data.map { it.Low },
                    Mode.GET_MIN
                ).toString()
            }?.formatValueString() ?: "",
            totalVolume = stockInfoInThreeMonth?.let { data ->
                getValue(
                    data.map { it.Volume },
                    Mode.GET_TOTAL
                ).toString()
            }?.formatValueString() ?: "",
            averageVolume = stockInfoInThreeMonth?.let { data ->
                (getValue(
                    data.map { it.Volume },
                    Mode.GET_TOTAL
                ) / 90).toString()
            }?.formatValueString() ?: "",
            changeValue = stockInfo?.let { (it.last().Close - it[it.size - 2].Close) } ?: 0,
            changeRate = stockInfo?.let { ((it.last().Close - it[it.size - 2].Close).toFloat() / it[it.size - 2].Close) * 100 }
                ?: 0f
        )
    }

    private fun String.formatValueString(): String {
        val reversed = this.reversed()
        val stringBuilder = StringBuilder()
        for (i in reversed.indices) {
            if (i != 0 && i % 3 == 0) {
                stringBuilder.append('.')
            }
            stringBuilder.append(reversed[i])
        }
        return stringBuilder.reverse().toString()
    }
}
