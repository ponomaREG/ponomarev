package com.tinkoff.ponomarev.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tinkoff.ponomarev.ui.entity.FragmentPageFactory
import com.tinkoff.ponomarev.ui.entity.InfoOfPage
import com.tinkoff.ponomarev.ui.fragment.FragmentSectionWithGifs

class PagerAdapter(
    fragmentActivity: FragmentActivity
    ): FragmentStateAdapter(fragmentActivity) {
    private val fragments: List<InfoOfPage> = FragmentPageFactory.PAGES.toList() //TODO: заменить

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val info = fragments[position]
        return FragmentSectionWithGifs.newInstance(info.type)
    }

    fun getInfoByPosition(position: Int): InfoOfPage?{
        return fragments.getOrNull(position)
    }
}