package br.com.project.orderreceipt;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableRabbit
public class OrderReceiptApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderReceiptApplication.class, args);
    }

}
