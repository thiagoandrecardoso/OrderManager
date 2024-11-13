package br.com.project.orderprocess.controller;

import br.com.project.orderprocess.feignclient.OrderReceiptCalculateClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@EnableAutoConfiguration
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderProcessControllerTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderReceiptCalculateClient orderReceiptCalculateClient;

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        postgreSQLContainer.start();
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("order.receipt.url", () -> "http://localhost:8080/order-receipt-calculate");
    }

    @Test
    @Sql(scripts = "/test-script/script-get-test.sql")
    public void getTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/order-process/P001")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productCode").value("P001"))
                .andExpect(jsonPath("$.status").value("RECEIVED"))
                .andExpect(jsonPath("$.totalValue").value(100.00));
    }

    @Test
    @Sql(scripts = "/test-script/script-post-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void postTestSuccess() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/order-process")
                .contentType(MediaType.APPLICATION_JSON)
                .content("P004")
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andExpect(status().isOk());

        ResultActions resultGetByProductOrder = mockMvc.perform(MockMvcRequestBuilders
                .get("/order-process/P004")
                .accept(MediaType.APPLICATION_JSON));

        resultGetByProductOrder.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CALCULATED"));
    }

    @Test
    @Sql(scripts = "/test-script/script-get-all-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getAllByFilterTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/order-process")
                .param("orderId", "1")
                .param("page", "0")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
