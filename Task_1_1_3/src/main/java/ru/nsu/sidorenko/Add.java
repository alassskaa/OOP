package ru.nsu.sidorenko;

/**
 * Класс для операции сложения.
 */
public class Add extends Expression {
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
    public String print() {
        return "(" + left.print() + "+" + right.print() + ")";
    }
    
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }
    
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) + right.eval(assignments);
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
