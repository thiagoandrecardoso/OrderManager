package br.com.project.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder
                .routes()
                .route(r -> r.path("/order-receipt/**").uri("lb://order-receipt"))
                .route(r -> r.path("/order-process/**").uri("lb://order-process"))
                .route(r -> r.path("/order-receipt-calculate/**").uri("lb://order-receipt-calculate"))
                .build();
    }

}
