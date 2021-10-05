package ru.job4j.thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) throws InterruptedException {
        int limit = 2;
        while (queue.size() == limit) {
            this.wait();
        }
        if (queue.size() < limit) {
            notifyAll();
            queue.offer(value);
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        this.notifyAll();
        return queue.poll();
    }
}
