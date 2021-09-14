package com.starbux.repository;

import com.starbux.model.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

    Boolean existsById(Integer id);
    Drink findById(Integer id);
    Drink findByIdAndDeleted(Integer id, boolean deleted);

}
