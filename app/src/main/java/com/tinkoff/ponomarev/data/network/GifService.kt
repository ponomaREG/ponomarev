package com.tinkoff.ponomarev.data.network

import com.tinkoff.ponomarev.data.entity.network.GifBody
import com.tinkoff.ponomarev.data.entity.network.response.GifsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GifService {

    @GET("random")
    fun getRandomGif(
        @Query("json") json: Boolean = true
    ): GifBody

    @GET("latest/{page}")
    fun getLatestGifs(
        @Path("page") page: Int,
        @Query("json") json: Boolean = true
    ): GifsResponse

    @GET("hot/{page}")
    fun getHottestGifs(
        @Path("page") page: Int,
        @Query("json") json: Boolean = true
    ): GifsResponse

    @GET("top/{page}")
    fun getTopGifs(
        @Path("page") page: Int,
        @Query("json") json: Boolean = true
    ): GifsResponse
}