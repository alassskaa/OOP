package ru.nsu.sidorenko.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для описания препятствий.
 * Клетки, занятые препятствием, хранятся
 * в формате списка.
 */
public class Obstacle {
    private final List<Point> positions;

    /**
     * Конструктор класса.
     *
     * @param positions - клетки, занятые препятствием.
     */
    public Obstacle(List<Point> positions) {
        this.positions = new ArrayList<>(positions);
    }

    /**
     * Метод для получения списка позиций.
     *
     * @return список позиций клеток препятствия.
     */
    public List<Point> getPositions() {
        return positions;
    }

}