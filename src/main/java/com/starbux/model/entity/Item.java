package com.starbux.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    Drink drink;

    Integer price;

    @ManyToMany
    Set<Topping> toppings;

    public Item(Drink drink){
        this.drink = drink;
        this.toppings = new HashSet<>();
    }

    public Item(Integer id, Drink drink){
        this.id = id;
        this.drink = drink;
    }

    public Integer getTotalPrice(){
        return drink.getPrice() + toppings.stream().mapToInt(topping -> topping.getPrice()).sum();
    }

    public Item addTopping(Topping topping){
        toppings.add(topping);
        return this;
    }

    public void setPriceCalculatedPrice() {
        price = this.getTotalPrice();
    }
}
