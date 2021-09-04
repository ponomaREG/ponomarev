package com.tinkoff.ponomarev.ui.entity

import com.tinkoff.ponomarev.R
import com.tinkoff.ponomarev.ui.entity.FragmentPageFactory.Companion.PAGES

/**
 * Класс-контейнер, хранящий [InfoOfPage] информацию для отображения страниц для отображения
 * гиф-изображений
 * @property PAGES - страницы для отображения
 */
class FragmentPageFactory {
    companion object {
        val PAGES: Set<InfoOfPage> = setOf(
            InfoOfPage(R.string.section_random, SearchType.RANDOM),
            InfoOfPage(R.string.section_latest, SearchType.LATEST),
            InfoOfPage(R.string.section_top, SearchType.TOP),
            InfoOfPage(R.string.section_hottest, SearchType.HOT),
        )
    }
}