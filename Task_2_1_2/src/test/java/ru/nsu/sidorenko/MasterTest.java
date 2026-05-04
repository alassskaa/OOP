package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Класс тестов для проверки работы
 * программы.
 */
public class MasterTest {

    @BeforeAll
    static void startCluster() throws InterruptedException {
        Worker.start(5001);
        Worker.start(5002);
        Worker.start(5003);
        Worker.start(5004);
        Thread.sleep(500);
    }

    @Test
    void smallNumbers() throws Exception {
        assertTrue(new Master().distribute(List.of(6, 8, 7, 13, 5, 9, 4)));
    }

    @Test
    void bigNumbers() throws Exception {
        assertFalse(new Master().distribute(List.of(20319251, 6997901,
                6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053)));
    }

    @Test
    void emptyArray() throws Exception {
        assertFalse(new Master().distribute(List.of()));
    }

    @Test
    void singleComposite() throws Exception {
        assertTrue(new Master().distribute(List.of(4)));
    }

    @Test
    void singlePrime() throws Exception {
        assertFalse(new Master().distribute(List.of(7)));
    }

    @Test
    void singleOne() throws Exception {
        assertTrue(new Master().distribute(List.of(1)));
    }

    @Test
    void singleZero() throws Exception {
        assertTrue(new Master().distribute(List.of(0)));
    }

    @Test
    void manyMixed() throws Exception {
        List<Integer> nums = new ArrayList<>();
        for (int i = 100; i < 200; i++) {
            nums.add(i);
        }
        assertTrue(new Master().distribute(nums));
    }

    @Test
    void multipleMastersInParallel() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        try {
            List<Future<Boolean>> futures = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                futures.add(pool.submit(() -> new Master().distribute(List.of(2, 3, 5, 7, 11, 4))));
            }
            for (Future<Boolean> f : futures) {
                assertTrue(f.get(60, TimeUnit.SECONDS));
            }
        } finally {
            pool.shutdown();
        }
    }

    @Test
    void parallelMixedExpectations() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        try {
            Future<Boolean> withComposite = pool.submit(
                    () -> new Master().distribute(List.of(2, 3, 5, 9, 11)));
            Future<Boolean> allPrime = pool.submit(
                    () -> new Master().distribute(List.of(2, 3, 5, 7, 11)));

            assertTrue(withComposite.get(60, TimeUnit.SECONDS));
            assertFalse(allPrime.get(60, TimeUnit.SECONDS));
        } finally {
            pool.shutdown();
        }
    }
}