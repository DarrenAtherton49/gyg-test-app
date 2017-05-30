package com.gyg.browse

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
class BrowsePresenter @Inject constructor(val dataManager: DataManager,
                                          val ioExecutor: IoExecutor,
                                          val mainThread: UiThread)
    : BasePresenter<BrowsePresenter.View>() {

    /**
     * When the view is attached, we immediately load and show a cached version of the reviews
     * if it is available, before requesting a refresh from the server.
     */
    override fun onViewAttached() {
        addToAutoUnsubscribe {
            dataManager.getCachedReviews().subscribe { cachedReviews ->
                if (cachedReviews.isEmpty()) {
                    showEmptyAndLoadingState()
                } else {
                    showPopulatedState(cachedReviews)
                }
            }
        }
        refreshReviews()
    }

    override fun onViewDetached() {}

    fun onSubmitReviewClicked() {
        view?.openSubmitScreen()
    }

    /**
     * Loads reviews from server and update UI state.
     */
    private fun refreshReviews() {
        addToAutoUnsubscribe {
            dataManager.getReviews()
                    .subscribeOn(ioExecutor.scheduler)
                    .observeOn(mainThread.scheduler)
                    .subscribe({ reviews ->
                        if (reviews.isNotEmpty()) {
                            showPopulatedState(reviews)
                        }
                    }, { throwable ->
                        resolveError(throwable)
                    })
        }
    }

    /**
     * Decides which UI state to show based on the error passed in.
     */
    private fun resolveError(error: Throwable) {
        if (error is NetworkUnavailableException) {
            dataManager.getCachedReviews().subscribe { cachedReviews ->
                if (cachedReviews.isEmpty()) {
                    showOfflineWithoutDataState()
                } else {
                    showOfflineWithDataState(cachedReviews)
                }
            }
        } else {
            view?.showGenericError()
        }
    }

    private fun showEmptyAndLoadingState() {
        view?.showLoading()
        view?.hideList()
    }

    private fun showPopulatedState(reviews: List<Review>) {
        view?.hideLoading()
        view?.showList()
        view?.updateReviews(reviews)
    }

    private fun showOfflineWithDataState(reviews: List<Review>) {
        view?.hideLoading()
        view?.showList()
        view?.showOfflineMessage()
        view?.updateReviews(reviews)
    }

    private fun showOfflineWithoutDataState() {
        view?.hideLoading()
        view?.hideList()
        view?.showOfflineMessage()
    }


    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showList()
        fun hideList()
        fun showGenericError()
        fun showOfflineMessage()
        fun updateReviews(reviews: List<Review>)
        fun openSubmitScreen()
    }
}