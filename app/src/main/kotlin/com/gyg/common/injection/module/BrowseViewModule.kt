package com.gyg.common.injection.module

import android.support.v7.app.AppCompatActivity
import com.gyg.browse.BrowseAdapter
import com.gyg.common.injection.scope.PerScreen
import dagger.Module
import dagger.Provides

@Module
class BrowseViewModule(private val activity: AppCompatActivity) {

    @Provides @PerScreen internal fun provideBrowseAdapter() = BrowseAdapter()
}