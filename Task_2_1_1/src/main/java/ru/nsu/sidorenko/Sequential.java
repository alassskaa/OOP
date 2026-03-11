package ru.nsu.sidorenko;

import java.util.List;

/**
 * Класс, реализующий последовательную проверку списка чисел.
 * Проверяется, есть ли в списке составные числа.
 * Проверка происходит последовательно в одном потоке.
 */
public class Sequential {
    /**
     * Основная функция для проверки списка на наличие в нем составных чисел.
     * Каждый элемент проверяется последовательно.
     *
     * @param list - обрабатываемый список.
     * @return результат.
     */
    public static boolean check(List<Integer> list) {
        boolean flag = false;
        for (Integer el : list) {
            if (!IsPrime.isPrime(el)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
