package com.tinkoff.ponomarev.ui.entity

import android.os.Parcelable
import com.tinkoff.ponomarev.ui.entity.SearchType.*
import kotlinx.parcelize.Parcelize

/**
 * Класс, который передается в фрагмент для определения текущего типа получаемых
 * гиф-изображение, другими словами, секцию, из которой получаем данные
 * @property RANDOM - получение случайной гифки
 * @property HOT - получение гифки из секции "Горячее"
 * @property LATEST - получение гифки из секции "Новое"
 * @property TOP - получение гифки из секции "Топ"
 */
@Parcelize
enum class SearchType(val sectionName: String) : Parcelable {
    RANDOM("random"),
    HOT("hot"),
    LATEST("latest"),
    TOP("top")
}