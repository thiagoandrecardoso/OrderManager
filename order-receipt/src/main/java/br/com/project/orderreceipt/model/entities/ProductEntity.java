package br.com.project.orderreceipt.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
