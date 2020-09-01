package com.example.bookstore.core.data

sealed class Response<out T> {
    class Success<out T>(val value: T) : Response<T>()
    class Error(val exception: String) : Response<Nothing>()
    class Loading():Response<Nothing>()
}
