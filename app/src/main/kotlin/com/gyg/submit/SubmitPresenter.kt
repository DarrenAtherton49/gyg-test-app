package com.gyg.submit

import com.gyg.base.BasePresenter
import com.gyg.base.BaseView
import com.gyg.common.injection.scope.PerScreen
import com.gyg.common.threading.IoExecutor
import com.gyg.common.threading.UiThread
import com.gyg.data.DataManager
import com.gyg.data.entity.Review
import javax.inject.Inject

@PerScreen
class SubmitPresenter @Inject constructor(val dataManager: DataManager,
                                          val ioExecutor: IoExecutor,
                                          val mainThread: UiThread)
    : BasePresenter<SubmitPresenter.View>() {

    override fun onViewAttached() {}

    override fun onViewDetached() {}

    fun onSubmitClicked(review: Review) {

    }

    interface View : BaseView {

    }
}