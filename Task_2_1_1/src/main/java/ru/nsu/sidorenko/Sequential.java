package ru.nsu.sidorenko;

import java.util.List;

/**
 * Класс, реализующий последовательную проверку списка чисел.
 * Проверяется, есть ли в списке составные числа.
 * Проверка происходит последовательно в одном потоке.
 */
public class Sequential {
    private boolean flag = false;

    /**
     * Основная функция для проверки списка на наличие в нем составных чисел.
     * Каждый элемент проверяется последовательно.
     *
     * @param list - обрабатываемый список.
     * @return результат.
     */
    public boolean check(List<Integer> list) {

        for (Integer el : list) {
            if (!IsPrime.isPrime(el)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
