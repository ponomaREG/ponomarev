package com.tinkoff.ponomarev.ui.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SearchType(val sectionName: String) : Parcelable {
    RANDOM("random"),
    HOT("hot"),
    LATEST("latest"),
    TOP("top")
}