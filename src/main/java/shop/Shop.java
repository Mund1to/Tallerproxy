package shop;

/** Arma la tienda de producción: aquí se decide con qué piezas trabaja el Checkout. */
public final class Shop {
    private Shop() {}

    public static Checkout checkout() {
        FeatureFlags flags = new FeatureFlags(); // smart-shipping apagado
        return new Checkout(new ShippingCalculatorProxy(
                new LegacyShippingCalculator(), new SmartShippingCalculator(), flags));
    }
}
