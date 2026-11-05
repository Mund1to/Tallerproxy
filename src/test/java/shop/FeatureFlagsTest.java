package shop;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FeatureFlagsTest {
    FeatureFlags flags = new FeatureFlags();

    @Test void unFlagEmpiezaApagado() {
        assertFalse(flags.isEnabled("algun-flag"));
    }

    @Test void seEnciendeYSeApaga() {
        flags.enable("algun-flag");
        assertTrue(flags.isEnabled("algun-flag"));
        flags.disable("algun-flag");
        assertFalse(flags.isEnabled("algun-flag"));
    }
}
