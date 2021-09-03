package com.tinkoff.ponomarev.ui.error

import kotlin.Error

sealed class Error{
    object EmptyResultError: Error()
    object UnknownError: Error()
}
