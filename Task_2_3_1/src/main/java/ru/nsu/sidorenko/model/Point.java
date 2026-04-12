package ru.nsu.sidorenko.model;

import java.util.Objects;

/**
 * Класс для задания координат внутри поля.
 */
public class Point {
    public int x;
    public int y;

    /**
     * Конструктор класса.
     *
     * @param x - координата по оси X.
     * @param y - координата по оси Y.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}