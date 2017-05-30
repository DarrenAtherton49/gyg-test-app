package com.gyg.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyg.GygApplication
import com.gyg.common.injection.module.ActivityModule

abstract class BaseActivity<View: BaseView, out Presenter : BasePresenter<View>> : AppCompatActivity() {

    protected abstract val passiveView: View
    protected abstract val presenter: Presenter
    protected abstract val layoutResId: Int

    /**
     * In each activity that extends this, automatically set up the content view, specify
     * dependency injection and attach the presenter.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        initInjection()

        presenter.viewAttached(passiveView)
    }

    /**
     * In each activity that extends this, automatically detach the presenter.
     */
    override fun onDestroy() {
        presenter.viewDetached()
        super.onDestroy()
    }

    protected fun appComponent() = (application as GygApplication).appComponent

    protected fun activityModule() = ActivityModule(this)

    protected abstract fun initInjection()
}