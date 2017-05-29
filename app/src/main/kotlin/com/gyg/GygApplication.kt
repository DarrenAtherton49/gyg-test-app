package com.gyg

import android.app.Application
import com.gyg.common.injection.component.AppComponent
import com.gyg.common.injection.component.DaggerAppComponent
import com.gyg.common.injection.module.AppModule

class GygApplication : Application() {

    internal lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initInjection()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}