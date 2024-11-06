package br.com.project.orderreceiptcalculate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderReceiptCalculateApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderReceiptCalculateApplication.class, args);
    }

}
