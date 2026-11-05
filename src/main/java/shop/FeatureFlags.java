package shop;

public class FeatureFlags {
    private final java.util.Set<String> enabled = new java.util.HashSet<>();
    public void enable(String name)  { enabled.add(name); }
    public void disable(String name) { enabled.remove(name); }
    public boolean isEnabled(String name) { return enabled.contains(name); }
}
