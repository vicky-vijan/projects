package au.com.dius.shopping;

import au.com.dius.shopping.domain.ItemData;
import au.com.dius.shopping.model.BulkDiscountedRule;
import au.com.dius.shopping.model.FreeOfChargeRule;
import au.com.dius.shopping.domain.SKU;
import au.com.dius.shopping.domain.Catalogue;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.model.BasePricingRule;
import au.com.dius.shopping.model.ThreeForTwoRule;
import au.com.dius.shopping.util.LoaderSingletonClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for various offers on pricing rules
 */
public class OfferRulesUnitTest {

    LoaderSingletonClass singleton = LoaderSingletonClass.SINGLE_INSTANCE;
    private final Catalogue catalogue = singleton.getCatalogue();

    @Test
    public void testCheckItemsInCatalogue() {

        assertEquals(549.99, catalogue.getItem(SKU.ipd).getPrice(), 0.0);
        assertEquals("VGA adapter", catalogue.getItem(SKU.vga).getName());

        assertEquals(4, catalogue.getAllItems().size());
    }

    @Test
    public void testThreeForTwoRule() {

        BasePricingRule rule = new ThreeForTwoRule(SKU.atv);

        Map<Item, ItemData> cart = new HashMap<>();

        Item appleTv = catalogue.getItem(SKU.atv);
        ItemData stats = new ItemData();

        cart.put(appleTv, stats);
        rule.applyOn(cart);
        assertEquals(0.0, cart.get(appleTv).getAmount(), 0.0);

        // cart now has 1 appleTv
        stats.incrementQuantity(1);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 1 appleTv
        assertEquals(appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

        // cart now has 2 appleTv
        stats.incrementQuantity(1);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 2 appleTv
        assertEquals(2 * appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

        // cart now has 3 appleTv
        stats.incrementQuantity(1);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 2 appleTv only
        assertEquals(2 * appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

        // cart now has 5 appleTv
        stats.incrementQuantity(2);
        cart.put(appleTv, stats);
        rule.applyOn(cart);
        // customer has to pay for 4 appleTv only
        assertEquals(4 * appleTv.getPrice(), cart.get(appleTv).getAmount(), 0.0);

    }

    @Test
    public void testBulkDiscountedRule() {

        BasePricingRule rule = new BulkDiscountedRule(SKU.ipd, 4, 499.99);

        Map<Item, ItemData> cart = new HashMap<>();

        Item superIPad = catalogue.getItem(SKU.ipd);
        ItemData stats = new ItemData();

        cart.put(superIPad, stats);
        rule.applyOn(cart);
        assertEquals(0.0, cart.get(superIPad).getAmount(), 0.0);

        // cart now has 1 superIPad
        stats.incrementQuantity(1);
        cart.put(superIPad, stats);
        rule.applyOn(cart);
        // customer has to pay full price per superIPad
        assertEquals(superIPad.getPrice(), cart.get(superIPad).getAmount(), 0.0);

        // cart now has 2 superIPad
        stats.incrementQuantity(1);
        cart.put(superIPad, stats);
        rule.applyOn(cart);
        // customer has to pay full price per superIPad
        assertEquals(2 * superIPad.getPrice(), cart.get(superIPad).getAmount(), 0.0);

        // cart now has 6 superIPad
        stats.incrementQuantity(4);
        cart.put(superIPad, stats);
        rule.applyOn(cart);
        // customer has to pay discounted price per superIPad
        assertEquals((6 * 499.99), cart.get(superIPad).getAmount(), 0.0);

    }

    @Test
    public void testFreeOfChargePricingRule() {

        BasePricingRule rule = new FreeOfChargeRule(SKU.mbp, SKU.vga);

        Map<Item, ItemData> cart = new HashMap<>();

        Item macBookPro = catalogue.getItem(SKU.mbp);
        ItemData macBookProStats = new ItemData();

        Item vgaAdaptor = catalogue.getItem(SKU.vga);
        ItemData vgaAdaptorStats = new ItemData();

        cart.put(macBookPro, macBookProStats);
        cart.put(vgaAdaptor, vgaAdaptorStats);

        rule.applyOn(cart);
        assertEquals(0.0, cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 1 macBookPro and 0 vgaAdaptor
        macBookProStats.incrementQuantity(1);
        rule.applyOn(cart);
        assertEquals(macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 0 macBookPro and 1 vgaAdaptor
        macBookProStats.incrementQuantity(-1);
        vgaAdaptorStats.incrementQuantity(1);
        rule.applyOn(cart);
        assertEquals(0.0, cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(vgaAdaptor.getPrice(), cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 1 macBookPro and 1 vgaAdaptor
        macBookProStats.incrementQuantity(1);
        rule.applyOn(cart);
        assertEquals(macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 3 macBookPro and 1 vgaAdaptor
        macBookProStats.incrementQuantity(2);
        rule.applyOn(cart);
        assertEquals(3 * macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(0.0, cart.get(vgaAdaptor).getAmount(), 0.0);

        // cart now has 3 macBookPro and 5 vgaAdaptor
        vgaAdaptorStats.incrementQuantity(4);
        rule.applyOn(cart);
        assertEquals(3 * macBookPro.getPrice(), cart.get(macBookPro).getAmount(), 0.0);
        assertEquals(2 * vgaAdaptor.getPrice(), cart.get(vgaAdaptor).getAmount(), 0.0);

    }

}
