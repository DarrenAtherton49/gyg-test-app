package com.gyg.data.service

import com.gyg.data.entity.GetReviewsResponse
import com.gyg.data.entity.ReviewData
import com.gyg.data.entity.SubmitReviewResponse
import retrofit2.http.*
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
                   @Query("direction") direction: String = "DESC"): Observable<GetReviewsResponse>

    @POST
    fun submitReview(@Url url: String,
                     @Body data: ReviewData): Observable<SubmitReviewResponse>
}