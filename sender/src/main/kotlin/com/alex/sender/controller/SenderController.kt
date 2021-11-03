package com.alex.sender.controller

import com.alex.sender.model.DataMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class SenderController {

    @MessageMapping("/confirm-message")
    @Throws(Exception::class)
    fun processMessageFromClient(message: DataMessage) {
        println("Receiver received message with id: ${message.id}")
    }
}