package shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShippingCalculatorProxyTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);
    FeatureFlags flags = new FeatureFlags();
    ShippingCalculatorProxy proxy = new ShippingCalculatorProxy(
            new LegacyShippingCalculator(), new SmartShippingCalculator(), flags);

    @Test void usaLegacyConFlagApagado() {
        assertEquals(10.0, proxy.calculate(order));
    }

    @Test void usaSmartConFlagEncendido() {
        flags.enable("smart-shipping");
        assertEquals(15.0, proxy.calculate(order)); // 4 kg × 2,5 + 5
    }

    @Test void checkoutFuncionaConCualquierCalculador() {
        Checkout checkout = new Checkout(o -> 7.0);
        assertEquals(107.0, checkout.total(order));
    }

    @Test void checkoutNoSeEnteraDelCambio() {
        Checkout checkout = new Checkout(proxy);
        assertEquals(110.0, checkout.total(order));
        flags.enable("smart-shipping");
        assertEquals(115.0, checkout.total(order)); // mismo Checkout, ahora responde smart
    }
}
