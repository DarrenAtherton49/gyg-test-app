package com.gyg.data.service

import com.gyg.data.entity.ReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import rx.Observable
import javax.inject.Singleton

@Singleton
interface ReviewService {

    @GET
    fun getReviews(@Url tour: String,
                   @Query("count") count: Int,
                   @Query("page") page: Int,
                   @Query("rating") rating: Int = 0,
                   @Query("sortBy") sortBy: String = "date_of_review",
                   @Query("direction") direction: String = "DESC"): Observable<ReviewsResponse>
}