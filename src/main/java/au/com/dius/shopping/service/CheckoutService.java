package au.com.dius.shopping.service;

import au.com.dius.shopping.domain.ItemData;
import au.com.dius.shopping.domain.SKU;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.model.BasePricingRule;
import au.com.dius.shopping.util.CommonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a POS (point of sales) checkout counter
 */
public class CheckoutService {

    private Map<Item, ItemData> cart = new HashMap<>();
    private Map<SKU, BasePricingRule> pricingRules;

    /**
     * @param pricingRules set of pricing rules applicable on items scanned at this checkout counter
     */
    public CheckoutService(final Map<SKU, BasePricingRule> pricingRules) {
        this.pricingRules = pricingRules;
    }

    /**
     * Item scanned is added to shopping cart.
     * Look up pricing rule applicable on the item scanned.
     * Apply pricing rule on the shopping cart.
     *
     * @param sku item scanned
     * @return
     */
    public CheckoutService scan(final SKU sku) {

        Item item = CommonUtils.findItemInCart(sku, cart);

        if (item == null) throw new RuntimeException("Item not found. Please check catalogue.");

        ItemData stats = cart.get(item);
        stats = stats != null ? stats : new ItemData();

        stats.incrementQuantity(1);
        stats.setAmount(item.getPrice());

        cart.put(item, stats);

        BasePricingRule pricingRule = pricingRules.get(sku);

        if (pricingRule != null) {
            pricingRule.applyOn(cart);
        }

        return this;

    }

    /**
     * Produces sum total of amounts of all items in the shopping cart.
     * Clears the shopping cart for next customer checkout.
     *
     * @return total amount payable by customer
     */
    public double total() {

        Double amountToPay = cart.values().stream().map(stat -> stat.getAmount()).mapToDouble(Double::doubleValue).sum();

        cart.clear();

        return amountToPay;

    }

}
