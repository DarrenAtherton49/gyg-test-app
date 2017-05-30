package com.gyg.data

import com.gyg.data.entity.Review
import com.gyg.data.entity.ReviewData
import com.gyg.data.entity.ReviewsResponse

/**
 * Functions to map entities from data/network to application objects
 */

internal fun reviewsDataToApplication(reviewsResponse: ReviewsResponse): List<Review> {
    return reviewsResponse.data.map(::reviewDataToApplication)
}

internal fun reviewDataToApplication(review: ReviewData): Review = with(review) {
    Review(review_id,
            rating,
            title,
            message,
            author,
            date)
}