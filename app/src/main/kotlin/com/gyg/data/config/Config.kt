package com.gyg.data.config

interface Config {

    val tourUrl: String

    val submitUrl: String

    val reviewCount: Int

    val numTimesToRetry: Int

    val millisecondsBetweenRetries: Long
}