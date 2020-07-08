package au.com.dius.shopping.util;

import au.com.dius.shopping.domain.Item;
import au.com.dius.shopping.domain.ItemData;
import au.com.dius.shopping.domain.SKU;

import java.util.Map;

/**
 * A class to hold common utility methods for the application.
 */
public class CommonUtils {

    /**
     * Finds item in the given cart. If not found in cart, looks up in the catalogue
     *
     * @param sku  search item
     * @param cart shopping cart
     * @return
     */
    public static Item findItemInCart(final SKU sku, final Map<Item, ItemData> cart) {
        LoaderSingletonClass singleton = LoaderSingletonClass.SINGLE_INSTANCE;
        return sku == null ? null : cart.keySet().stream().filter(item -> item.getSKU() == sku)
                .findFirst()
                .orElse(singleton.getCatalogue().getItem(sku));
    }
}
