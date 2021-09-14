package service;

import com.starbux.dto.OrderDto;
import com.starbux.dto.request.OrderRequestDto;
import com.starbux.exception.ParameterException;
import com.starbux.model.entity.Order;
import com.starbux.repository.DrinkRepository;
import com.starbux.repository.OrderRepository;
import com.starbux.repository.ToppingRepository;
import com.starbux.service.CustomerService;
import model.fixture.DrinkFixture;
import model.fixture.OrderFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CustomerService.class, DrinkRepository.class, ToppingRepository.class})
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    DrinkRepository drinkRepository;

    @MockBean
    ToppingRepository toppingRepository;

    @MockBean
    OrderRepository orderRepository;

    @Test
    public void createOrderWithOneDrinkAndTwoToppings(){
        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setCustomer("llizaso");
        orderDto.setDrinkId(1);
        List<Integer> toppings = Arrays.asList(new Integer[]{1, 2});
        orderDto.setToppingsId(toppings);

        when(drinkRepository.findByIdAndDeleted(any(Integer.class), any(boolean.class))).thenReturn(DrinkFixture.mocha().getDrink());
        when(toppingRepository.findAllByIdInAndDeleted(any(List.class), any(boolean.class))).thenReturn(DrinkFixture.mocha().getToppings());
        when(orderRepository.save(any(Order.class))).then(AdditionalAnswers.returnsFirstArg());

        OrderDto dto = customerService.createOrder(orderDto);

        Assertions.assertEquals(dto.getStatus(), Order.Status.OPEN);

    }

    @Test
    public void createOrderWithoutCustomerThrowsEx(){

        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setDrinkId(1);
        List<Integer> toppings = Arrays.asList(new Integer[]{1, 2});
        orderDto.setToppingsId(toppings);

        Assertions.assertThrows(ParameterException.class, () -> {
            customerService.createOrder(orderDto);
        });

    }

    @Test
    public void addDrinktoOrder(){
        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setCustomer("llizaso");
        orderDto.setDrinkId(1);
        List<Integer> toppings = Arrays.asList(new Integer[]{3, 4});
        orderDto.setToppingsId(toppings);

        Order order = OrderFixture.orderWithUser();

        when(drinkRepository.findByIdAndDeleted(any(Integer.class), any(boolean.class))).thenReturn(DrinkFixture.mocha().getDrink());
        when(toppingRepository.findAllByIdInAndDeleted(any(List.class), any(boolean.class))).thenReturn(DrinkFixture.mocha().getToppings());
        when(orderRepository.findById(any(Integer.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).then(AdditionalAnswers.returnsFirstArg());

        OrderDto dto = customerService.addItemToOrder(1, orderDto);

        Assertions.assertEquals(dto.getDrinks().size(), 3);
        Assertions.assertEquals(dto.getStatus(), Order.Status.OPEN);

    }

    @Test
    public void addDrinkToNotExistingOrderThrowsEx(){

        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setDrinkId(1);
        List<Integer> toppings = Arrays.asList(new Integer[]{1, 2});
        orderDto.setToppingsId(toppings);

        when(drinkRepository.findById(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(ParameterException.class, () -> {
            customerService.createOrder(orderDto);
        });

    }

    @Test
    public void closeOrder(){
        Order order = OrderFixture.orderWithUser();

        when(orderRepository.findById(any(Integer.class))).thenReturn(order);
        when(orderRepository.save(any(Order.class))).then(AdditionalAnswers.returnsFirstArg());

        OrderDto dto = customerService.closeOrder(1);

        Assertions.assertEquals(dto.getStatus(), Order.Status.CLOSED);

    }

    @Test
    public void closeNotExistingOrderThrowEx(){

        when(orderRepository.findById(any(Integer.class))).thenReturn(null);

        Assertions.assertThrows(ParameterException.class, () -> {
            customerService.closeOrder(1);
        });

    }
}
