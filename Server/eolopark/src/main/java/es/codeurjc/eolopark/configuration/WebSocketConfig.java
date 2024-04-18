package es.codeurjc.eolopark.configuration;

import es.codeurjc.eolopark.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


/**
 * This component is used to configure websocket connections.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    ServerService handler;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(handler, "/notifications")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}