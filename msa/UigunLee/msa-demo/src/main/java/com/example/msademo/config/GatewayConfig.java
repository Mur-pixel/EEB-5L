package com.example.msademo.config;

import com.example.msademo.logger.LoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${spring.cloud.gateway.routes[0].uri}")
    private String simpleServiceUri;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
        System.out.println("simple-service-uri: " + simpleServiceUri);

        return null;
    }
}
