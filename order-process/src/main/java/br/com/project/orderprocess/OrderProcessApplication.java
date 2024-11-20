package br.com.project.orderprocess;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableRabbit
@EnableFeignClients
@EnableCaching
public class OrderProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProcessApplication.class, args);
    }

}
