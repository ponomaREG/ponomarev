package com.tinkoff.ponomarev.domain.base

sealed class BaseResult <out T>{
    data class Error(var exception: Throwable): BaseResult<Nothing>()
    data class Success<T>(var data: T): BaseResult<T>()
    object Loading: BaseResult<Nothing>()
}
