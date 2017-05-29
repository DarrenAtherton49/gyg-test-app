package com.gyg.common.threading

import rx.Scheduler

interface BackgroundExecutor {
    val scheduler: Scheduler
}