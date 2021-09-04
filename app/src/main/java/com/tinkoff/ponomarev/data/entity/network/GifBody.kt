package com.tinkoff.ponomarev.data.entity.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifBody(
    @SerialName("id") val id:Int,
    @SerialName("description") val description: String,
    @SerialName("author") val author: String,
    @SerialName("gifURL") val gifURL: String?
)
