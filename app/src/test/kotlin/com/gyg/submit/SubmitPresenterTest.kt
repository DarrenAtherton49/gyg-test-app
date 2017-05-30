package com.gyg.submit

import com.gyg.NetworkUnavailableException
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

class SubmitPresenterTest {

    private lateinit var view: SubmitPresenter.View
    private lateinit var presenter: SubmitPresenter
    private lateinit var dataManager: DataManager

    private val successfulSubmitObservable = Observable.fromCallable {
        Review(1, "", "", "", "", "")
    }

    @Before
    fun setUp() {
        view = mock()
        dataManager = mock()
        presenter = SubmitPresenter(dataManager, IoTestExecutor(), UiThreadTestScheduler())
    }

    @Test
    fun onSubmitClicked_successfulSubmission_updatesView() {

        val review = Review(1, "", "", "", "", "")
        When calling presenter.dataManager.submitReview(review) itReturns successfulSubmitObservable

        // attach view
        presenter.viewAttached(view)

        // simulate submit button click
        presenter.onSubmitClicked(review)

        // verify UI is in 'submitting' state
        verify(view, times(1)).showLoading()
        verify(view, times(1)).disableControls()

        // verify data manager is called
        verify(dataManager, times(1)).submitReview(review)

        // verify the UI is in a 'success' state
        verify(view, times(1)).hideLoading()
        verify(view, times(1)).enableControls()
        verify(view, times(1)).closeScreen()
    }

    @Test
    fun onSubmitClicked_offline_updatesView() {

        val review = Review(1, "", "", "", "", "")
        When calling presenter.dataManager.submitReview(review) itReturns
                Observable.error(NetworkUnavailableException())

        // attach view
        presenter.viewAttached(view)

        // simulate submit button click
        presenter.onSubmitClicked(review)

        // verify UI is in 'submitting' state
        verify(view, times(1)).showLoading()
        verify(view, times(1)).disableControls()

        // verify data manager is called
        verify(dataManager, times(1)).submitReview(review)

        // verify the UI is in a 'success' state
        verify(view, times(1)).hideLoading()
        verify(view, times(1)).enableControls()
        verify(view, times(1)).showOfflineMessage()
    }
}