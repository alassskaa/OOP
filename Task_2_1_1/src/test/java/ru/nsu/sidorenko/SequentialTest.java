package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс тестов для файла Sequential.
 */
public class SequentialTest {

    @Test
    public void smallData() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);

        boolean ans = Sequential.check(list);
        assertTrue(ans);
    }

    @Test
    public void bigData() {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);

        boolean ans = Sequential.check(list);
        assertFalse(ans);
    }

    @Test
    public void dataWithTwo() {
        List<Integer> list = Arrays.asList(2, 3, 5, 7);

        boolean ans = Sequential.check(list);
        assertFalse(ans);
    }

    @Test
    public void dataWithZero() {
        List<Integer> list = Arrays.asList(2, 0, 5, 7);

        boolean ans = Sequential.check(list);
        assertTrue(ans);
    }

    @Test
    public void timeTestSmallLenSmallNumbers() throws InterruptedException {
        double sum = 0;
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();
            List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9 , 4);

            boolean result = Sequential.check(list);
            assertTrue(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and small numbers took: %.3f ms%n", durationMs);
            sum +=  durationMs;
        }
        System.out.println(sum/20);
    }

    @Test
    public void timeTestSmallLenBigNumbers() throws InterruptedException {
        double sum = 0;
        List<Integer> list = PrimeGenerator.generateBigPrimes(15);
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();

            boolean result = Sequential.check(list);
            assertFalse(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and big numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.println(sum/20);
    }


    @Test
    public void timeTestBigLenSmallNumbers() throws InterruptedException {
        double sum = 0;
        List<Integer> list = PrimeGenerator.generateFirstPrimes(10000);
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();

            boolean result = Sequential.check(list);
            assertFalse(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and big numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.println(sum/20.0);
    }

    @Test
    public void timeTestSmallDataBigNumbers() throws InterruptedException {
        double sum = 0;
        List<Integer> list = PrimeGenerator.generateBigPrimes(3000);
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();

            boolean result = Sequential.check(list);
            assertFalse(result);

            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;
            System.out.printf("Test with small data and big numbers took: %.2f ms%n", durationMs);
            sum += durationMs;
        }
        System.out.println(sum/20);
    }
}
