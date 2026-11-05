package shop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShippingCalculatorProxy implements ShippingCalculator {
    private final ShippingCalculator real;
    private final Map<String, Double> cache = new ConcurrentHashMap<>();

    public ShippingCalculatorProxy(ShippingCalculator real) {
        this.real = real;
    }

    @Override
    public double calculate(Order order) {
        return cache.computeIfAbsent(order.id(), id -> real.calculate(order));
    }
}
