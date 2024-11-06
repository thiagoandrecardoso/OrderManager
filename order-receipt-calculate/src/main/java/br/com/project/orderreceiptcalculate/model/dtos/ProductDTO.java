package br.com.project.orderreceiptcalculate.model.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductDTO {
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
}
