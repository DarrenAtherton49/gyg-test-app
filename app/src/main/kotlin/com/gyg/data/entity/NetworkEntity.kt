package com.gyg.data.entity

data class ReviewsResponse(val status: Boolean,
                           val total_reviews: Int,
                           @Suppress("ArrayInDataClass") val data: Array<ReviewData>)

// Note: I have omitted the the 'traveler_type' and 'data_unformatted' attributes as it was unclear
// from the data which types they were supposed to be.
data class ReviewData(val review_id: Int,
                      val rating: String,
                      val title: String,
                      val message: String,
                      val author: String,
                      val foreignLanguage: Boolean,
                      val date: String,
                      val languageCode: String,
                      val reviewerName: String,
                      val reviewerCountry: String)