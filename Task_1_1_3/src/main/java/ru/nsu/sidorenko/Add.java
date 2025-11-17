package ru.nsu.sidorenko;

import java.util.Map;

/**
 * Класс для операции сложения.
 */
public class Add implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для записи слагаемых.
     *
     * @param left - левое слагаемое.
     * @param right - правое слагаемое.
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }
    
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }
    
    @Override
    public int eval(Map<String, Integer> arguments) {
        return left.eval(arguments) + right.eval(arguments);
    }

    /**
     * Геттер для левого слагаемого.
     *
     * @return вернет слагаемое
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Геттер для правого слагаемого.
     *
     * @return вернет слагаемое
     */
    public Expression getRight() {
        return right;
    }
}
