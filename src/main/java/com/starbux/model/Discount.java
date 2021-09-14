package com.starbux.model;

import com.starbux.model.entity.Order;

public enum Discount {
    ITEM_QUANTITY {
        @Override
        public Float calculateFor(Order order) {
            return order.getItemCount() > 2 ? order.getCheaperDrinkPrice() : 0F;
        }
    },
    ORDER_PRICE {
        @Override
        public Float calculateFor(Order order) {
            return order.getPrice() > 12 ? order.getPrice() * 0.25F : 0F;
        }
    };

    public abstract Float calculateFor(Order order);
}
