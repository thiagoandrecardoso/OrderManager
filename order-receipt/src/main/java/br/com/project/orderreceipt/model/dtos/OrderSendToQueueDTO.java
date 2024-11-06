package br.com.project.orderreceipt.model.dtos;

import br.com.project.orderreceipt.model.enums.OrderStatusType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSendToQueueDTO {
    private Long id;
    private String productCode;
    private List<ProductRequestDTO> products;
    private OrderStatusType status;
}
