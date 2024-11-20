package br.com.project.orderprocess.model.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductDTO implements Serializable {
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
}
