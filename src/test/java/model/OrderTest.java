package model;


import com.starbux.model.entity.Order;
import org.junit.jupiter.api.Test;
import static model.fixture.DrinkFixture.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class OrderTest {

    @Test
    public void orderWithTwoCheapDrinks(){
        Integer expectedPrice = 11;

        Order cheapOrder= new Order().addItem(teaWithLemmon()).addItem(blackCoffeWithMilk());

        assertThat(cheapOrder.getPrice(), is(expectedPrice));
    }

    @Test
    public void orderWithTwoExpensiveDrink(){
        Integer expectedPrice = 19;

        Order expensiveOrder= new Order().addItem(latteWithChocolate()).addItem(mochaHazelnut());

        assertThat(expensiveOrder.getPrice(), is(expectedPrice));
    }

    @Test
    public void closeOrder(){
        Order.Status expectedStatus = Order.Status.CLOSED;
        Order order= new Order().addItem(latteWithChocolate()).addItem(mochaHazelnut());
        order.closeOrder();

        assertThat(order.getStatus(), is(expectedStatus));
    }

}
