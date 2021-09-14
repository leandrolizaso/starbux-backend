package com.starbux.repository;

import com.starbux.model.entity.BaseDrink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseDrinkRepository extends JpaRepository<BaseDrink, Long> {
    void deleteById(Integer id);
    Boolean existsById(Integer id);
    BaseDrink findById(Integer id);
}
