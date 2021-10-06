package ru.job4j.thread;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        try {
            while (queue.size() == limit) {
                this.wait();
            }
            notifyAll();
            queue.offer(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        this.notifyAll();
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
