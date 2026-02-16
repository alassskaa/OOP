package ru.nsu.sidorenko;

/**
 * Общий класс для определения простых или не простых чисел.
 * Проверка производится при помощи метода isPrime.
 */
public class  IsPrime {

    /**
     * Реализация проверки числа на простоту. Проверяем до корня из этого числа.
     *
     * @param el - число, проверяемое на простоту.
     */
    public static boolean isPrime(int el) {
        if (el < 2) {
            return false;
        } else {
            for (int i = 2; i * i <= el; i++) {
                if (el % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}