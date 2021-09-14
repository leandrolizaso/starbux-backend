package com.starbux.controller;

import com.starbux.dto.*;
import com.starbux.dto.StarbuxAppResponse;
import com.starbux.dto.request.DrinkRequestDto;
import com.starbux.dto.request.ToppingRequestDto;
import com.starbux.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class AdminController {

    @Autowired
    AdminService adminService;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin/report/customer/{username}")
    public ResponseEntity<StarbuxAppResponse<CustomerDto> > createCostumerReport(@PathVariable("username") String username) {
        CustomerDto dto = adminService.ordersByCustomer(username);
        StarbuxAppResponse<CustomerDto> response = new StarbuxAppResponse<>(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/report/topping")
    public ResponseEntity<StarbuxAppResponse<ToppingDto>> createToppingReport() {
        ToppingDto dto = adminService.mostUsedToppingReport();;
        StarbuxAppResponse<ToppingDto> response = new StarbuxAppResponse<>();
        response.setData(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/topping")
    public ResponseEntity<StarbuxAppResponse<ToppingDto>> createTopping(@RequestBody ToppingRequestDto toppingDto) {
        ToppingDto dto = adminService.createTopping(toppingDto);
        StarbuxAppResponse<ToppingDto> response = new StarbuxAppResponse<>(dto);
        response.setData(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/admin/topping/{id}")
    public ResponseEntity<StarbuxAppResponse<ToppingDto>> updateTopping(@PathVariable("id") Integer id, @RequestBody ToppingRequestDto toppingDto) {

        ToppingDto dto = adminService.updateTopping(toppingDto, id);
        StarbuxAppResponse<ToppingDto> response = new StarbuxAppResponse<>();
        response.setData(dto);

        return  ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/admin/topping/{id}")
    public ResponseEntity<StarbuxAppResponse<String>> deleteTopping(@PathVariable("id") Integer id) {

        StarbuxAppResponse<String> response = new StarbuxAppResponse<>();
        adminService.deleteTopping(id);

        response.setData("Topping deleted");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/drink")
    public ResponseEntity<StarbuxAppResponse<DrinkDto>> createDrink(@RequestBody DrinkRequestDto drinkDto) {
        DrinkDto dto = adminService.createDrink(drinkDto);
        StarbuxAppResponse<DrinkDto> response = new StarbuxAppResponse<>(dto);
        return  ResponseEntity.status(201).body(response);
    }

    @PutMapping("/admin/drink/{id}")
    public ResponseEntity<StarbuxAppResponse<DrinkDto>> updateDrink(@PathVariable("id") Integer id, @RequestBody DrinkRequestDto drinkDto) {

        DrinkDto dto = adminService.updateDrink(drinkDto, id);
        StarbuxAppResponse<DrinkDto> response = new StarbuxAppResponse<>();
        response.setData(dto);
        return  ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/admin/drink/{id}")
    public ResponseEntity<StarbuxAppResponse<String>> deleteDrink(@PathVariable("id") Integer id) {

        StarbuxAppResponse<String> response = new StarbuxAppResponse<>();
        adminService.deleteDrink(id);

        response.setData("Drink deleted");

        return ResponseEntity.ok(response);
    }

}
