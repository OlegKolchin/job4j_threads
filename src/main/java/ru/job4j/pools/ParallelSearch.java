package ru.job4j.pools;

import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {
    private final int[] array;
    private final int value;
    private final int start;
    private final int end;

    public ParallelSearch(int[] array, int value, int start, int end) {
        this.array = array;
        this.value = value;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int size = end - start;
        if (size <= 10) {
            return search();
        }
        int mid = (end + start) / 2;
        ParallelSearch leftSearch = new ParallelSearch(array, value, start, mid);
        ParallelSearch rightSearch = new ParallelSearch(array, value, mid + 1, end);
        leftSearch.fork();
        rightSearch.fork();
        int left =  leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    private int search() {
        if (start < 0 || end < 0 || start > end) {
            throw new IllegalArgumentException();
        }
        for (int i = start; i <= end; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
