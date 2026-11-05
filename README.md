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
