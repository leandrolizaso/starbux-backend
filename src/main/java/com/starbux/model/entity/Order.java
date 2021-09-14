package com.starbux.model.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    @OneToMany
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    List<Item> items = new ArrayList<>();
    Status status = Status.OPEN;
    Double discount = 0D;
    Integer price = 0;
    String customer;

    public enum Status{
        OPEN,
        CLOSED;
    }

    public Order addItem(Item item) {
        if(this.status == Status.CLOSED){
            throw new IllegalStateException("This order is already closed");
        }
        items.add(item);
        price += item.getTotalPrice();
        return this;
    }

    public Double getTotal(){
        return price - discount;
    }

    public Integer getCheaperDrinkPrice() {
        return items.stream().mapToInt(drink -> drink.getTotalPrice()).min().orElse(0);
    }

    public Integer getItemCount() {
        return items.size();
    }

    public void closeOrder(){
        status = Status.CLOSED;
    }

}
