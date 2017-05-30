package com.gyg.common.injection.component

import com.gyg.common.injection.module.ActivityModule
import com.gyg.common.injection.module.SubmitViewModule
import com.gyg.common.injection.scope.PerScreen
import com.gyg.submit.SubmitActivity
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ActivityModule::class, SubmitViewModule::class))
interface SubmitViewComponent : ActivityComponent {

    fun inject(submitActivity: SubmitActivity)
}