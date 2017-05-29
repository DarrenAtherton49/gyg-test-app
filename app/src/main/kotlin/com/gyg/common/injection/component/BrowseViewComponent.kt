package com.gyg.common.injection.component

import com.gyg.browse.BrowseActivity
import com.gyg.common.injection.module.ActivityModule
import com.gyg.common.injection.module.BrowseViewModule
import com.gyg.common.injection.scope.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = arrayOf(AppComponent::class),
        modules = arrayOf(ActivityModule::class, BrowseViewModule::class))
interface BrowseViewComponent : ActivityComponent {

    fun inject(browseActivity: BrowseActivity)
}