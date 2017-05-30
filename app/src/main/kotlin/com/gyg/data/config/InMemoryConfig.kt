package com.gyg.data.config

class InMemoryConfig : Config {

    override val tourUrl = "berlin-l17/tempelhof-2-hour-airport-history-tourUrl-berlin" +
            "-airlift-more-t23776/reviews.json"

    override val submitUrl = ""

    override val reviewCount = 15

    override val numTimesToRetry = 3

    override val millisecondsBetweenRetries = 1000L
}