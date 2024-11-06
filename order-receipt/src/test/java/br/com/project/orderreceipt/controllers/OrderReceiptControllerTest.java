package br.com.project.orderreceipt.controllers;

import br.com.project.orderreceipt.infra.mqueue.RequestProducerOrder;
import br.com.project.orderreceipt.model.dtos.OrderRequestDTO;
import br.com.project.orderreceipt.model.dtos.ProductRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestProducerOrder requestPublishedOrder;

    @Test
    void postSuccess() throws Exception {
        // Arrange
        OrderRequestDTO orderRequestDTO = getOrderRequestDTO();

        // Act
        doNothing().when(requestPublishedOrder).publishOrder(any());

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/order-receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void postGetDuplicate() throws Exception {
        // Arrange
        OrderRequestDTO orderRequestDTO = getOrderRequestDTO();
        orderRequestDTO.setProductCode("ORDER123");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/order-receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(409))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Order already exists with code: ORDER123"));
    }

    private static OrderRequestDTO getOrderRequestDTO() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setProductCode("ORDER124");

        ProductRequestDTO product1 = new ProductRequestDTO();
        product1.setProductName("Product 1");
        product1.setQuantity(2);
        product1.setUnitPrice(new BigDecimal("10.50"));

        ProductRequestDTO product2 = new ProductRequestDTO();
        product2.setProductName("Product 2");
        product2.setQuantity(1);
        product2.setUnitPrice(new BigDecimal("20.00"));

        orderRequestDTO.setProducts(List.of(product1, product2));
        return orderRequestDTO;
    }
}
