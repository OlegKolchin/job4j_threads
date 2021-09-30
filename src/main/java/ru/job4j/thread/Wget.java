package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String target;

    public Wget(String url, int speed, String target) {
        this.url = url;
        this.speed = speed;
        this.target = target;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(target)) {
                    byte[] dataBuffer = new  byte[1024];
                    int bytesRead;
                    long time = System.currentTimeMillis();
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                        long rsl = System.currentTimeMillis() - time;
                        if (rsl < speed) {
                            Thread.sleep(speed - rsl);
                        }
                        time = System.currentTimeMillis();
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, "temp.xml"));
        wget.start();
        wget.join();
    }
}