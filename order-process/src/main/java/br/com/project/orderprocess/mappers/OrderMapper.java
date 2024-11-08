package br.com.project.orderprocess.mappers;

import br.com.project.orderprocess.model.dtos.OrderDTO;
import br.com.project.orderprocess.model.entities.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO convet(OrderEntity entity);
}
