package com.gyg.common.injection.module

import com.gyg.browse.BrowseAdapter
import com.gyg.common.injection.scope.PerScreen
import dagger.Module
import dagger.Provides

@Module
class BrowseViewModule {

    @Provides @PerScreen internal fun provideBrowseAdapter() = BrowseAdapter()
}