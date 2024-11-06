package br.com.project.orderreceipt.model.dtos;


import br.com.project.orderreceipt.model.enums.OrderStatusType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequestDTO {
    @NotNull(message = "Product code cannot be null")
    private String productCode;
    @NotNull(message = "Products cannot be null")
    private List<ProductRequestDTO> products;
    private OrderStatusType status;
}
