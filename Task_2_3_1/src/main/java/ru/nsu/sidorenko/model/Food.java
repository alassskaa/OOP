package ru.nsu.sidorenko.model;

/**
 * Класс для описания элементов еды.
 * У каждого элемента еды есть координаты расположения
 * на поле.
 */
public class Food {
    private final Point position;

    /**
     * Конструктор класса.
     *
     * @param x - координата элемента по оси X.
     * @param y - координата элемента по оси Y.
     */
    public Food(int x, int y) {
        this.position = new Point(x, y);
    }

    /**
     * Геттер класса, с помощью которого
     * можно получить координаты элемента.
     *
     * @return координаты элемента еды.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Сеттер класса. Устанавливает координаты элемента.
     *
     * @param x - координата элемента по оси X.
     * @param y - координата элемента по оси Y.
     */
    public void setPosition(int x, int y) {
        this.position.xCoord = x;
        this.position.yCoord = y;
    }
}