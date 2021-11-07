package com.alex.sender.config

import org.springframework.context.event.EventListener
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent
import java.util.concurrent.atomic.AtomicBoolean


@Component
class SubscribeEventListener {
    companion object {
        val isReceiverConnected = AtomicBoolean(false)
    }

    @EventListener
    fun handleSessionSubscribeEvent(event: SessionSubscribeEvent) {
        val message = event.message as GenericMessage<*>
        val simpDestination = message.headers["simpDestination"] as String?
        if (simpDestination!!.startsWith("/user/receiver/messages")) {
            println("Our receiver connected!")
            isReceiverConnected.set(true)
        }
    }

    @EventListener
    fun handleSessionUnsubscribeEvent(event: SessionUnsubscribeEvent) {
        val message = event.message as GenericMessage<*>
        val simpDestination = message.headers["simpDestination"] as String?
        if (simpDestination.equals("/user/receiver/messages")) {
            println("Our receiver disconnected!")
            isReceiverConnected.set(false)
        }
    }
}