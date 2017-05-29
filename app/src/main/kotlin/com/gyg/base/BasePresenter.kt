package com.gyg.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<View: BaseView> {

    protected var view: View? = null
        private set

    protected val compositeSubscription: CompositeSubscription by lazy { CompositeSubscription() }

    fun viewAttached(view: View) {
        this.view = view
        onViewAttached()
    }

    fun viewDetached() {
        unsubscribeSubscriptions()
        onViewDetached()
        view = null
    }

    /*
     * Implement this method to perform initialisation, subscribe to any reactive
     * streams/observables etc. when the view is attached.
     */
    protected abstract fun onViewAttached()

    /*
     * Implement this method to stop any running tasks etc. when the view is detached.
     * Reactive subscriptions will automatically be unsubscribed via unsubscribeSubscriptions() if
     * they are added to the compositeSubscription via addToAutoUnsubscribe()
     */
    protected abstract fun onViewDetached()

    inline protected fun addToAutoUnsubscribe(action: () -> Subscription) {
        compositeSubscription.add(action())
    }

    private fun unsubscribeSubscriptions() {
        compositeSubscription.clear()
    }
}