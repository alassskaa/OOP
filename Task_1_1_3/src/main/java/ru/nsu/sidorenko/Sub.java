package ru.nsu.sidorenko;

import java.util.Map;

/**
 * Класс для операции вычитания.
 */
public class Sub implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для записи уменьшаемого и вычитаемого.
     *
     * @param left - уменьшаемое.
     * @param right - вычитаемое.
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }
    
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }
    
    @Override
    public int eval(Map<String, Integer> arguments) {
        return left.eval(arguments) - right.eval(arguments);
    }

    /**
     * Геттер для уменьшаемого.
     *
     * @return вернет уменьшаемое
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Геттер для вычитаемого.
     *
     * @return вернет вычитаемое
     */
    public Expression getRight() {
        return right;
    }
}
