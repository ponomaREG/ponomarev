package com.tinkoff.ponomarev.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tinkoff.ponomarev.ui.entity.FragmentPageFactory
import com.tinkoff.ponomarev.ui.entity.InfoOfPage
import com.tinkoff.ponomarev.ui.fragment.FragmentSectionWithGifs

/**
 * Адаптер для отображения фрагментов в ViewPager
 * @param fragmentActivity - активи с фрагментами
 */
class PagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    /**
     * Фрагменты для отображения, получаемые из [FragmentPageFactory]
     */
    private val fragments: List<InfoOfPage> = FragmentPageFactory.PAGES.toList()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val info = fragments[position]
        return FragmentSectionWithGifs.newInstance(info.type)
    }

    /**
     * Получение информации по позиции
     * Используется для получения заголовка для TabLayout
     * @param position - позиция
     * @return [InfoOfPage]
     */
    fun getInfoByPosition(position: Int): InfoOfPage? {
        return fragments.getOrNull(position)
    }
}