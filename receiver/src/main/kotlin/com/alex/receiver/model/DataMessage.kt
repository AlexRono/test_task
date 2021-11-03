package com.alex.receiver.model


import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class DataMessage(@JsonProperty("id")val id: String,
                       @JsonProperty("timestamp")val timestamp: Instant,
                       @JsonProperty("value")val value: Double)