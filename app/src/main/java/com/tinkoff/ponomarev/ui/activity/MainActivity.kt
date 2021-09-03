package com.tinkoff.ponomarev.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tinkoff.ponomarev.R
import com.tinkoff.ponomarev.databinding.ActivityMainBinding
import com.tinkoff.ponomarev.ui.adapter.PagerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAdapters()
        attachTabLayoutToViewPager()
    }

    private fun initAdapters(){
        viewPagerAdapter = PagerAdapter(this)
    }

    private fun attachTabLayoutToViewPager(){
        TabLayoutMediator(
            binding.mainTabs,
            binding.mainViewPager,
            true,
            true
        ){ tab: TabLayout.Tab, position: Int ->
            viewPagerAdapter.getInfoByPosition(position)?.let { info ->
                tab.text = getString(info.resourceTitleId)
                binding.mainViewPager.setCurrentItem(tab.position, true)
            }
        }
    }
}