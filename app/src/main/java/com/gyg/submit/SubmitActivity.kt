package com.gyg.submit

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.gyg.R
import com.gyg.base.BaseActivity
import com.gyg.common.injection.component.DaggerSubmitViewComponent
import com.gyg.data.entity.Review
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
}
