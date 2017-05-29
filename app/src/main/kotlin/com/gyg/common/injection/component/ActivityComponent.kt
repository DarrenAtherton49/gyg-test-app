package com.gyg.common.injection.component

import android.app.Activity
import com.gyg.common.injection.module.ActivityModule
import com.gyg.common.injection.scope.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun activity(): Activity
}
