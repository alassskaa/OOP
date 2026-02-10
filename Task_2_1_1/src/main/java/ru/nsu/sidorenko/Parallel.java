package ru.nsu.sidorenko;

import java.lang.Thread;
import java.util.List;

public class Parallel extends Thread {
    private final int from;
    private final int to;
    List<Integer> list;
    Result flag;

    private Parallel(int from, int to, List<Integer> list, Result flag) {
        this.from = from;
        this.to = to;
        this.list = list;
        this.flag = flag;
    }

    public static boolean check(List<Integer> list, int threadCount) throws InterruptedException {
        Result flag = new Result();
        Thread[] threads = new Thread[threadCount];

        int piece = (list.size() + threadCount - 1) / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int from = i * piece;
            int to = Math.min(from + piece, list.size());
            threads[i] = new Parallel(from, to, list, flag);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        return flag.flag;
    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            isPrime(list.get(i));
            if (flag.flag) {
                break;
            }
        }
    }

    private void isPrime(int el) {
        if (el < 2) {
            flag.flag = true;
        } else {
            for (int i = 2; i * i <= el; i++) {
                if (el % i == 0) {
                    flag.flag = true;
                    break;
                }
            }
        }
    }

    private static class Result {
       volatile boolean flag = false;
    }

}
