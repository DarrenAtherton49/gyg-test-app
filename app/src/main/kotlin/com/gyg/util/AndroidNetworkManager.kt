package com.gyg.util

import android.net.ConnectivityManager

class AndroidNetworkManager(val connectivityManager: ConnectivityManager) : NetworkManager {

    override fun isOnline(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }
}