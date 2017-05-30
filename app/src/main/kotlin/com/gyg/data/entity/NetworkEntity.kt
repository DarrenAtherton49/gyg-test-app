package com.gyg.data.entity

data class GetReviewsResponse(val status: Boolean,
                              val total_reviews: Int,
                              @Suppress("ArrayInDataClass") val data: Array<ReviewData>)

data class SubmitReviewResponse(val status: Boolean,
                                val data: ReviewData)

data class ReviewData(val review_id: Int?,
                      val rating: String,
                      val title: String,
                      val message: String,
                      val author: String,
                      val date: String)