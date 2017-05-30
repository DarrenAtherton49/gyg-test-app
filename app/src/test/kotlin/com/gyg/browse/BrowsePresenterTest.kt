package com.gyg.browse

import com.gyg.data.DataManager
import com.gyg.data.entity.Review
import com.gyg.util.IoTestExecutor
import com.gyg.util.UiThreadTestScheduler
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Before
import org.junit.Test
import rx.Observable

class BrowsePresenterTest {

    private lateinit var view: BrowsePresenter.View
    private lateinit var presenter: BrowsePresenter
    private lateinit var dataManager: DataManager

    @Before
    fun setUp() {
        view = mock()
        dataManager = mock()
        presenter = BrowsePresenter(dataManager, IoTestExecutor(), UiThreadTestScheduler())
    }

    @Test
    fun onViewAttached_andReviewsCached_showsReviews() {

        val cachedReviews = listOf(Review(1, "", "", "", "", ""))
        val networkReviews = listOf(Review(1, "", "", "", "", ""), Review(2, "", "", "", "", ""))
        When calling presenter.dataManager.getCachedReviews() itReturns Observable.fromCallable {
            cachedReviews
        }
        When calling presenter.dataManager.getReviews() itReturns Observable.fromCallable {
            networkReviews
        }

        // attach view
        presenter.viewAttached(view)

        // verify cached state is shown and then updated state is shown after network call
        verify(view, times(2)).hideLoading()
        verify(view, times(2)).showList()
        verify(view, times(1)).updateReviews(cachedReviews)
        verify(view, times(1)).updateReviews(networkReviews)
    }
}