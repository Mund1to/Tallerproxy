package shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShippingCalculatorProxyTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);
    ShippingCalculatorProxy proxy = new ShippingCalculatorProxy(new SmartShippingCalculator());

    @Test void delegaEnSmart() {
        assertEquals(15.0, proxy.calculate(order)); // 4 kg × 2,5 + 5
    }

    @Test void checkoutFuncionaConCualquierCalculador() {
        Checkout checkout = new Checkout(o -> 7.0);
        assertEquals(107.0, checkout.total(order));
    }

    @Test void noRecalculaUnaOrdenYaCalculada() {
        CalculadorEspia real = new CalculadorEspia(15.0);
        ShippingCalculatorProxy conCache = new ShippingCalculatorProxy(real);

        assertEquals(15.0, conCache.calculate(order));
        assertEquals(15.0, conCache.calculate(order));
        assertEquals(1, real.llamadas);
    }

    @Test void cadaOrdenSeCalculaUnaSolaVez() {
        CalculadorEspia real = new CalculadorEspia(15.0);
        ShippingCalculatorProxy conCache = new ShippingCalculatorProxy(real);
        Order otra = new Order("o2", "c1", "Medellín", 100, 4);

        conCache.calculate(order);
        conCache.calculate(otra);
        conCache.calculate(order);
        assertEquals(2, real.llamadas);
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
