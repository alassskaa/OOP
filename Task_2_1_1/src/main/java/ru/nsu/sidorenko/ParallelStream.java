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
        return list.parallelStream().anyMatch(n -> !IsPrime.isPrime(n));
    }
}
