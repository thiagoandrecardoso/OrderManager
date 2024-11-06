package br.com.project.orderreceipt.model.entities;

import br.com.project.orderreceipt.model.enums.OrderStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private OrderStatusType status;
    private Double totalValue;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;
}
