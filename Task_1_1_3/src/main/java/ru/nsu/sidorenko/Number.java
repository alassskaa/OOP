package ru.nsu.sidorenko;

import java.util.Map;

/**
 * Представляет числовую константу.
 */
public class Number implements Expression {
    private final int value;

    /**
     * Конструктор, который сохраняет число и записывает его в поле класса.
     *
     * @param value - число, которое запишется
     */
    public Number(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }
    
    @Override
    public int eval(Map<String, Integer> arguments) {
        return value;
    }

    /**
     * Геттер, возвращающий само число.
     *
     * @return вернет число
     */
    public int getValue() {
        return value;
    }
}
