package ru.nsu.sidorenko;

/**
 * Представляет операцию деления.
 */
public class Div extends Expression {
    private final Expression left;
    private final Expression right;

    /**
     * Конструктор для записи делимого и делителя.
     *
     * @param left - делимое.
     * @param right - делитель.
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String print() {
        return "(" + left.print() + "/" + right.print() + ")";
    }
    
    @Override
    public Expression derivative(String variable) {
        Expression numerator = new Sub(
            new Mul(left.derivative(variable), right),
            new Mul(left, right.derivative(variable))
        );
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }
    
    @Override
    public int eval(String assignments) {
        int rightValue = right.eval(assignments);
        if (rightValue == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return left.eval(assignments) / rightValue;
    }

    /**
     * Геттер для делимого.
     *
     * @return вернет делимое
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Геттер для делителя.
     *
     * @return вернет делитель
     */
    public Expression getRight() {
        return right;
    }
}
