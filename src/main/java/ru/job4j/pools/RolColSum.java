package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums s = new Sums();
            s.setColSum(sumCol(i, matrix));
            s.setRowSum(sumRow(i, matrix));
            rsl[i] = s;
        }
        return rsl;
    }

    private static int sumRow(int row, int[][] matrix) {
        int rsl = 0;
        for (int i = 0; i < matrix.length; i++) {
            rsl += matrix[row][i];
        }
        return rsl;
    }

    private static int sumCol(int col, int[][] matrix) {
        int rsl = 0;
        for (int i = 0; i < matrix.length; i++) {
            rsl += matrix[i][col];
        }
        return rsl;
    }

    private static CompletableFuture<Integer> asyncSumRow(int row, int[][] matrix) {
        return CompletableFuture.supplyAsync(
                () -> sumRow(row, matrix)
        );
    }

    private static CompletableFuture<Integer> asyncSumCol(int col, int[][] matrix) {
        return CompletableFuture.supplyAsync(
                () -> sumCol(col, matrix)
        );
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums s = new Sums();
            s.setColSum(asyncSumCol(i, matrix).get());
            s.setRowSum(asyncSumRow(i, matrix).get());
            rsl[i] = s;
        }
        return rsl;
    }
}