package com.alex.sender.model

import java.time.Instant

data class DataMessage(val id: String, val timestamp: Instant, val value: Double)