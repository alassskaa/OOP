package ru.nsu.sidorenko;

/**
 * Класс проверки числа на простоту.
 */
public class IsPrime {

    /**
     * Метод для проверки числа на простоту.
     *
     * @param el - число, проверяемое на простоту.
     * @return false или true.
     */
    public static boolean isPrime(int el) {
        if (el <= 1) {
            return false;
        }

        for (int i = 2; i * i <= el; i++) {
            if (el % i == 0) {
                return false;
            }
        }
        return true;
    }
}