package model.fixture;

import com.starbux.model.entity.Order;
import static model.fixture.DrinkFixture.*;

public class OrderFixture {

    public static Order cheapOrder(){
        return new Order().addItem(blackCoffee()).addItem(tea()).addItem(latte());
    }

    public static Order expensiveOrder(){
        return new Order().addItem(latteWithChocolate()).addItem(mochaHazelnut());
    }

    public static Order bigOrderByPrice(){
        return new Order().addItem(latteWithChocolate()).addItem(tea()).addItem(tea()).addItem(tea());
    }

    public static Order bigOrderByQuantity(){
        return new Order().addItem(mochaHazelnut()).addItem(mochaHazelnut()).addItem(mochaHazelnut());
    }

    public static Order orderWithUser(){
        Order order = new Order();
        order.setCustomer("llizaso");
        return order.addItem(latteWithChocolate()).addItem(mochaHazelnut());
    }
}
