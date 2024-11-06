package br.com.project.orderreceipt.services;

import br.com.project.orderreceipt.model.dtos.OrderRequestDTO;

public interface IOrderService {
    void processOrder(OrderRequestDTO orderRequestDTO);
}
