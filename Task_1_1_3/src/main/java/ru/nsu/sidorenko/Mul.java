package ru.nsu.sidorenko;

import java.util.Map;

/**
 * Представляет операцию умножения.
 */
public class Mul implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для записи умножаемых.
     *
     * @param left - левое умножаемое.
     * @param right - правое умножаемое.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }
    
    @Override
    public Expression derivative(String variable) {
        return new Add(
            new Mul(left.derivative(variable), right),
            new Mul(left, right.derivative(variable))
        );
    }
    
    @Override
    public int eval(Map<String, Integer> arguments) {
        return left.eval(arguments) * right.eval(arguments);
    }

    /**
     * Геттер для левого умножаемого.
     *
     * @return вернет умножаемое
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Геттер для правого умножаемого.
     *
     * @return вернет умножаемое
     */
    public Expression getRight() {
        return right;
    }
}

