package controller;

import com.starbux.controller.AdminController;
import com.starbux.converter.DrinkConverter;
import com.starbux.converter.ToppingConverter;
import com.starbux.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starbux.dto.request.DrinkRequestDto;
import com.starbux.dto.request.ToppingRequestDto;
import com.starbux.exception.ParameterException;
import com.starbux.service.AdminService;
import model.fixture.DrinkFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@WebMvcTest(AdminController.class)
@ContextConfiguration(classes = {AdminController.class})
public class AdminControllerTest {

    @MockBean
    AdminService adminService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Retrieves the most used topping")
    void getToppingReportTest() throws Exception {

        ToppingDto topping = ToppingConverter.toToppingDto(DrinkFixture.milk());

        when(adminService.mostUsedToppingReport()).thenReturn(topping);

        mockMvc.perform(get("/admin/report/topping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.name").value("milk"));


    }

    @Test
    @DisplayName("Retrieves the amount of orders from a customer")
    void getCustomerReportTest() throws Exception {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setTotalAmount(1300);
        customerDto.setUsername("llizaso");

        when(adminService.ordersByCustomer(any(String.class))).thenReturn(customerDto);

        mockMvc.perform(get("/admin/report/customer/llizaso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.totalAmount").value(1300));


    }

    @Test
    @DisplayName("Creates and retrieves a topping")
    void addTopping() throws Exception {

        ToppingDto topping = ToppingConverter.toToppingDto(DrinkFixture.milk());
        when(adminService.createTopping(any(ToppingRequestDto.class))).thenReturn(topping);

        mockMvc.perform(post("/admin/topping")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(topping)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.name").value("milk"));
    }

    @Test
    @DisplayName("Updates a topping")
    void updateTopping() throws Exception {

        ToppingDto topping = ToppingConverter.toToppingDto(DrinkFixture.milk());
        when(adminService.updateTopping(any(ToppingRequestDto.class), any(Integer.class))).thenReturn(topping);

        mockMvc.perform(put("/admin/topping/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(topping)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.name").value("milk"));
    }

    @Test
    @DisplayName("Delete a topping")
    void deleteTopping() throws Exception {

        mockMvc.perform(delete("/admin/topping/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty());
    }

    @Test
    @DisplayName("Creates and retrieves a drink")
    void addDrink() throws Exception {

        DrinkDto drink = DrinkConverter.toDrinkDto(DrinkFixture.blackCoffee().getDrink());
        when(adminService.createDrink(any(DrinkRequestDto.class))).thenReturn(drink);

        mockMvc.perform(post("/admin/drink")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(drink)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.name").value("black coffee"));
    }

    @Test
    @DisplayName("Updates a drink")
    void updateDrink() throws Exception {

        DrinkDto drink = DrinkConverter.toDrinkDto(DrinkFixture.blackCoffee().getDrink());
        when(adminService.updateDrink(any(DrinkRequestDto.class), any(Integer.class))).thenReturn(drink);

        mockMvc.perform(put("/admin/drink/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(drink)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("data").isNotEmpty())
                .andExpect(jsonPath("data.name").value("black coffee"));
    }

    @Test
    @DisplayName("Delete a topping")
    void deleteDrink() throws Exception {

        mockMvc.perform(delete("/admin/drink/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty());
    }

    @Test
    @DisplayName("update a topping that do not exists")
    void updateToppingThatNotExist() throws Exception {

        when(adminService.updateTopping(any(ToppingRequestDto.class),any(Integer.class))).thenThrow(new ParameterException("There is no topping with id: 1"));

        mockMvc.perform(put("/admin/drink/1"))
                .andExpect(status().is(400));
    }


}
