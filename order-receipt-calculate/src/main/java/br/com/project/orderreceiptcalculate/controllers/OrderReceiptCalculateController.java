package br.com.project.orderreceiptcalculate.controllers;

import br.com.project.orderreceiptcalculate.model.dtos.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("order-receipt-calculate")
public class OrderReceiptCalculateController {

    private final ObjectMapper objectMapper;

    @PostMapping
    public void post(@RequestBody OrderDTO orderDTO) {
        try {
            String json = objectMapper.writeValueAsString(orderDTO);
            log.info("POST /order-receipt-calculate {}", json);
        } catch (Exception e) {
            log.error("Error converting OrderDTO to JSON", e);
        }
    }
}
