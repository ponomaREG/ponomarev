package com.tinkoff.ponomarev.ui.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class InfoOfPage(
    val resourceTitleId: Int,
    val type: SearchType
)