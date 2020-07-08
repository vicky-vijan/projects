package au.com.dius.shopping;

import au.com.dius.shopping.domain.SKU;
import au.com.dius.shopping.model.BasePricingRule;
import au.com.dius.shopping.service.CheckoutService;
import au.com.dius.shopping.util.LoaderSingletonClass;
import org.junit.Test;

import java.util.Map;

import static au.com.dius.shopping.domain.SKU.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Checkout Service operations
 */
public class CheckoutServiceUnitTest {

    LoaderSingletonClass singleton = LoaderSingletonClass.SINGLE_INSTANCE;
    private final Map<SKU, BasePricingRule> pricingRules = singleton.getPricingRules();

    @Test
    public void testCheckoutScenario1() {

        CheckoutService checkout = new CheckoutService(pricingRules);
        checkout.scan(atv);
        checkout.scan(atv);
        checkout.scan(atv);
        checkout.scan(vga);
        Double total = checkout.total();

        assertEquals(249.00, total, 0.0);

    }

    @Test
    public void testCheckoutScenario2() {

        CheckoutService checkout = new CheckoutService(pricingRules);
        checkout.scan(atv)
                .scan(ipd)
                .scan(ipd)
                .scan(atv)
                .scan(ipd)
                .scan(ipd)
                .scan(ipd);
        Double total = checkout.total();

        assertEquals(2718.95, total, 0.0);

    }

    @Test
    public void testCheckoutScenario3() {

        CheckoutService checkout = new CheckoutService(pricingRules);
        checkout.scan(mbp)
                .scan(vga)
                .scan(ipd);
        Double total = checkout.total();

        assertEquals(1949.98, total, 0.0);

    }

    @Test(expected = RuntimeException.class)
    public void testCheckoutNullItem() {

        CheckoutService checkout = new CheckoutService(pricingRules);
        checkout.scan(null);

    }

    @Test(expected = RuntimeException.class)
    public void testCheckoutItemNotInCatalogue() {

        CheckoutService checkout = new CheckoutService(pricingRules);
        checkout.scan(watch);

    }

    @Test
    public void testCheckoutCartClearedAfterTotal() {

        CheckoutService checkout = new CheckoutService(pricingRules);
        checkout.scan(mbp)
                .scan(vga);
        Double total = checkout.total();

        assertEquals(1399.99, total, 0.0);

        // old cart cleared. ready to serve next customer.
        checkout.scan(atv)
                .scan(atv)
                .scan(atv);

        total = checkout.total();

        assertEquals(219.00, total, 0.0);

    }


}
