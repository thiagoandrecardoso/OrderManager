package br.com.project.orderreceipt.services;

import br.com.project.orderreceipt.exceptions.DuplicateOrderException;
import br.com.project.orderreceipt.exceptions.SendToQueueException;
import br.com.project.orderreceipt.infra.mqueue.RequestProducerOrder;
import br.com.project.orderreceipt.mappers.OrderMapper;
import br.com.project.orderreceipt.model.dtos.OrderRequestDTO;
import br.com.project.orderreceipt.model.dtos.OrderSendToQueueDTO;
import br.com.project.orderreceipt.model.entities.OrderEntity;
import br.com.project.orderreceipt.model.entities.ProductEntity;
import br.com.project.orderreceipt.model.enums.OrderStatusType;
import br.com.project.orderreceipt.repositories.OrderRepository;
import br.com.project.orderreceipt.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final RequestProducerOrder requestPublishedOrder;

    @Override
    public void processOrder(OrderRequestDTO orderRequestDTO) {
        checkForDuplicateOrder(orderRequestDTO);
        OrderEntity orderEntity = orderMapper.convert(orderRequestDTO);
        orderEntity.setStatus(OrderStatusType.RECEIVED);
        orderRepository.save(orderEntity);

        for (ProductEntity product : orderEntity.getProducts()) {
            product.setOrder(orderEntity);
            productRepository.save(product);
        }

        OrderSendToQueueDTO sendToQueueDTO = orderMapper.convertToQueueDTO(orderEntity);
        sendOrderToQueue(sendToQueueDTO);
    }

    public void checkForDuplicateOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRepository.existsByProductCode(orderRequestDTO.getProductCode())) {
            throw new DuplicateOrderException("Order already exists with code: " + orderRequestDTO.getProductCode());
        }
    }

    private void sendOrderToQueue(OrderSendToQueueDTO sendToQueueDTO) {
        try {
            requestPublishedOrder.publishOrder(sendToQueueDTO);
        } catch (Exception ex) {
            throw new SendToQueueException(ex.getMessage());
        }
    }
}
