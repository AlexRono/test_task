package com.alex.sender.tasks

import com.alex.sender.config.SubscribeEventListener
import com.alex.sender.model.DataMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*


@Component
class SendToQueueTask {
    @Autowired
    lateinit var template: SimpMessagingTemplate

    @Scheduled(fixedRate = 2000)
    fun sendMessage() {
        if (SubscribeEventListener.isReceiverConnected.get()) {
            val message = generateNewMessage()
            template.convertAndSendToUser("receiver", "/messages", message)
            println("Sender sent message with id: ${message.id}")
        }
    }

    private fun generateNewMessage(): DataMessage {
        return DataMessage(
            UUID.randomUUID().toString(),
            Instant.now(),
            Random().nextDouble()
        )
    }
}