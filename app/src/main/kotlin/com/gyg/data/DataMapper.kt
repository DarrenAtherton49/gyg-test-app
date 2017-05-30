package com.gyg.data

import com.gyg.data.entity.GetReviewsResponse
import com.gyg.data.entity.Review
import com.gyg.data.entity.ReviewData
import com.gyg.data.entity.SubmitReviewResponse

/**
 * Functions to map entities from data/network to application objects and vice-versa
 */

internal fun getReviewsNetworkToApplication(getReviewsResponse: GetReviewsResponse): List<Review> {
    return getReviewsResponse.data.map(::reviewNetworkToApplication)
}

internal fun submitReviewNetworkToApplication(submitReviewResponse: SubmitReviewResponse): Review {
    return reviewNetworkToApplication(submitReviewResponse.data)
}

internal fun reviewNetworkToApplication(review: ReviewData): Review = with(review) {
    Review(review_id, rating, title, message, author, date)
}

internal fun reviewApplicationToNetwork(review: Review): ReviewData = with(review) {
    ReviewData(reviewId, rating, title, message, author, date)
}