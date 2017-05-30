package com.gyg.data.entity

data class Review(val reviewId: Int?,
                  val rating: String,
                  val title: String,
                  val message: String,
                  val author: String,
                  val date: String)