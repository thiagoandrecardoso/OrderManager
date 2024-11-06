package br.com.project.orderprocess.feignclient;

import br.com.project.orderprocess.model.dtos.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-receipt-calculate", url = "${order.receipt.url}")
public interface OrderReceiptCalculateClient {
    @PostMapping(consumes = "application/json")
    void calculate(@RequestBody OrderDTO orderDTO);
}
