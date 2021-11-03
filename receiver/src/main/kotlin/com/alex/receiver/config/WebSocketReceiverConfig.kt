package com.alex.receiver.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompSessionHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.messaging.WebSocketStompClient


@Configuration
@EnableWebSocketMessageBroker
class WebSocketReceiverConfig : WebSocketMessageBrokerConfigurer {
    @Value("\${app.senderUrl}")
    private val stompURL: String = ""

    @Bean
    fun mappingJackson2MessageConverter(objectMapper: ObjectMapper): MappingJackson2MessageConverter {
        objectMapper.registerModule(JavaTimeModule())
        val jacksonMessageConverter = MappingJackson2MessageConverter()
        jacksonMessageConverter.objectMapper = objectMapper
        return jacksonMessageConverter
    }

    @Autowired
    lateinit var mappingJackson2MessageConverter: MappingJackson2MessageConverter

    @Bean
    fun webSocketClient(): WebSocketStompClient {
        val client: WebSocketClient = StandardWebSocketClient()
        val stompClient = WebSocketStompClient(client)
        stompClient.messageConverter = mappingJackson2MessageConverter
        val sessionHandler: StompSessionHandler = CustomSessionHandler()
        stompClient.connect(stompURL, sessionHandler)
        return stompClient
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic/")
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/receiver")
    }
}