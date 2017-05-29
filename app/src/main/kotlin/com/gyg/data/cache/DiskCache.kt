package com.gyg.data.cache

import com.gyg.data.entity.Review

class DiskCache : Cache {

    override fun getReviews(): List<Review>? {
        return emptyList()//todo implement
    }

    override fun saveReviews(reviews: List<Review>) {
        //todo implement
        // each list should be added to the end of the reviews already saved, not overwrite them
    }
}