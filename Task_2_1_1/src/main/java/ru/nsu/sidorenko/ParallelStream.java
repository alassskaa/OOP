package ru.nsu.sidorenko;

import java.util.List;

public class  ParallelStream {

    public static boolean check(List<Integer> list) {
        return list.parallelStream().anyMatch(ParallelStream::isPrime);
    }
    
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
