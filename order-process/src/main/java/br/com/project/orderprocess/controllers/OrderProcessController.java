package br.com.project.orderprocess.controllers;

import br.com.project.orderprocess.feignclient.OrderReceiptCalculateClient;
import br.com.project.orderprocess.model.dtos.OrderDTO;
import br.com.project.orderprocess.model.dtos.ProductDTO;
import br.com.project.orderprocess.model.dtos.ProductFilterDTO;
import br.com.project.orderprocess.model.entities.OrderEntity;
import br.com.project.orderprocess.services.OrderProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("order-process")
public class OrderProcessController {

    private final OrderProcessService orderProcessService;
    private final OrderReceiptCalculateClient orderReceiptCalculateClient;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody String productCode) {
        log.info("POST /order-process {}", productCode);
        OrderEntity byProductCode = orderProcessService.findByProductCode(productCode);
        OrderEntity updateStatus = orderProcessService.updateStatus(byProductCode);
        OrderDTO orderDTO = orderProcessService.processOrder(updateStatus);
        orderReceiptCalculateClient.calculate(orderDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<OrderEntity> get(@PathVariable String productCode) {
        log.info("GET /order-process/{}", productCode);
        OrderEntity order = getOrderEntity(productCode);
        return ResponseEntity.ok(order);
    }

    @Cacheable(value = "order", key = "#productCode")
    public OrderEntity getOrderEntity(String productCode) {
        log.info("Fetching order for productCode: {}", productCode);
        return orderProcessService.findByProductCode(productCode);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllByFilter(ProductFilterDTO productFilterDTO) {
        log.info("GET /order-process {}", productFilterDTO);
        Page<ProductDTO> allByFilter = orderProcessService.getAllByFilter(productFilterDTO);
        return ResponseEntity.ok(allByFilter);
    }
}
