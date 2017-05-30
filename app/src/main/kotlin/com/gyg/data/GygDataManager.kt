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

    override fun getReviews(): Observable<List<Review>> {
        return if (networkManager.isOnline()) {
            with(config) {
                reviewService.getReviews(tour = tour, count = reviewCount, page = currentPage)
                        .retryWhen(RetryWithDelay(numTimesToRetry, millisecondsBetweenRetries))
                        .map(::reviewsDataToApplication)
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

    override fun getCachedReviews(): Observable<List<Review>> {
        return Observable.fromCallable { cache.getReviews() }
    }
}