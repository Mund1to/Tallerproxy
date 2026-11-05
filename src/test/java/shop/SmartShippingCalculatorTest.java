package shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SmartShippingCalculatorTest {
    SmartShippingCalculator calc = new SmartShippingCalculator();

    @Test void cobraDosYMedioPorKilo() {
        assertEquals(5.0, calc.calculate(new Order("o1", "c1", "Bogotá", 100, 2))); // 2 kg × 2,5
    }
}
