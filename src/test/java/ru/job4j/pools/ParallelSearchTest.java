package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {
    @Test
    public void whenSuccessful() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int rsl = pool.invoke(new ParallelSearch(array, 11, 0, 13));
        assertThat(rsl, is(11));
    }

    @Test
    public void whenNotSuccessful() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int rsl = pool.invoke(new ParallelSearch(array, 11, 0, 9));
        assertThat(rsl, is(-1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void whenException() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int rsl = pool.invoke(new ParallelSearch(array, 11, -1, 9));
    }

    @Test
    public void whenDoNotFound() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] array = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int rsl = pool.invoke(new ParallelSearch(array, 15, 0, 13));
        assertThat(rsl, is(-1));
    }
}