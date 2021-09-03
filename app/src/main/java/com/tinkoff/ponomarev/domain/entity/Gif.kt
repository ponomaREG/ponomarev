package com.tinkoff.ponomarev.domain.entity

data class Gif(
    val id:Int,
    val description: String,
    val author: String,
    val gifURL: String
){
    val gifURLHttps: String
    get() {
        return gifURL.replace("http","https")
    }
}
