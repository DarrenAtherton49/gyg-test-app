package com.gyg.browse

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.gyg.R
import com.gyg.common.injection.scope.PerScreen
import com.gyg.data.entity.Review
import kotlinx.android.synthetic.main.item_review.view.*
import java.util.*

@PerScreen
class BrowseAdapter constructor(var reviews: List<Review> = emptyList())
    : RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        with(holder.itemView) {
            title.text = review.title
            message.text = review.message
            authorDate.text = String.format(Locale.getDefault(), "%s - %s", review.author, review.date)
            try {
                ratingBar.rating = review.rating.toFloat()
                ratingBar.visibility = VISIBLE
            } catch (e: Exception) {
                ratingBar.visibility = GONE
            }
        }
    }

    override fun getItemCount() = reviews.size

    internal fun replaceData(newReviews: List<Review>) {
        reviews = newReviews
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) = reviews[position]
}