package com.example.stockaiapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.stockaiapp.forecast.ForecastFragment

class MyAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ForecastFragment()
            }

            else -> {
                InfoFragment()
            }
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}
