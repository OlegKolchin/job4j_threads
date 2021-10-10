package ru.job4j.pool;

import ru.job4j.thread.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(12);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
               while (!Thread.currentThread().isInterrupted()) {
                   try {
                       tasks.poll().run();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                       Thread.currentThread().interrupt();
                   }
               }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread :: interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        for (int i = 0; i < 20; i++) {
            int x = i;
            pool.work(() -> System.out.println(x + " " + Thread.currentThread().getName()));
        }
        Thread.sleep(1000);
        pool.shutdown();
    }
}