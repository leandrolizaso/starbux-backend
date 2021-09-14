package com.starbux.controller;

import com.starbux.dto.OrderDto;
import com.starbux.dto.request.OrderRequestDto;
import com.starbux.dto.StarbuxAppResponse;
import com.starbux.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    public static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/customer/order")
    public ResponseEntity<StarbuxAppResponse<OrderDto>> createOrder(@RequestBody OrderRequestDto orderDto) {

        StarbuxAppResponse<OrderDto> response = new StarbuxAppResponse<>();
        OrderDto dto = customerService.createOrder(orderDto);

        logger.info("Order created with id: " + dto.getId());

        response.setData(dto);
        return  ResponseEntity.status(201).body(response);
    }

    @PostMapping("/customer/order/{id}/drink")
    public ResponseEntity<StarbuxAppResponse<OrderDto>>  addDrink(@PathVariable("id") Integer id, @RequestBody OrderRequestDto orderDto) {

        StarbuxAppResponse<OrderDto> response = new StarbuxAppResponse<>();
        OrderDto dto = customerService.addItemToOrder(id, orderDto);

        logger.info("Drink added to order with id: " + dto.getId());

        response.setData(dto);
        return  ResponseEntity.status(201).body(response);
    }

    @PutMapping("/customer/order/{id}")
    public ResponseEntity<StarbuxAppResponse<OrderDto>> closeOrder(@PathVariable("id") Integer id) {

        StarbuxAppResponse<OrderDto> response = new StarbuxAppResponse<>();
        OrderDto dto = customerService.closeOrder(id);

        logger.info("Finished order with id: " + dto.getId());

        response.setData(dto);
        return ResponseEntity.ok(response);
    }
}
