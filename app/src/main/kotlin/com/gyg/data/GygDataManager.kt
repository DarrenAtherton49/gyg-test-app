package com.gyg.data

import com.gyg.NetworkUnavailableException
import com.gyg.data.cache.Cache
import com.gyg.data.config.Config
import com.gyg.data.entity.Review
import com.gyg.data.service.ReviewService
import com.gyg.util.NetworkManager
import com.gyg.util.RetryWithDelay
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GygDataManager @Inject constructor(val reviewService: ReviewService,
                                         val cache: Cache,
                                         val config: Config,
                                         val networkManager: NetworkManager) : DataManager {

    private var currentPage = 0

    /**
     * Fetches reviews from the server.
     * If the request fails, a number of retries will occur according to the values specified in
     * the config.
     * Upon a successful request, the reviews are saved in the cached, and the combined list of
     * current reviews and new reviews are returned.
     */
    override fun getReviews(): Observable<List<Review>> {
        return if (networkManager.isOnline()) {
            with(config) {
                reviewService.getReviews(tour = tourUrl, count = reviewCount, page = currentPage)
                        .retryWhen(RetryWithDelay(numTimesToRetry, millisecondsBetweenRetries))
                        .map(::getReviewsNetworkToApplication)
                        .doOnNext { reviews ->
                            cache.saveReviews(reviews)
                            currentPage++
                        }
                        .map { cache.getReviews() }
            }
        } else {
            Observable.error(NetworkUnavailableException())
        }
    }

    /**
     * Returns cached reviews so that we have something to show immediately or in the case that
     * the application is offline.
     */
    override fun getCachedReviews(): Observable<List<Review>> {
        return Observable.fromCallable { cache.getReviews() }
    }

    /**
     * Submits a new review to the server.
     */
    override fun submitReview(review: Review): Observable<Review?> {
        return if (networkManager.isOnline()) {
            with(config) {
                reviewService.submitReview(url = submitUrl, data = reviewApplicationToNetwork(review))
                        .retryWhen(RetryWithDelay(numTimesToRetry, millisecondsBetweenRetries))
                        .map(::submitReviewNetworkToApplication)
            }
        } else {
            Observable.error(NetworkUnavailableException())
        }
    }
}