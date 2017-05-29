package com.gyg.browse

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_LONG
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.gyg.R
import com.gyg.base.BaseActivity
import com.gyg.common.injection.component.DaggerBrowseViewComponent
import com.gyg.data.entity.Review
import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.content_browse.*
import javax.inject.Inject

class BrowseActivity : BaseActivity<BrowsePresenter.View, BrowsePresenter>(),
        BrowsePresenter.View {

    override val passiveView = this
    @Inject override lateinit var presenter: BrowsePresenter
    @LayoutRes override val layoutResId = R.layout.activity_browse

    //===================================================================================
    // Lifecycle methods and initialization
    //===================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_browse, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //===================================================================================
    // Dependency injection
    //===================================================================================

    override fun initInjection() {
        DaggerBrowseViewComponent.builder()
                .appComponent(appComponent())
                .activityModule(activityModule())
                .build()
                .inject(this)
    }

    //===================================================================================
    // View methods
    //===================================================================================

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

    override fun updateReviews(review: List<Review>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}