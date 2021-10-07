package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount(AtomicReference<Integer> count) {
        this.count = count;
    }

    public void increment() {
        int i;
        do {
            i = count.get();
        } while (!count.compareAndSet(i, ++i));
    }

    public int get() {
        return count.get();
    }
}