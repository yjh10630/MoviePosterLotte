package com.jihunyun.movieposterlotte.utils

sealed class ResponseResult<T> {
    data class Success<T>(val value: T? = null) : ResponseResult<T>()
    data class Error<T>(val message: String? = null) : ResponseResult<T>()
}