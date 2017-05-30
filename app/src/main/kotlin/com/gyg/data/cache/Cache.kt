package com.gyg.data.cache

import com.gyg.data.entity.Review

interface Cache {

    fun getReviews(): List<Review>

    fun saveReviews(reviews: List<Review>)
}