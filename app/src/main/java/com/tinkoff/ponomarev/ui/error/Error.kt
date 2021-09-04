package com.tinkoff.ponomarev.ui.error

sealed class Error: Throwable(){
    object EmptyResultError: Error()
    object UnknownError: Error()
    object NullGifUrlError: Error()
}
