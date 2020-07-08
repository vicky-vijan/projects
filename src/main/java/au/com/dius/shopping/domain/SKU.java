package au.com.dius.shopping.domain;

/**
 * SKUs stored in enum pertaining to all the products.
 */
public enum SKU {

    ipd("Super iPad"), mbp("MacBook Pro"), atv("Apple TV"), vga("VGA adapter"), watch("Apple Watch");

    private final String description;

    /**
     * @param description
     */
    SKU(final String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }
}
