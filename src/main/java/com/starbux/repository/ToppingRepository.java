package com.starbux.repository;

import com.starbux.model.entity.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ToppingRepository extends JpaRepository <Topping, Long> {

    Set<Topping> findAllByIdInAndDeleted(List<Integer> id, boolean deleted);

    Topping findById(Integer id);
    Boolean existsById(Integer id);

    @Query(value = "SELECT t FROM Order o JOIN o.items d JOIN d.toppings t WHERE t.deleted = false GROUP BY t.id ORDER BY count(t.id) DESC")
    List<Topping> findMostUsed();

    Topping findByIdAndDeleted(Integer id, boolean deleted);
}
