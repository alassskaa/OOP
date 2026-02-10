package ru.nsu.sidorenko;

import java.util.List;

/**
 * Класс, реализующий параллельную проверку списка чисел.
 * Проверяется, есть ли в списке составные числа.
 * Для реализации параллельного подхода использован метод parallelStream().
 */
public class  ParallelStream {

    /**
     * Основная функция для проверки списка на наличие в нем составных чисел.
     * Используется метод parallelStream(). При помощи метода anyMatch() производится
     * проверка на наличие в списке составных чисел.
     *
     * @param list - обрабатываемый список.
     * @return результат.
     */
    public static boolean check(List<Integer> list) {
        return list.parallelStream().anyMatch(ParallelStream::isPrime);
    }

    /**
     * Реализация проверки числа на простоту. Проверяем до корня из этого числа.
     *
     * @param el - число, проверяемое на простоту.
     */
    private static boolean isPrime(int el) {
        if (el < 2) {
            return true;
        }

        for (int i = 2; i * i <= el; i++) {
            if (el % i == 0) {
                return true;
            }
        }

        return false;
    }
}
