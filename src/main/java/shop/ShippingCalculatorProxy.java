package shop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShippingCalculatorProxy implements ShippingCalculator {
    private final ShippingCalculator legacy;
    private final ShippingCalculator smart;
    private final FeatureFlags flags;
    // Caché por order.id(), una por implementación: si el flag cambia no se devuelve el precio del otro calculador.
    private final Map<String, Double> legacyCache = new ConcurrentHashMap<>();
    private final Map<String, Double> smartCache = new ConcurrentHashMap<>();

    public ShippingCalculatorProxy(ShippingCalculator legacy,
                                   ShippingCalculator smart,
                                   FeatureFlags flags) {
        this.legacy = legacy;
        this.smart = smart;
        this.flags = flags;
    }

    @Override
    public double calculate(Order order) {
        return flags.isEnabled("smart-shipping")
                ? smartCache.computeIfAbsent(order.id(), id -> smart.calculate(order))
                : legacyCache.computeIfAbsent(order.id(), id -> legacy.calculate(order));
    }
}
