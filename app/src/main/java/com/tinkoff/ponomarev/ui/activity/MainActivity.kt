package com.tinkoff.ponomarev.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tinkoff.ponomarev.R
import com.tinkoff.ponomarev.databinding.ActivityMainBinding
import com.tinkoff.ponomarev.ui.adapter.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Главное активити для отображения ViewPager с фрагментами
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Вьюбиндинг [R.layout.activity_main]
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Адаптер для ViewPager для отображения фрагментов по секции
     */
    private lateinit var viewPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAdapters()
        setupActionBar()
        attachAdapter()
        attachTabLayoutToViewPager()
    }

    /**
     * Установка ToolBar как ActionBar
     */
    private fun setupActionBar() {
        setSupportActionBar(binding.mainToolBar)
    }

    /**
     * Иницилизация адаптера
     */
    private fun initAdapters() {
        viewPagerAdapter = PagerAdapter(this)
    }

    /**
     * Связывание адаптера и ViewPager
     */
    private fun attachAdapter() {
        binding.mainViewPager.adapter = viewPagerAdapter
    }

    /**
     * Связывание TabLayout и ViewPager при помощи [TabLayoutMediator]
     */
    private fun attachTabLayoutToViewPager() {
        TabLayoutMediator(
            binding.mainTabs,
            binding.mainViewPager,
            true,
            true
        ) { tab: TabLayout.Tab, position: Int ->
            viewPagerAdapter.getInfoByPosition(position)?.let { info ->
                tab.text = getString(info.resourceTitleId)
                binding.mainViewPager.setCurrentItem(tab.position, true)
            }
        }.attach()
    }
}