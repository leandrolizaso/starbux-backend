package com.starbux.service;

import com.starbux.converter.OrderConverter;
import com.starbux.dto.OrderDto;
import com.starbux.dto.request.OrderRequestDto;
import com.starbux.exception.ParameterException;
import com.starbux.model.DiscountService;
import com.starbux.model.entity.Drink;
import com.starbux.model.entity.Item;
import com.starbux.model.entity.Order;
import com.starbux.repository.DrinkRepository;
import com.starbux.repository.OrderRepository;
import com.starbux.repository.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DrinkRepository drinkRepository;
    @Autowired
    ToppingRepository toppingRepository;



    public OrderDto createOrder(OrderRequestDto orderDto) throws ParameterException {

        if(orderDto.getCustomer() == null){
            throw new ParameterException("The customer field is mandatory");
        }

        Order order = new Order();
        Item item = new Item();
        item.setDrink(drinkRepository.findByIdAndDeleted(orderDto.getDrinkId(), false));
        item.setToppings(toppingRepository.findAllByIdInAndDeleted(orderDto.getToppingsId(), false));

        if(item.getDrink() == null){
            throw new ParameterException("There is no drink with id: " + orderDto.getDrinkId());
        }

        item.setPriceCalculatedPrice();

        order.addItem(item);
        order.setCustomer(orderDto.getCustomer());
        DiscountService discountService = new DiscountService();
        discountService.applyDiscount(order);

        return OrderConverter.toOrderDto(orderRepository.save(order));
    }

    public OrderDto closeOrder(Integer id) throws ParameterException {

        Order order = orderRepository.findById(id);

        if(order == null){
            throw new ParameterException("There is no order with id: " + id);
        }

        order.setStatus(Order.Status.CLOSED);

        return OrderConverter.toOrderDto(orderRepository.save(order));
    }

    public OrderDto addItemToOrder(Integer id, OrderRequestDto orderDto) throws ParameterException {

        Order order = orderRepository.findById(id);
        if(order == null){
            throw new ParameterException("There is no order with id: " + id);
        }

        Drink drink = drinkRepository.findByIdAndDeleted(orderDto.getDrinkId(), false);

        if(drink == null){
            throw new ParameterException("There is no drink with id: " + orderDto.getDrinkId());
        }

        Item item = new Item();
        item.setDrink(drink);
        item.setToppings(toppingRepository.findAllByIdInAndDeleted(orderDto.getToppingsId(), false));
        item.setPriceCalculatedPrice();

        order.addItem(item);

        DiscountService discountService = new DiscountService();
        discountService.applyDiscount(order);

        return OrderConverter.toOrderDto(orderRepository.save(order));
    }
}
