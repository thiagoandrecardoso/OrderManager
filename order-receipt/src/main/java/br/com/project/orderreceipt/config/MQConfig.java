package br.com.project.orderreceipt.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Value("${mq.queues.order-queue}")
    private String orderQueue;

    @Bean
    public Queue queueOrder(){
        return new Queue(orderQueue, true);
    }
}
