package ru.nsu.sidorenko;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParallelStreamTest {

    @Test
    public void smallData() throws InterruptedException {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);

        boolean result = ParallelStream.check(list);

        assertTrue(result);
    }

    @Test
    public void bigData() throws InterruptedException {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);

        boolean result = ParallelStream.check(list);

        assertFalse(result);
    }

    @Test
    public void dataWithTwo() throws InterruptedException {
        List<Integer> list = Arrays.asList(2, 3, 5, 7);

        boolean result = ParallelStream.check(list);

        assertFalse(result);
    }

    @Test
    public void dataWithZero() throws InterruptedException {
        List<Integer> list = Arrays.asList(2, 0, 5, 7);

        boolean result = ParallelStream.check(list);

        assertTrue(result);
    }

    @Test
    public void timeTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            list.add(i);
        }

        boolean result = ParallelStream.check(list);
        assertTrue(result);

        long end = System.currentTimeMillis();
        System.out.println("Test took: " + (end - start) + " ms\n");
    }
}