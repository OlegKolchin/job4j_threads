package ru.job4j.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void defaultTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        AtomicInteger i = new AtomicInteger(2);
        Thread producer = new Thread(() -> {
            try {
                while (i.intValue() >= 0) {
                    queue.offer(i.getAndDecrement());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        List<Integer> rsl = new ArrayList<>();
        Thread consumer = new Thread(() -> {
            try {
                for (int x = 1; x >= 0; x--) {
                    Integer s = queue.poll();
                    if (s != null) {
                        rsl.add(s);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rsl.size(), is(2));
    }


    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        AtomicInteger i = new AtomicInteger(0);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            try {
                while (i.intValue() < 5) {
                    queue.offer(i.getAndIncrement());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}

