package br.com.project.orderprocess.services;

import br.com.project.orderprocess.feignclient.OrderReceiptCalculateClient;
import br.com.project.orderprocess.mappers.OrderMapper;
import br.com.project.orderprocess.mappers.ProductMapper;
import br.com.project.orderprocess.model.dtos.OrderDTO;
import br.com.project.orderprocess.model.dtos.ProductDTO;
import br.com.project.orderprocess.model.dtos.ProductFilterDTO;
import br.com.project.orderprocess.model.entities.OrderEntity;
import br.com.project.orderprocess.model.entities.ProductEntity;
import br.com.project.orderprocess.model.enums.OrderStatusType;
import br.com.project.orderprocess.repositories.OrderRepository;
import br.com.project.orderprocess.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderProcessService implements IOrderProcessService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public OrderEntity updateStatus(OrderEntity orderEntity) {
        orderEntity.setStatus(OrderStatusType.PROCESSING);
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderDTO processOrder(OrderEntity orderEntity) {
        orderEntity.setStatus(OrderStatusType.CALCULATED);

        BigDecimal total = BigDecimal.ZERO;

        for (ProductEntity product : orderEntity.getProducts()) {
            BigDecimal unitPrice = product.getUnitPrice();
            BigDecimal quantity = BigDecimal.valueOf(product.getQuantity());
            BigDecimal productTotal = unitPrice.multiply(quantity);
            total = total.add(productTotal);
        }

        orderEntity.setTotalValue(total);

        OrderEntity saved = orderRepository.save(orderEntity);
        return orderMapper.convet(saved);
    }

    @Override
    public OrderEntity findByProductCode(String productCode) {
        return orderRepository.findByProductCode(productCode);
    }

    @Override
    public Page<ProductDTO> getAllByFilter(ProductFilterDTO productFilterDTO) {
        Page<ProductEntity> byOrderId = productRepository.findByOrderId(productFilterDTO.getOrderId(), productFilterDTO.pageable());
        return productMapper.convert(byOrderId);
    }
}
