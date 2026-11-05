package shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Lo que ve el cliente en producción. Solo debe cambiar cuando se encienda el flag. */
class ShopTest {
    Order order = new Order("o1", "c1", "Medellín", 100, 4);

    @Test void produccionCobraEnvioSmart() {
        assertEquals(115.0, Shop.checkout().total(order)); // 100 + 4 kg × 2,5 + 5 fuera de Bogotá
    }
}
