package shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Lo que ve el cliente en producción. Solo debe cambiar cuando se encienda el flag. */
class ShopTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test void produccionCobraEnvioLegacy() {
        assertEquals(110.0, Shop.checkout().total(order)); // 100 + tarifa fija de 10
    }
}
