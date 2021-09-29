package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName()));
        first.start();
        second.start();
        Thread.State state = Thread.State.TERMINATED;
        while (first.getState() != state || second.getState() != state) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println("Работа завершена");
    }
}