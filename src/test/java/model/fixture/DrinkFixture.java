package model.fixture;

import com.starbux.model.entity.Drink;
import com.starbux.model.entity.Item;
import com.starbux.model.entity.Topping;

public class DrinkFixture {

    public static Topping milk(){
        return new Topping("milk", 2);
    }

    public static Topping chocolateSauce() {
        return new Topping("chocolate sauce", 5);
    }

    public static Topping hazelnutSyrup(){
        return new Topping("hazelnut syrup", 3);
    }

    public static Topping lemon(){
        return new Topping("lemon", 2);
    }

    public static Item blackCoffee(){
        return new Item(new Drink("black coffee", 4));
    }

    public static Item latte(){
        return new Item(new Drink("latte", 5));
    }

    public static Item mocha(){
        return new Item(new Drink("mocha", 6));
    }

    public static Item tea(){
        return new Item(new Drink("tea", 3));
    }

    public static Item teaWithLemmon(){
        return tea().addTopping(lemon());
    }

    public static Item blackCoffeWithMilk(){
        return blackCoffee().addTopping(milk());
    }

    public static Item mochaHazelnut(){
        return mocha().addTopping(hazelnutSyrup());
    }

    public static Item latteWithChocolate(){
        return latte().addTopping(chocolateSauce());
    }
}
