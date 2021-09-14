package model;

import com.starbux.model.DiscountService;
import com.starbux.model.entity.Order;
import static model.fixture.OrderFixture.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class DiscountTest {

    DiscountService discountService = new DiscountService();

    @Test
    public void discountOnlyByQuantity(){
        Integer expectedPrice = 12;
        Double expectedDiscount = 3D;
        Double expectedTotal = expectedPrice - expectedDiscount;

        Order cheapOrder = cheapOrder();
        discountService.applyDiscount(cheapOrder);

        assertThat(cheapOrder.getPrice(), is(expectedPrice));
        assertThat(cheapOrder.getDiscount(), is(expectedDiscount));
        assertThat(cheapOrder.getTotal(), is(expectedTotal));
    }

    @Test
    public void discountOnlyByPrice(){
        Integer expectedPrice = 19;
        Double expectedDiscount = expectedPrice * 0.25;
        Double expectedTotal = expectedPrice - expectedDiscount;

        Order expensiveOrder = expensiveOrder();
        discountService.applyDiscount(expensiveOrder);

        assertThat(expensiveOrder.getPrice(), is(expectedPrice));
        assertThat(expensiveOrder.getDiscount(), is(expectedDiscount));
        assertThat(expensiveOrder.getTotal(), is(expectedTotal));
    }

    @Test
    public void betterDiscountByPriceWithBothDiscount(){
        Integer expectedPrice = 19;
        Double expectedDiscount = expectedPrice * 0.25D;
        Double expectedTotal = expectedPrice - expectedDiscount;

        Order bigOrderByPrice = bigOrderByPrice();
        discountService.applyDiscount(bigOrderByPrice);

        assertThat(bigOrderByPrice.getPrice(), is(expectedPrice));
        assertThat(bigOrderByPrice.getDiscount(), is(expectedDiscount));
        assertThat(bigOrderByPrice.getTotal(), is(expectedTotal));
    }

    @Test
    public void betterDiscountByQuantityWithBothDiscount(){
        Integer expectedPrice = 27;
        Double expectedDiscount = 9D;
        Double expectedTotal = expectedPrice - expectedDiscount;

        Order bigOrderByQuantity = bigOrderByQuantity();
        discountService.applyDiscount(bigOrderByQuantity);

        assertThat(bigOrderByQuantity.getPrice(), is(expectedPrice));
        assertThat(bigOrderByQuantity.getDiscount(), is(expectedDiscount));
        assertThat(bigOrderByQuantity.getTotal(), is(expectedTotal));
    }
}
