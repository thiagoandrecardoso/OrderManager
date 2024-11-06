package br.com.project.orderreceiptcalculate.model.dtos;

import br.com.project.orderreceiptcalculate.model.enums.OrderStatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {
    private Long id;
    private String productCode;
    private List<ProductDTO> products;
    private OrderStatusType status;
}
