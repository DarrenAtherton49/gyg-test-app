package com.gyg.util

import com.gyg.common.threading.IoExecutor
import rx.Scheduler
import rx.schedulers.Schedulers

class IoTestExecutor : IoExecutor {

    override val scheduler: Scheduler = Schedulers.immediate()
}