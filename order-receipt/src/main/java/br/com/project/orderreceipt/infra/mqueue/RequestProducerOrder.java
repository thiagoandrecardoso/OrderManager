package br.com.project.orderreceipt.infra.mqueue;

import br.com.project.orderreceipt.model.dtos.OrderSendToQueueDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestProducerOrder {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void publishOrder(OrderSendToQueueDTO order) throws JsonProcessingException {
        var json = convertIntoJson(order);
        rabbitTemplate.convertAndSend(queue.getName(), json);
    }

    public String convertIntoJson(OrderSendToQueueDTO order) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(order);
    }
}
