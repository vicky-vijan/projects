package au.com.dius.shopping.util;

import au.com.dius.shopping.domain.Catalogue;
import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.SKU;
import au.com.dius.shopping.model.BulkDiscountedRule;
import au.com.dius.shopping.model.FreeOfChargeRule;
import au.com.dius.shopping.model.BasePricingRule;
import au.com.dius.shopping.model.ThreeForTwoRule;

import java.util.*;

/**
 * Loading the catalog and pricing rules for items sold with initial values.
 */

public enum LoaderSingletonClass {

        SINGLE_INSTANCE;

        private Catalogue catalogue;
        private Map<SKU, BasePricingRule> pricingRules = new HashMap<>();

        LoaderSingletonClass() {

            Set<Item> items = new HashSet<>();
            items.add(new Item(SKU.ipd, "Super iPad", 549.99));
            items.add(new Item(SKU.mbp, "MacBook Pro", 1399.99));
            items.add(new Item(SKU.atv, "Apple TV", 109.50));
            items.add(new Item(SKU.vga, "VGA adapter", 30.00));
            catalogue = new Catalogue(items);

            pricingRules.put(SKU.atv, new ThreeForTwoRule(SKU.atv));
            pricingRules.put(SKU.ipd, new BulkDiscountedRule(SKU.ipd, 4, 499.99));
            pricingRules.put(SKU.mbp, new FreeOfChargeRule(SKU.mbp, SKU.vga));
            pricingRules.put(SKU.vga, new FreeOfChargeRule(SKU.mbp, SKU.vga));

        }

        /**
         * @return catalogue
         */
        public Catalogue getCatalogue() {
            return catalogue;
        }

        /**
         * @return pricing rules
         */
        public Map<SKU, BasePricingRule> getPricingRules() {
            return Collections.unmodifiableMap(pricingRules);
        }

    }


