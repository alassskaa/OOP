package ru.nsu.sidorenko;

import java.util.Map;

/**
 * Представляет переменную в выражении.
 */
public class Variable implements Expression {
    private final String name;

    /**
     * Конструктор, записывающий имя переменной в поле класса.
     *
     * @param name - имя записываемой переменной
     */
    public Variable(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public Expression derivative(String variable) {
        //Производная по этой же переменной - 1, по другой - 0.
        if (this.name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }
    
    @Override
    public int eval(Map<String, Integer> arguments) {
        if (arguments == null || !arguments.containsKey(name)) {
            throw new IllegalArgumentException("Variable is not assigned");
        }
        return arguments.get(name);
    }

    /**
     * Геттер для получения имени переменной.
     *
     * @return вернет имя
     */
    public String getName() {
        return name;
    }
}
