package com.starbux.model;

import com.starbux.model.entity.Order;
import java.util.Arrays;
import java.util.List;

public class DiscountService {

    List<Discount> discounts = Arrays.asList(Discount.values());

    public void applyDiscount(Order order){
        Double maximumDiscount = discounts.stream().mapToDouble(discount -> discount.calculateFor(order)).max().orElse(0);
        order.setDiscount(maximumDiscount);
    }
}
