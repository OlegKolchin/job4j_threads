package ru.job4j.pools;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

public class RolColSumTest {
    @Test
    public void whenSuccessfulFirstCol() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 8, 12},
                {1, 10, 26},
                {1, 2, 3}
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl[0].getColSum(), is(3));
    }

    @Test
    public void whenSuccessfulSecondCol() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 8},
                {1, 10}
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl[1].getColSum(), is(18));
    }

    @Test
    public void whenRowSuccessful() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 8},
                {1, 10}
        };
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        assertThat(rsl[1].getRowSum(), is(11));
    }

}