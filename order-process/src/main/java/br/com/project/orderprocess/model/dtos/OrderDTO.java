package br.com.project.orderprocess.model.dtos;

import br.com.project.orderprocess.model.enums.OrderStatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO implements Serializable {
    private Long id;
    private String productCode;
    private List<ProductDTO> products;
    private OrderStatusType status;
}
