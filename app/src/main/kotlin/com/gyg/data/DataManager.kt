package com.gyg.data

import com.gyg.data.entity.Review
import rx.Observable

interface DataManager {

    fun getReviews(): Observable<List<Review>>

    fun getCachedReviews(): Observable<List<Review>>

    fun submitReview(review: Review): Observable<Review?>
}