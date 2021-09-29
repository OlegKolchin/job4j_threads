package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.run();
        progress.interrupt();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r Loading... \\");
                Thread.sleep(500);
                System.out.print("\r Loading... |");
                Thread.sleep(500);
                System.out.print("\r Loading... /");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
