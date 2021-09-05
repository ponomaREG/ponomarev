package com.tinkoff.ponomarev.ui.entity

/**
 * Класс, хранящий [SearchType] тип поиска и идентификатор на строковый ресурс с заголовком
 * Используется при связывании ViewPager и TabLayout для отображения соответсвующего заголовка
 * @param resourceTitleId - идентификатор строкового ресурса заголовка
 * @param type - тип поиска
 */
data class InfoOfPage(
    val resourceTitleId: Int,
    val type: SearchType
)