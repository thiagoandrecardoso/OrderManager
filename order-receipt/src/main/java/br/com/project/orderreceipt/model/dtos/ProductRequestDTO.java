package br.com.project.orderreceipt.model.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductRequestDTO {
    @NotNull(message = "Product name cannot be null")
    private String productName;
    @NotNull(message = "Quantity cannot be null")
    private int quantity;
    @NotNull(message = "Unit price cannot be null")
    private BigDecimal unitPrice;
}