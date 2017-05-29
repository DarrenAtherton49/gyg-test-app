package com.gyg.common.threading

import rx.Scheduler

interface UiThread {
    val scheduler: Scheduler
}