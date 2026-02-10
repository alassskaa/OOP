package ru.nsu.sidorenko;

import java.util.List;

public class Sequential {
    private boolean flag = false;

    public boolean check(List<Integer> list) {

        for (Integer el : list) {
            isPrime(el);
        }

        return flag;
    }

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
