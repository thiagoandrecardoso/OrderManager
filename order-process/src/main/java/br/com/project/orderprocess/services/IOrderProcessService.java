package br.com.project.orderprocess.services;

import br.com.project.orderprocess.model.dtos.OrderDTO;
import br.com.project.orderprocess.model.dtos.ProductDTO;
import br.com.project.orderprocess.model.dtos.ProductFilterDTO;
import br.com.project.orderprocess.model.entities.OrderEntity;
import org.springframework.data.domain.Page;

public interface IOrderProcessService {
    OrderEntity getOrderById(Long orderId);

    OrderEntity updateStatus(OrderEntity orderEntity);

    OrderDTO processOrder(OrderEntity orderEntity);

    OrderEntity findByProductCode(String productCode);

    Page<ProductDTO> getAllByFilter(ProductFilterDTO productFilterDTO);
}
