package com.alex.receiver

import com.alex.receiver.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class ReceiverApplication

fun main(args: Array<String>) {
	runApplication<ReceiverApplication>(*args)
}
