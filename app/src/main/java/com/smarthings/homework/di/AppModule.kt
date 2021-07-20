package com.smarthings.homework.di

import com.smarthings.data.common.CoroutineContextProvider
import org.koin.dsl.module

val appModule = module {
    single { CoroutineContextProvider() }
}