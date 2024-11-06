package br.com.project.orderprocess.mappers;

import br.com.project.orderprocess.model.dtos.OrderDTO;
import br.com.project.orderprocess.model.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    OrderDTO convet(OrderEntity entity);
}
