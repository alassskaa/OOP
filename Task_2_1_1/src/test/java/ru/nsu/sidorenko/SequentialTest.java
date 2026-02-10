package ru.nsu.sidorenko;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SequentialTest {

    @Test
    public void smallData() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);

        Sequential seq = new Sequential();
        boolean ans = false;

        ans = seq.check(list);
        assertTrue(ans);
    }

    @Test
    public void bigData() {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);

        Sequential seq = new Sequential();
        boolean ans = false;

        ans = seq.check(list);
        assertFalse(ans);
    }

    @Test
    public void dataWithTwo() {
        List<Integer> list = Arrays.asList(2, 3, 5, 7);

        Sequential seq = new Sequential();
        boolean ans = false;

        ans = seq.check(list);
        assertFalse(ans);
    }

    @Test
    public void dataWithZero() {
        List<Integer> list = Arrays.asList(2, 0, 5, 7);

        Sequential seq = new Sequential();
        boolean ans = false;

        ans = seq.check(list);
        assertTrue(ans);
    }

    @Test
    public void timeTest()  {
        long start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            list.add(i);
        }

        Sequential seq = new Sequential();
        boolean ans = false;

        ans = seq.check(list);
        assertTrue(ans);
        long end = System.currentTimeMillis();
        System.out.println("Test took: " + (end - start) + " ms\n");
    }
}
