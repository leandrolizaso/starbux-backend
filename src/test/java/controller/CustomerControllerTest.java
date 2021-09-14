package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.controller.CustomerController;
import com.starbux.converter.OrderConverter;
import com.starbux.dto.request.OrderRequestDto;
import com.starbux.exception.ParameterException;
import com.starbux.exception.handler.ResponseEntityExceptioHandler;
import com.starbux.model.entity.Order;
import com.starbux.service.CustomerService;
import model.fixture.DrinkFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static model.fixture.OrderFixture.orderWithUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@ContextConfiguration(classes = {CustomerController.class, ResponseEntityExceptioHandler.class})
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();


    @Test
    @DisplayName("Creates and retrieves an order with a drink")
    void createOrder() throws Exception {

        OrderRequestDto dto = new OrderRequestDto();
        dto.setCustomer("llizaso");
        dto.setDrinkId(1);
        List<Integer> toppingIds = new ArrayList<>();
        toppingIds.add(1);
        dto.setToppingsId(toppingIds);

        Order order = orderWithUser();

        when(customerService.createOrder(dto)).thenReturn(OrderConverter.toOrderDto(order));

        mockMvc.perform(post("/customer/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.customer").value("llizaso"));
    }

    @Test
    @DisplayName("Creates and retrieves an order with a new drink")
    void addDrinkToOrder() throws Exception {

        OrderRequestDto dto = new OrderRequestDto();
        dto.setCustomer("llizaso");
        dto.setDrinkId(1);
        List<Integer> toppingIds = new ArrayList<>();
        toppingIds.add(1);
        dto.setToppingsId(toppingIds);

        Order updatedOrder = orderWithUser();
        updatedOrder.addItem(DrinkFixture.blackCoffee());

        when(customerService.addItemToOrder(any(Integer.class),any(OrderRequestDto.class))).thenReturn(OrderConverter.toOrderDto(updatedOrder));

        mockMvc.perform(post("/customer/order/1/drink")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.customer").value("llizaso"));
    }

    @Test
    @DisplayName("Close the order")
    void closeOrder() throws Exception {

        Order updatedOrder = orderWithUser();
        updatedOrder.addItem(DrinkFixture.blackCoffee());

        when(customerService.closeOrder(1)).thenReturn(OrderConverter.toOrderDto(updatedOrder));

        mockMvc.perform(put("/customer/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.customer").value("llizaso"));
    }

    @Test
    @DisplayName("Close a order that do not exists")
    void closeOrderThatNotExist() throws Exception {

        when(customerService.closeOrder(1)).thenThrow(new ParameterException("There is no order with id: 1"));

        mockMvc.perform(put("/customer/order/1"))
                .andExpect(status().is(400));
    }
}
