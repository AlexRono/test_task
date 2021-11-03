package com.alex.sender.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.RequestUpgradeStrategy
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy
import org.springframework.web.socket.server.support.DefaultHandshakeHandler


@Configuration
@EnableWebSocketMessageBroker
class WebSocketSenderConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic", "/user")
        config.setApplicationDestinationPrefixes("/app")
    }

    var upgradeStrategy: RequestUpgradeStrategy = TomcatRequestUpgradeStrategy()
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/sender").withSockJS()
        registry.addEndpoint("/sender")
                .setHandshakeHandler(DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("*");
    }
}