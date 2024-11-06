package br.com.project.orderreceipt.controllers;

import br.com.project.orderreceipt.model.dtos.OrderRequestDTO;
import br.com.project.orderreceipt.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("order-receipt")
public class OrderReceiptController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        log.info("POST /order-receipt {}", orderRequestDTO);
        orderService.processOrder(orderRequestDTO);
        URI location = URI.create(String.format("/order-receipt/%s", orderRequestDTO.getProductCode()));
        return ResponseEntity.created(location).build();
    }
}
