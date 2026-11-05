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

    @Test void noRecalculaUnaOrdenYaCalculada() {
        CalculadorEspia legacy = new CalculadorEspia(10.0);
        ShippingCalculatorProxy conCache = new ShippingCalculatorProxy(legacy, new CalculadorEspia(15.0), flags);

        assertEquals(10.0, conCache.calculate(order));
        assertEquals(10.0, conCache.calculate(order));
        assertEquals(1, legacy.llamadas);
    }

    @Test void cadaOrdenSeCalculaUnaSolaVez() {
        CalculadorEspia legacy = new CalculadorEspia(10.0);
        ShippingCalculatorProxy conCache = new ShippingCalculatorProxy(legacy, new CalculadorEspia(15.0), flags);
        Order otra = new Order("o2", "c1", "Medellín", 100, 4);

        conCache.calculate(order);
        conCache.calculate(otra);
        conCache.calculate(order);
        assertEquals(2, legacy.llamadas);
    }

    @Test void laCacheNoMezclaCalculadoresAlCambiarElFlag() {
        CalculadorEspia legacy = new CalculadorEspia(10.0);
        CalculadorEspia smart = new CalculadorEspia(15.0);
        ShippingCalculatorProxy conCache = new ShippingCalculatorProxy(legacy, smart, flags);

        assertEquals(10.0, conCache.calculate(order));
        flags.enable("smart-shipping");
        assertEquals(15.0, conCache.calculate(order));
        flags.disable("smart-shipping");
        assertEquals(10.0, conCache.calculate(order));
        assertEquals(1, legacy.llamadas);
        assertEquals(1, smart.llamadas);
    }

    /** Calculador falso que cuenta cuántas veces lo llaman. */
    static class CalculadorEspia implements ShippingCalculator {
        final double valor;
        int llamadas;

        CalculadorEspia(double valor) { this.valor = valor; }

        @Override public double calculate(Order o) {
            llamadas++;
            return valor;
        }
    }
}
