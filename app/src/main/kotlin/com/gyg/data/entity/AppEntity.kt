package com.gyg.data.entity

/**
 * Entities which are only used at the domain/application level.
 */

data class Review(val reviewId: Int?,
                  val rating: String,
                  val title: String,
                  val message: String,
                  val author: String,
                  val date: String)