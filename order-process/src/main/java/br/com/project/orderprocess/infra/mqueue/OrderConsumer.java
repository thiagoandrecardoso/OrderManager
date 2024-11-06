package br.com.project.orderprocess.infra.mqueue;

import br.com.project.orderprocess.feignclient.OrderReceiptCalculateClient;
import br.com.project.orderprocess.model.dtos.OrderDTO;
import br.com.project.orderprocess.model.entities.OrderEntity;
import br.com.project.orderprocess.services.OrderProcessService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final OrderProcessService orderProcessService;
    private final OrderReceiptCalculateClient orderReceiptCalculateClient;

    @RabbitListener(queues = "${mq.queues.order-queue}")
    public void receivingOrder(String payload){
        var mapper = new ObjectMapper();
        try {
            OrderDTO orderDTO = mapper.readValue(payload, OrderDTO.class);
            log.info("Order received: {}", orderDTO);

            OrderEntity orderEntity = orderProcessService.getOrderById(orderDTO.getId());

            if (orderEntity != null) {
                orderEntity.setStatus(orderDTO.getStatus());
                OrderEntity updateStatus = orderProcessService.updateStatus(orderEntity);
                OrderDTO processOrder = orderProcessService.processOrder(updateStatus);
                orderReceiptCalculateClient.calculate(processOrder);
            } else {
                log.error("Order not found: {}", orderDTO);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
