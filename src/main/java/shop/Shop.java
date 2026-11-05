package shop;

/** Arma la tienda de producción: aquí se decide con qué piezas trabaja el Checkout. */
public final class Shop {
    private Shop() {}

    public static Checkout checkout() {
        return new Checkout(new LegacyShippingCalculator());
    }
}
