package ru.nsu.sidorenko;

import java.lang.Thread;
import java.util.List;

/**
 * Класс, реализующий параллельную проверку списка чисел.
 * Проверяется, есть ли в списке составные числа.
 * Реализовано явное указание количества используемых потоков.
 * В зависимости от выбранного количества нитей, список,
 * подаваемый на вход, разбивается на отдельные участки,
 * которые обрабатываются разными нитями.
 */
public class Parallel extends Thread {
    private final int from;
    private final int to;
    List<Integer> list;
    Result foundComposite;

    /**
     * Конструктор для создания элемента класса.
     *
     * @param from - с какого элемента нить обрабатывает список.
     * @param to - до какого элемента нить обрабатывает список.
     * @param list - список.
     * @param flag - флаг-результат, который нити обрабатывают совместно.
     */
    private Parallel(int from, int to, List<Integer> list, Result flag) {
        this.from = from;
        this.to = to;
        this.list = list;
        this.foundComposite = flag;
    }

    /**
     * Основная функция для проверки списка на наличие в нем составных чисел.
     *
     * @param list - обрабатываемый список.
     * @param threadCount - количество используемых потоков.
     * @return флаг-результат.
     * @throws InterruptedException - исключение, которое возвращает используемая
     * функция thread.start().
     */
    public static boolean check(List<Integer> list, int threadCount) throws InterruptedException {
        Result flag = new Result();
        Thread[] threads = new Thread[threadCount];

        int chunkSize = (list.size() + threadCount - 1) / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int from = i * chunkSize;
            int to = Math.min(from + chunkSize, list.size());
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
            if (!IsPrime.isPrime(list.get(i))) {
                foundComposite.flag = true;
                break;
            }
        }
    }

    /**
     * Приватный класс, необходимый для того, чтобы потоки обрабатывали результат совместно.
     */
    private static class Result {
       volatile boolean flag = false;
    }

}
