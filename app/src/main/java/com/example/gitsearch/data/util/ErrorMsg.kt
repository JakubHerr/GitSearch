package com.example.gitsearch.data.util

enum class ErrorMsg(val message: String) {
    Generic3xx("Redirect response error"),
    Generic4xx("Client request error"),
    Forbidden("You ran out of free requests :("),
    InternalServerError("Internal server error"),
    Generic5xx("Server response error"),
    NotFound("Not found"),
    NoConnection("Please check your internet connection"),
    Teapot("The server is a teapot"),
    Unspecified("Unspecified Error")
}
