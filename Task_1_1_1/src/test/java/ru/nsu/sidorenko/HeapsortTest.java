package ru.nsu.sidorenko;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest
{
    @Test
    void sorted()
    {
        int[] arr = {1, 2, 3, 4, 5};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void not_sorted()
    {
        int[] arr = {5, 17, 40, 11, 4, 3, 7};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[]{3, 4, 5, 7, 11, 17, 40}, arr);
    }

    @Test
    void empty()
    {
        int[] arr = {};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void with_negatve()
    {
        int[] arr = {1, -2, 3, 7, 2};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[]{-2, 1, 2, 3, 7}, arr);
    }

    @Test
    void only_one()
    {
        int[] arr = {1};
        Heapsort.heapsort(arr);
        assertArrayEquals(new int[]{1}, arr);
    }
}