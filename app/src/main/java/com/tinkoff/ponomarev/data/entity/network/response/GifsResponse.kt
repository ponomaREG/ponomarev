package com.tinkoff.ponomarev.data.entity.network.response

import com.tinkoff.ponomarev.data.entity.network.GifBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifsResponse(
    @SerialName("result") val result: List<GifBody>
)