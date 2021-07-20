package com.smarthings.data.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnectivityImpl(private val context: Context) : Connectivity {

    @Suppress("DEPRECATION")
    override fun hasNetworkAccess(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = manager?.getNetworkCapabilities(manager.activeNetwork)
            when {
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
                activeNetwork?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
                else -> false
            }
        } else {
            // deprecated way used for older versions
            when (manager?.activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                else -> false
            }
        }
    }
}