package br.com.project.orderreceipt.repositories;

import br.com.project.orderreceipt.model.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Boolean existsByProductCode(String productCode);
}
