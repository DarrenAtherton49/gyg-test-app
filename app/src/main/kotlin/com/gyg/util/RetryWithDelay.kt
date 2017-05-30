package com.gyg.util

import rx.Observable
import rx.functions.Func1
import java.util.concurrent.TimeUnit

/**
 * This class is used in Observable streams in order to retry failed network requests.
 */
class RetryWithDelay(private val maxRetries: Int,
                     private val retryDelayMillis: Long)
    : Func1<Observable<out Throwable>, Observable<*>> {

    private var retryCount: Int = 0

    override fun call(attempts: Observable<out Throwable>): Observable<*> {
        return attempts.flatMap {
            if (++retryCount < maxRetries) {
                Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
            } else {
                // Max retries hit. Just pass the error along.
                Observable.error(it)
            }
        }
    }
}