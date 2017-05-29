package com.gyg.common.injection.component

import android.content.Context
import com.gyg.common.injection.module.AppModule
import com.gyg.common.threading.IoExecutor
import com.gyg.common.threading.UiThread
import com.gyg.data.DataManager
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger component which lasts for the entire app lifecycle
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun context(): Context
    fun ioExecutor(): IoExecutor
    fun uiThread(): UiThread
    fun dataManager(): DataManager
}