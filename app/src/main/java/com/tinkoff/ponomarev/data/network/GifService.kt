package com.tinkoff.ponomarev.data.network

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.data.entity.network.response.GifsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GifService {

    @GET("random")
    suspend fun getRandomGif(
        @Query("json") json: Boolean = true
    ): GifBody

    @GET("{section}/{page}")
    suspend fun getGifsBySection(
        @Path("section") section: String,
        @Path("page") page: Int,
        @Query("json") json: Boolean = true
    ): GifsResponse
}