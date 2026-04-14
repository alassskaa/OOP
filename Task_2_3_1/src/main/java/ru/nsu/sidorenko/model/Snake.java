package ru.nsu.sidorenko.model;

import java.util.LinkedList;

/**
 * Класс для описания змейки.
 * Клетки, занятые змейкой, хранятся в формате списка.
 * В начале игры змейка движется вправо.
 */
public class Snake {
    private final LinkedList<Point> snake;
    private Direction direction;

    /**
     * Создание новой змейки состоящей из одногй точки с координатами {@code startX, startY}.
     *
     * @param startX - позиция головы в начале игры по X.
     * @param startY - позиция головы в начале игры по Y.
     *
     * @see Snake
     */
    public Snake(int startX, int startY) {
        snake = new LinkedList<>();
        snake.add(new Point(startX, startY));
        direction = Direction.RIGHT;
    }

    /**
     * Метод для установки направления движения змейки.
     * Нажатие кнопки направления движения в сторону,
     * в которую змейка движется в данный момент, или
     * в сторону, противоположную нынешнему движению,
     * игнорируется.
     *
     * @param newDirection - новое направление движения.
     */
    public void setDirection(Direction newDirection) {
        if ((direction == Direction.UP && newDirection == Direction.DOWN)
                || (direction == Direction.DOWN && newDirection == Direction.UP)
                || (direction == Direction.LEFT && newDirection == Direction.RIGHT)
                || (direction == Direction.RIGHT && newDirection == Direction.LEFT)) {
            return;
        }
        this.direction = newDirection;
    }

    /**
     * Метод для определения длины змейки.
     *
     * @return длину.
     */
    public int length() {
        return snake.size();
    }

    /**
     * Метод для реализации одного хода змейки.
     * Голова перемещается посредством
     * добавления одного звена к ее голове и
     * удаления одного звена из хвоста.
     */
    public void move() {
        Point head = getHead();
        Point newHead = nextPoint(head);

        snake.addFirst(newHead);
        snake.removeLast();
    }

    /**
     * Метод для реализации роста змейки.
     * Рост происходит после съедания элемента еды.
     */
    public void grow() {
        Point head = getHead();
        Point newHead = nextPoint(head);

        snake.addFirst(newHead);
    }

    /**
     * Геттер класса.
     *
     * @return snake.
     */
    public Iterable<Point> getSnake() {
        return snake;
    }

    /**
     * Геттер для головы змейки.
     *
     * @return первый элемент списка змейки.
     */
    public Point getHead() {
        return snake.getFirst();
    }

    /**
     * Геттер для направления змейки.
     *
     * @return направление.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Метод для определения клетки, на которой
     * змейка должна оказаться при следующем шаге.
     *
     * @param head - голова змейки.
     * @return координаты клетки следующего шага.
     */
    private Point nextPoint(Point head) {
        int x = head.xCoord;
        int y = head.yCoord;

        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        return new Point(x, y);
    }
}