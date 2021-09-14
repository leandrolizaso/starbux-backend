package com.starbux.service;

import com.starbux.converter.DrinkConverter;
import com.starbux.converter.ToppingConverter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    ToppingRepository toppingRepository;
    @Autowired
    DrinkRepository drinkRepository;
    @Autowired
    OrderRepository orderRepository;

    private static Logger logger = LoggerFactory.getLogger(AdminService.class);

    public ToppingDto createTopping(ToppingRequestDto toppingDto) {
        Topping topping = new Topping(toppingDto.getName(), toppingDto.getPrice());
        return ToppingConverter.toToppingDto(toppingRepository.save(topping));
    }

    public ToppingDto updateTopping(ToppingRequestDto toppingDto, Integer id) throws ParameterException {
        Topping topping = toppingRepository.findByIdAndDeleted(id, false);

        if(topping == null){
            throw new ParameterException("There is no topping with id: " + id);
        }

        topping.setName(toppingDto.getName());
        topping.setPrice(toppingDto.getPrice());

        return ToppingConverter.toToppingDto(toppingRepository.save(topping));
    }

    public void deleteTopping(Integer id) throws ParameterException {
        Topping topping = toppingRepository.findByIdAndDeleted(id, false);

        if(topping == null){
            throw new ParameterException("There is no topping with id: " + id);
        }

        topping.setDeleted(true);

        toppingRepository.save(topping);
    }

    public DrinkDto createDrink(DrinkRequestDto drinkDto) {
        Drink drink = new Drink(drinkDto.getName(), drinkDto.getPrice());
        return DrinkConverter.toDrinkDto(drinkRepository.save(drink));
    }

    public void deleteDrink(Integer id) throws ParameterException {

        Drink drink = drinkRepository.findByIdAndDeleted(id, false);
        if(drink == null){
            throw new ParameterException("There is no drink with id: " + id);
        }

        drink.setDeleted(true);

        drinkRepository.save(drink);
    }


    public DrinkDto updateDrink(DrinkRequestDto drinkDto, Integer id) throws ParameterException {

        Drink drink = drinkRepository.findByIdAndDeleted(id, false);

        if(drink == null){
            throw new ParameterException("There is no drink with id: " + id);
        }

        drink.setName(drinkDto.getName());
        drink.setPrice(drinkDto.getPrice());

        return DrinkConverter.toDrinkDto(drinkRepository.save(drink));
    }

    public CustomerDto ordersByCustomer(String username) {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername(username);
        customerDto.setTotalAmount(orderRepository.totalByCustomer(username));

        return customerDto;
    }

    public ToppingDto mostUsedToppingReport() throws NoToppingException {
        List<Topping> toppings = toppingRepository.findMostUsed();

        if(toppings.isEmpty()){
            throw new NoToppingException("There is no ordered topping");
        }

        Topping topping =  toppings.get(0);
        return ToppingConverter.toToppingDto(topping);
    }
}
