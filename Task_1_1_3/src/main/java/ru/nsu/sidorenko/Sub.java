package ru.nsu.sidorenko;

/**
 * Класс для операции вычитания.
 */
public class Sub extends Expression {
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
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }
    
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }
    
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) - right.eval(assignments);
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
