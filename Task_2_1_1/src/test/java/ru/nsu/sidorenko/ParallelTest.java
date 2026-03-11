package ru.nsu.sidorenko;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Класс тестов для файла Parallel.
 */
public class ParallelTest {

    @Test
    public void smallData() throws InterruptedException {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);

        boolean result = Parallel.check(list, 3);

        assertTrue(result);
    }

    @Test
    public void bigData() throws InterruptedException {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);

        boolean result = Parallel.check(list, 4);

        assertFalse(result);
    }

    @Test
    public void dataWithTwo() throws InterruptedException {
        List<Integer> list = Arrays.asList(2, 3, 5, 7);

        boolean result = Parallel.check(list, 2);

        assertFalse(result);

    }

    @Test
    public void dataWithZero() throws InterruptedException {
        List<Integer> list = Arrays.asList(2, 0, 5, 7);

        boolean result = Parallel.check(list, 2);

        assertTrue(result);

    }

    @Test
    public void timeTestSmallLenSmallNumbers() throws InterruptedException {
        double sum = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();
            List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);

            boolean result = Parallel.check(list, 100);
            assertTrue(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small length and small numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.printf("Average time for small length and small numbers: %.2f ms%n", sum / 20.0);
    }

    @Test
    public void timeTestSmallLenBigNumbers() throws InterruptedException {
        double sum = 0;
        List<Integer> list = PrimeGenerator.generateBigPrimes(15);
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();

            boolean result = Parallel.check(list, 100);
            assertFalse(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and big numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.println(sum / 20);
    }

    @Test
    public void timeTestBigLenSmallNumbers() throws InterruptedException {
        double sum = 0;
        List<Integer> list = PrimeGenerator.generateFirstPrimes(10000);
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();

            boolean result = Parallel.check(list, 10);
            assertFalse(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and big numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.println(sum / 20.0);
    }

    @Test
    public void timeTestSmallDataBigNumbers() throws InterruptedException {
        double sum = 0;
        List<Integer> list = PrimeGenerator.generateBigPrimes(3000);
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();

            boolean result = Parallel.check(list, 100);
            assertFalse(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and big numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.println(sum / 20);
    }
}