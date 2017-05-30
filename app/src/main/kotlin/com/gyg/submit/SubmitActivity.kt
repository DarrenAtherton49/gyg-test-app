package com.gyg.submit

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.gyg.R
import com.gyg.base.BaseActivity
import com.gyg.common.injection.component.DaggerSubmitViewComponent
import com.gyg.data.entity.Review
import kotlinx.android.synthetic.main.activity_submit.*
import kotlinx.android.synthetic.main.content_submit.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SubmitActivity : BaseActivity<SubmitPresenter.View, SubmitPresenter>(), SubmitPresenter.View {

    override val passiveView = this
    @Inject override lateinit var presenter: SubmitPresenter
    @LayoutRes override val layoutResId = R.layout.activity_submit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.activity_submit_title)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_submit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_submit -> {
                presenter.onSubmitClicked(buildReview())
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initInjection() {
        DaggerSubmitViewComponent.builder()
                .appComponent(appComponent())
                .activityModule(activityModule())
                .build()
                .inject(this)
    }

    private fun buildReview(): Review {
        return Review(reviewId = null,
                rating = ratingBar.rating.toString(),
                title = reviewTitle.text.toString(),
                message = message.text.toString(),
                author = author.text.toString(),
                date = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(Date()))
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun disableControls() {
        ratingBar.isEnabled = false
        reviewTitle.isEnabled = false
        message.isEnabled = false
        author.isEnabled = false
    }

    override fun enableControls() {
        ratingBar.isEnabled = true
        reviewTitle.isEnabled = true
        message.isEnabled = true
        author.isEnabled = true
    }

    override fun closeScreen() {
        finish()
    }

    override fun showGenericError() {
        Snackbar.make(coordinatorLayout, getString(R.string.generic_error_message), LENGTH_LONG).show()
    }

    override fun showOfflineMessage() {
        Snackbar.make(coordinatorLayout, getString(R.string.offline_message), LENGTH_LONG).show()
    }
}
