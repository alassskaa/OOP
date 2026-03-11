package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для генерации чисел для тестирования различных типов данных.
 **/
public class PrimeGenerator {

    /**
     * Класс для генерации простых чисел от меньшего к большему.
     * По порядку генерируется заданное количество простых чисел,
     * начиная с наименьшего.
     *
     * @param count - количество генерируемых простых чисел.
     * @return список из заданного количества простых чисел.
     */
    public static List<Integer> generateFirstPrimes(int count) {
        List<Integer> primes = new ArrayList<>();
        int number = 2;

        while (primes.size() < count) {
            if (IsPrime.isPrime(number)) {
                primes.add(number);
            }
            number++;
        }

        return primes;
    }

    /**
     * Класс для генерации простых чисел, начиная с максимально
     * возможного для типа Integer. По порядку генерируется
     * заданное количество простых чисел, начиная с заданного
     * максимального. Генерация по убыванию.
     *
     * @param count - количество генерируемых простых чисел.
     * @return список из заданного количества простых чисел.
     */
    public static List<Integer> generateBigPrimes(int count) {
        List<Integer> primes = new ArrayList<>();
        int n = Integer.MAX_VALUE - 100_000;
        while (primes.size() < count && n > 2) {
            if (IsPrime.isPrime(n)) {
                primes.add(n);
            }
            n--;
        }
        Collections.reverse(primes);
        return primes;
    }
}
