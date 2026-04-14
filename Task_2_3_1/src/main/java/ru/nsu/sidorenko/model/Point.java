package ru.nsu.sidorenko.model;

import java.util.Objects;

/**
 * Класс для задания координат внутри поля.
 */
public class Point {
    public int xCoord;
    public int yCoord;

    /**
     * Конструктор класса.
     *
     * @param x - координата по оси X.
     * @param y - координата по оси Y.
     */
    public Point(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point p = (Point) o;
        return xCoord == p.xCoord && yCoord == p.yCoord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord);
    }
}