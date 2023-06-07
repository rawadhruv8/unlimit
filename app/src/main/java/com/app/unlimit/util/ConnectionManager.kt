package com.app.unlimit.util

import android.net.ConnectivityManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConnectionManager : KoinComponent {

    private val connectionManager: ConnectivityManager by inject()

    fun isOnline(): Boolean {
        val activeNetworkInfo = connectionManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}