package service;


import com.starbux.dto.*;
import com.starbux.dto.request.DrinkRequestDto;
import com.starbux.dto.request.ToppingRequestDto;
import com.starbux.exception.NoToppingException;
import com.starbux.exception.ParameterException;
import com.starbux.model.entity.Drink;
import com.starbux.model.entity.Topping;
import com.starbux.repository.DrinkRepository;
import com.starbux.repository.OrderRepository;
import com.starbux.repository.ToppingRepository;
import com.starbux.service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {AdminService.class, DrinkRepository.class, ToppingRepository.class})
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @MockBean
    DrinkRepository drinkRepository;

    @MockBean
    ToppingRepository toppingRepository;

    @MockBean
    OrderRepository orderRepository;

    @Test
    public void createNewTopping(){
        ToppingRequestDto requestDto = new ToppingRequestDto();
        requestDto.setName("coconut milk");
        requestDto.setPrice(3);

        when(toppingRepository.save(any(Topping.class))).then(AdditionalAnswers.returnsFirstArg());

        ToppingDto dto = adminService.createTopping(requestDto);

        Assertions.assertEquals("coconut milk", dto.getName());
        Assertions.assertEquals(3, dto.getPrice());

    }

    @Test
    public void updateExistingTopping(){
        ToppingRequestDto requestDto = new ToppingRequestDto();
        requestDto.setName("milk");
        requestDto.setPrice(2);

        Topping topping = new Topping("coconut Milk", 3);
        topping.setId(1);

        when(toppingRepository.findByIdAndDeleted(any(Integer.class), any(boolean.class))).thenReturn(topping);
        when(toppingRepository.save(any(Topping.class))).then(AdditionalAnswers.returnsFirstArg());

        ToppingDto dto = adminService.updateTopping(requestDto, 1);

        Assertions.assertEquals("milk", dto.getName());
        Assertions.assertEquals(2, dto.getPrice());
        Assertions.assertEquals(1, dto.getId());

    }

    @Test
    public void deleteNotExistingToppingThrowsException(){
        when(toppingRepository.findById(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(ParameterException.class, () -> {
            adminService.deleteTopping(1);
        });

    }

    @Test
    public void createNewDrink(){
        DrinkRequestDto drinkDto = new DrinkRequestDto();
        drinkDto.setName("Green tea");
        drinkDto.setPrice(7);

        when(drinkRepository.save(any(Drink.class))).then(AdditionalAnswers.returnsFirstArg());

        DrinkDto dto = adminService.createDrink(drinkDto);

        Assertions.assertEquals("Green tea", dto.getName());
        Assertions.assertEquals(7, dto.getPrice());

    }

    @Test
    public void updateExistingDrink(){
        String name = "Green tea";
        Integer price = 7;

        DrinkRequestDto drinkDto = new DrinkRequestDto();
        drinkDto.setName(name);
        drinkDto.setPrice(price);

        Drink drink = new Drink(name, price);
        drink.setId(1);

        when(drinkRepository.findByIdAndDeleted(any(Integer.class), any(boolean.class))).thenReturn(drink);
        when(drinkRepository.save(any(Drink.class))).then(AdditionalAnswers.returnsFirstArg());

        DrinkDto dto = adminService.updateDrink(drinkDto, 1);

        Assertions.assertEquals("Green tea", dto.getName());
        Assertions.assertEquals(7, dto.getPrice());
        Assertions.assertEquals(1, dto.getId());

    }

    @Test
    public void deleteNotExistingDrinkThrowsException(){

        when(drinkRepository.findById(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(ParameterException.class, () -> {
            adminService.deleteDrink(1);
        });

    }

    @Test
    public void getReportByCustomer(){

        String name = "llizaso";

        when(orderRepository.totalByCustomer(any(String.class))).thenReturn(124D);

        CustomerDto dto = adminService.ordersByCustomer(name);

        Assertions.assertEquals("llizaso", dto.getUsername());
        Assertions.assertEquals(124D, dto.getTotalAmount());
    }

    @Test
    public void getMostOrderedTopping(){
        List<Topping> toppings = new ArrayList<>();

        toppings.add(new Topping("milk", 1));
        toppings.add(new Topping("cacao", 4));


        when(toppingRepository.findMostUsed()).thenReturn(toppings);

        ToppingDto dto = adminService.mostUsedToppingReport();

        Assertions.assertEquals("milk", dto.getName());
        Assertions.assertEquals(1, dto.getPrice());
    }

    @Test
    public void getMostOrderedToppingThrowEx(){

        when(toppingRepository.findMostUsed()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NoToppingException.class, () -> {
            adminService.mostUsedToppingReport();
        });

    }

    @Test
    public void updateDrinkThrowEx(){

        when(drinkRepository.findById(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(ParameterException.class, () -> {
            adminService.updateDrink(new DrinkRequestDto(), 1);
        });

    }

    @Test
    public void updateToppingThrowEx(){

        when(toppingRepository.findById(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(ParameterException.class, () -> {
            adminService.updateTopping(new ToppingRequestDto(), 1);
        });

    }

    @Test
    public void deleteDrinkThrowEx(){

        when(drinkRepository.existsById(any(Integer.class))).thenReturn(false);

        Assertions.assertThrows(ParameterException.class, () -> {
            adminService.deleteDrink(1);
        });

    }

    @Test
    public void deleteToppingThrowEx(){

        when(toppingRepository.existsById(any(Integer.class))).thenReturn(false);

        Assertions.assertThrows(ParameterException.class, () -> {
            adminService.deleteTopping(1);
        });

    }

}
