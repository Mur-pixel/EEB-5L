package org.example.gateway.config;

import org.example.gateway.logger.LoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    // 우리가 application.yaml에 작성한 simple-service-uri 경로입니다.
    // 추후 Simple Service를 작성해서 인식시켜줘야 합니다.
    // 현재 경로는 `http://localhost:12345` 으로 인식됩니다.
    @Value("${spring.cloud.gateway.routes[0].uri}")
    private String simpleServiceUri;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingFilter loggingFilter) {
        System.out.println("simple-service-uri: " + simpleServiceUri);

        return null;
    }
}
