package com.gyg.browse

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout.VERTICAL
import com.gyg.R
import com.gyg.base.BaseActivity
import com.gyg.common.injection.component.DaggerBrowseViewComponent
import com.gyg.common.injection.module.BrowseViewModule
import com.gyg.data.entity.Review
import com.gyg.submit.SubmitActivity
import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.content_browse.*
import javax.inject.Inject

class BrowseActivity : BaseActivity<BrowsePresenter.View, BrowsePresenter>(), BrowsePresenter.View {

    override val passiveView = this
    @Inject override lateinit var presenter: BrowsePresenter
    @LayoutRes override val layoutResId = R.layout.activity_browse

    @Inject lateinit var browseAdapter: BrowseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_browse, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_submit_review) {
            presenter.onSubmitReviewClicked()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun initInjection() {
        DaggerBrowseViewComponent.builder()
                .appComponent(appComponent())
                .activityModule(activityModule())
                .browseViewModule(BrowseViewModule(this))
                .build()
                .inject(this)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerView.adapter = browseAdapter
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = GONE
    }

    override fun showList() {
        recyclerView.visibility = VISIBLE
    }

    override fun hideList() {
        recyclerView.visibility = GONE
    }

    override fun showOfflineMessage() {
        Snackbar.make(coordinatorLayout, getString(R.string.offline_message), LENGTH_LONG).show()
    }

    override fun updateReviews(reviews: List<Review>) {
        browseAdapter.replaceData(reviews)
    }

    override fun openSubmitScreen() {
        startActivity(Intent(this, SubmitActivity::class.java))
    }
}
