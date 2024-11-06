package br.com.project.orderreceipt.mappers;

import br.com.project.orderreceipt.model.dtos.OrderRequestDTO;
import br.com.project.orderreceipt.model.dtos.OrderSendToQueueDTO;
import br.com.project.orderreceipt.model.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    OrderEntity convert(OrderRequestDTO orderRequestDTO);
    OrderSendToQueueDTO convertToQueueDTO(OrderEntity orderEntity);
}
