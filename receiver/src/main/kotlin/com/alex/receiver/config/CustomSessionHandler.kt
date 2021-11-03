package com.alex.receiver.config

import com.alex.receiver.model.DataMessage
import org.apache.log4j.Logger
import org.springframework.lang.Nullable
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import java.lang.reflect.Type


class CustomSessionHandler : StompSessionHandlerAdapter() {
    private val logger: Logger = Logger.getLogger(CustomSessionHandler::class.java)
    private lateinit var theSession: StompSession

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        logger.info("New session established : " + session.sessionId)
        session.subscribe("/user/receiver/messages", this)
        logger.info("Subscribed to receiver/messages")
        theSession=session
    }

    override fun handleFrame(headers: StompHeaders, @Nullable payload: Any?) {
        logger.info("Client received: payload: " + payload.toString())
        if (payload is DataMessage) {
            theSession.send("/app/confirm-message", payload)
        }
    }

    override fun handleException(session: StompSession,
                                 command: StompCommand?,
                                 headers: StompHeaders,
                                 payload: ByteArray,
                                 exception: Throwable) {
        logger.error("Got an exception", exception)
    }

    override fun getPayloadType(headers: StompHeaders): Type {
        return DataMessage::class.java
    }


    override fun handleTransportError(session: StompSession, exception: Throwable) {
        logger.warn("A transport error has occurred " + exception.message)
        logger.warn("Trying to reconnect")
        WebSocketReceiverConfig().webSocketClient()
    }
}