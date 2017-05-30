package com.gyg.util

import com.gyg.common.threading.UiThread
import rx.Scheduler
import rx.schedulers.Schedulers

class UiThreadTestScheduler : UiThread {

    override val scheduler: Scheduler = Schedulers.immediate()
}