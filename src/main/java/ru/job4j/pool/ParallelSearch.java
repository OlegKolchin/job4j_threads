package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {
    private final int[] array;
    private final int value;

    public ParallelSearch(int[] array, int value) {
        this.array = array;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            return search();
        }
        int mid = array.length / 2;
        ParallelSearch leftSearch = new ParallelSearch(Arrays.copyOfRange(array, 0, mid), value);
        ParallelSearch rightSearch = new ParallelSearch(Arrays.copyOfRange(array, mid, array.length), value);
        leftSearch.fork();
        rightSearch.fork();
        int left =  leftSearch.join();
        int right = rightSearch.join();
        if (right != -1) {
            right += array.length / 2;
        }
        return left != -1 ? left : right;
    }

    private int search() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        System.out.println(pool.invoke(new ParallelSearch(array, 13)));
        System.out.println(pool.invoke(new ParallelSearch(array, 15)));
    }
}
