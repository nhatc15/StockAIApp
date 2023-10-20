package com.example.stockaiapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.stockaiapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.apply {
            tbLayout.addTab(tbLayout.newTab().setText(getString(R.string.forecast)))
            tbLayout.addTab(tbLayout.newTab().setText(getString(R.string.info)))
            tbLayout.tabGravity = TabLayout.GRAVITY_FILL

            val adapter = MyAdapter(this@MainActivity, supportFragmentManager, tbLayout.tabCount)
            viewPager.adapter = adapter

            viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tbLayout))

            tbLayout.addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        viewPager.currentItem = tab.position
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {

                    }

                    override fun onTabReselected(tab: TabLayout.Tab) {

                    }
                }
            )
        }

    }
}
