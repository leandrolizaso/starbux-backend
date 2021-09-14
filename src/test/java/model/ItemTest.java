package model;


import com.starbux.model.entity.Item;
import com.starbux.model.entity.Topping;
import org.junit.jupiter.api.Test;
import static model.fixture.DrinkFixture.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ItemTest {

    @Test
    public void latteWithMilkPrice(){
        int expectedPrice = 7;

        Item latte = latte();
        Topping milk = milk();

        latte.addTopping(milk);
        //easily debug
        assertThat(latte.getTotalPrice(), is(expectedPrice));
    }

    @Test
    public void latteWithMilkAndChocolateSaucePrice(){
        int expectedPrice = 12;


        Item latteWithToppings = latte().addTopping(milk()).addTopping(chocolateSauce());

        assertThat(latteWithToppings.getTotalPrice(), is(expectedPrice));
    }

}
