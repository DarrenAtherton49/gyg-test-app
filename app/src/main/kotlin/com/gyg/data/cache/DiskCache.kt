package com.gyg.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gyg.data.entity.Review
import java.util.*
import java.util.Collections.emptyList
import javax.inject.Singleton

@Singleton
class DiskCache(val sharedPreferences: SharedPreferences, val gson: Gson) : Cache {

    private val KEY_REVIEWS = "sharedPrefsReviews"

    /**
     * Deserialize stored reviews JSON from SharedPreferences and return them as a list.
     */
    override fun getReviews(): List<Review> {
        val reviewsJson = sharedPreferences.getString(KEY_REVIEWS, null)
        return if (reviewsJson != null) {
            val listType = object : TypeToken<ArrayList<Review>>(){}.type
            gson.fromJson(reviewsJson, listType)
        } else {
            emptyList()
        }
    }

    /**
     * Append any new reviews to the currently stored reviews, serialize them and save them all
     * in SharedPreferences.
     */
    override fun saveReviews(reviews: List<Review>) {
        val existingReviews = getReviews()
        val allReviews: List<Review> = if (existingReviews.isNotEmpty()) {
            val list: MutableList<Review> = ArrayList(existingReviews)
            list.addAll(reviews)
            list
        } else {
            reviews
        }
        val reviewsToSave = allReviews.distinctBy { it.reviewId }
        sharedPreferences.edit().putString(KEY_REVIEWS, gson.toJson(reviewsToSave)).apply()
    }
}