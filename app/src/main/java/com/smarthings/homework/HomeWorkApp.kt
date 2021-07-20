package com.smarthings.homework

import android.app.Application
import com.smarthings.data.di.databaseModule
import com.smarthings.data.di.networkingModule
import com.smarthings.data.di.repositoryModule
import com.smarthings.domain.di.interactionModule
import com.smarthings.homework.di.appModule
import com.smarthings.homework.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HomeWorkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@HomeWorkApp)
            modules(appModules + domainModules + dataModules)
        }
    }

    companion object {
        lateinit var instance: Application
            private set
    }
}

val appModules = listOf(presentationModule, appModule)
val domainModules = listOf(interactionModule)
val dataModules = listOf(networkingModule, repositoryModule, databaseModule)