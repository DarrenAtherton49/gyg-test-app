package com.gyg.util

import android.net.ConnectivityManager

/**
 * This class allows us to inspect whether the device is currently online.
 */
class AndroidNetworkManager(val connectivityManager: ConnectivityManager) : NetworkManager {

    override fun isOnline(): Boolean {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
    }
}