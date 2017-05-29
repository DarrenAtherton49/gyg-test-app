package com.gyg.data.entity

data class Review(val reviewId: Int,
                  val rating: String,
                  val title: String,
                  val message: String,
                  val author: String,
                  val foreignLanguage: Boolean,
                  val date: String,
                  val languageCode: String,
                  val reviewerName: String,
                  val reviewerCountry: String)