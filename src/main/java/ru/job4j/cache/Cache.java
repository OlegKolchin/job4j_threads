package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), function(model)) != null;
    }

    private BiFunction<Integer, Base, Base> function(Base model) {
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        BiFunction<Integer, Base, Base> rsl = (a, b) -> b = new Base(model.getId(), model.getVersion() + 1);
        return rsl;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}