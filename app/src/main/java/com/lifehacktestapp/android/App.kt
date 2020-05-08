package com.lifehacktestapp.android

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set

        fun isNetworkAvailable(): Boolean {
            val cm = instance
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo?.isConnected ?: false
        }
    }

}