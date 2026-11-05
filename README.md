# Taller Proxy en el Trunk

Cambiamos cómo se calcula el envío de una tienda usando **Trunk-Based Development** y el patrón **Proxy**: una sola rama (`main`), un commit pequeño por paso y `main` siempre en verde. El código nuevo entra apagado, detrás de un Proxy y un feature flag.

## Cómo correr los tests

Solo hace falta JDK 17 o superior. El Maven Wrapper descarga Maven la primera vez.

```
./mvnw test          # Linux, macOS o Git Bash
.\mvnw.cmd test      # Windows (PowerShell o cmd)
```

GitHub Actions corre los mismos tests en cada push a `main`.

## Estructura

- `src/main/java/shop`: código de la tienda. `Shop` arma el `Checkout` de producción.
- `src/test/java/shop`: tests con JUnit 5. `ShopTest` fija lo que ve el cliente en producción.

## Extensión del Proxy (paso 5): caché

`ShippingCalculatorProxy` guarda el envío por `order.id()` y no vuelve a llamar al calculador real para una orden ya calculada. Mientras convivieron legacy y smart hubo una caché por implementación, para que al cambiar el flag nunca se devolviera un precio calculado por el otro calculador.

## Limpieza (paso 6)

Primero se encendió `smart-shipping` en `Shop` y `ShopTest` pasó de 110 a 115. Con todo en verde se borraron `LegacyShippingCalculator`, el flag y `FeatureFlags`, que ya no tenía usos. El Proxy quedó como caché delante de `SmartShippingCalculator` y `ShopTest` siguió en 115: la limpieza no cambió el comportamiento.

Los tests de los dos caminos del flag (`usaLegacyConFlagApagado` y `usaSmartConFlagEncendido`) se borraron junto con el flag. Están en verde en el historial, desde `feat: add ShippingCalculatorProxy (flag off)` hasta `chore: enable smart-shipping`.
