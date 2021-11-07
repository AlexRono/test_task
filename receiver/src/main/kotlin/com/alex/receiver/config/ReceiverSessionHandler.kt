package com.alex.receiver.config

import com.alex.receiver.model.DataMessage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.*
import org.springframework.stereotype.Component
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.lang.reflect.Type
import java.util.concurrent.CountDownLatch
import javax.annotation.PostConstruct


@Component
class ReceiverSessionHandler(val objectMapper: ObjectMapper) : StompSessionHandlerAdapter() {
    private val logger: Logger = Logger.getLogger(this::class.java)
    private lateinit var theSession: StompSession
    var latch = CountDownLatch(2)

    @Value("\${app.senderUrl}")
    private val stompURL: String = ""

    @PostConstruct
    fun connect() {
        objectMapper.registerModule(JavaTimeModule())
        val jacksonMessageConverter = MappingJackson2MessageConverter()
        jacksonMessageConverter.objectMapper = objectMapper
        val client: WebSocketClient = StandardWebSocketClient()
        val stompClient = WebSocketStompClient(client)
        stompClient.messageConverter = jacksonMessageConverter
        val sessionHandler: StompSessionHandler = this
        stompClient.connect(stompURL, sessionHandler)
        latch.await()
    }

    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        try {
            logger.info("New session established : " + session.sessionId)
            session.subscribe("/user/receiver/messages", this)
            logger.info("Subscribed to receiver/messages")
            theSession = session
        } finally {
            latch.countDown()
        }
    }

    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        logger.info("Client received: payload: " + payload.toString())
        if (payload is DataMessage) {
            theSession.send("/app/confirm-message", payload)
        }
    }

    override fun getPayloadType(headers: StompHeaders): Type {
        return DataMessage::class.java
    }

    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable
    ) {
        logger.error("Got an exception", exception)
    }

    override fun handleTransportError(session: StompSession, exception: Throwable) {
        logger.warn("A transport error has occurred " + exception.message)
        logger.warn("Trying to reconnect")
        this.connect()
    }

}