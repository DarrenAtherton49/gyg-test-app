package com.gyg.data.config

class InMemoryConfig : Config {

    override val tour = "berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json"

    override val reviewCount = 5

    override val numTimesToRetry = 3

    override val millisecondsBetweenRetries = 1000L
}