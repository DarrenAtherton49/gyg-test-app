package com.gyg.submit

import com.gyg.NetworkUnavailableException
import com.gyg.base.BasePresenter
import com.gyg.base.BaseView
import com.gyg.common.injection.scope.PerScreen
import com.gyg.common.threading.IoExecutor
import com.gyg.common.threading.UiThread
import com.gyg.data.DataManager
import com.gyg.data.entity.Review
import javax.inject.Inject

@PerScreen
class SubmitPresenter @Inject constructor(val dataManager: DataManager,
                                          val ioExecutor: IoExecutor,
                                          val mainThread: UiThread)
    : BasePresenter<SubmitPresenter.View>() {

    override fun onViewAttached() {}

    override fun onViewDetached() {}

    /**
     * Called when submit is clicked, takes the data entered and submits a new review to the server.
     */
    fun onSubmitClicked(review: Review) {
        showSubmittingState()

        dataManager.submitReview(review)
                .subscribeOn(ioExecutor.scheduler)
                .observeOn(mainThread.scheduler)
                .subscribe({ review ->
                    if (review != null) {
                        showSuccess()
                    } else {
                        showGenericError()
                    }
                }, { throwable ->
                    if (throwable is NetworkUnavailableException) {
                        showOfflineState()
                    } else {
                        showGenericError()
                    }
                })
    }

    private fun showSubmittingState() {
        view?.showLoading()
        view?.disableControls()
    }

    private fun showGenericError() {
        view?.hideLoading()
        view?.enableControls()
        view?.showGenericError()
    }

    private fun showOfflineState() {
        view?.hideLoading()
        view?.enableControls()
        view?.showOfflineMessage()
    }

    private fun showSuccess() {
        view?.hideLoading()
        view?.enableControls()
        view?.closeScreen()
    }

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun disableControls()
        fun enableControls()
        fun closeScreen()
        fun showGenericError()
        fun showOfflineMessage()
    }
}