package com.gyg.data.config

interface Config {

    val tour: String

    val reviewCount: Int

    val numTimesToRetry: Int

    val millisecondsBetweenRetries: Long
}