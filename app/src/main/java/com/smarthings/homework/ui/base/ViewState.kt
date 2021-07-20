package com.smarthings.homework.ui.base

sealed class ViewState<out T>
class Success<out T>(val data: T) : ViewState<T>()
class Error<out T>(val error: Throwable) : ViewState<T>()
class Loading<out T> : ViewState<T>()