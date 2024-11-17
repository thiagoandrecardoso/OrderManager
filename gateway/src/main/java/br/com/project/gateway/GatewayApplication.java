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
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Rota para API normal do order-process
                .route("order-process", r -> r.path("/order-process/**")
                        .uri("lb://order-process"))

                // Rota para Swagger do order-process
                .route("order-process-swagger", r -> r
                        .path("/order-process/swagger-ui/**", "/order-process/v3/api-docs/**", "/order-process/swagger-resources/**", "/order-process/webjars/**")
                        .filters(f -> f.rewritePath("/order-process/(?<path>.*)", "/${path}"))
                        .uri("lb://order-process"))

                // Rota para API normal do order-receipt
                .route("order-receipt", r -> r.path("/order-receipt/**")
                        .uri("lb://order-receipt"))

                // Rota para Swagger do order-receipt
                .route("order-receipt-swagger", r -> r
                        .path("/order-receipt/swagger-ui/**", "/order-receipt/v3/api-docs/**", "/order-receipt/swagger-resources/**", "/order-receipt/webjars/**")
                        .filters(f -> f.rewritePath("/order-receipt/(?<path>.*)", "/${path}"))
                        .uri("lb://order-receipt"))

                // Rota para API normal do order-receipt-calculate
                .route("order-receipt-calculate", r -> r.path("/order-receipt-calculate/**")
                        .uri("lb://order-receipt-calculate"))

                // Rota para Swagger do order-receipt-calculate
                .route("order-receipt-calculate-swagger", r -> r
                        .path("/order-receipt-calculate/swagger-ui/**", "/order-receipt-calculate/v3/api-docs/**", "/order-receipt-calculate/swagger-resources/**", "/order-receipt-calculate/webjars/**")
                        .filters(f -> f.rewritePath("/order-receipt-calculate/(?<path>.*)", "/${path}"))
                        .uri("lb://order-receipt-calculate"))

                .build();
    }



}
