package com.Market.MarketPulse;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
		//WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("*")  // ✅ CORS FIXED
			.withSockJS();
		//WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		WebSocketMessageBrokerConfigurer.super.addReturnValueHandlers(returnValueHandlers);
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		// TODO Auto-generated method stub
		return WebSocketMessageBrokerConfigurer.super.configureMessageConverters(messageConverters);
	}


	@Override
	public @Nullable Integer getPhase() {
		// TODO Auto-generated method stub
		return WebSocketMessageBrokerConfigurer.super.getPhase();
	}

}
