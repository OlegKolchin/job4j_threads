package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int i = get();
        count.set(i++);
        if (i == 0) {
            throw new UnsupportedOperationException("Count is not impl.");
        }

    }

    public int get() {
        return count.get();
    }
}