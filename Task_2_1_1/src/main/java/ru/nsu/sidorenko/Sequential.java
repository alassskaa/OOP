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
            isPrime(el);
        }

        return flag;
    }

    /**
     * Реализация проверки числа на простоту. Проверяем до корня из этого числа.
     *
     * @param el - число, проверяемое на простоту.
     */
    private void isPrime(Integer el) {
        if (el < 2) {
            flag = true;
        } else {
            for (int i = 2; i * i <= el; i++) {
                if (el % i == 0) {
                    flag = true;
                    break;
                }
            }
        }
    }

}
