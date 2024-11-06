package br.com.project.orderprocess.repositories;


import br.com.project.orderprocess.model.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByProductCode(String productCode);
}
