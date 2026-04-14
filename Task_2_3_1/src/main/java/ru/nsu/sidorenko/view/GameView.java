package ru.nsu.sidorenko.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.sidorenko.controller.GameController;
import ru.nsu.sidorenko.model.Point;

/**
 * Класс для отрисовки игрового поля.
 * Расширяет класс Canvas.
 */
public class GameView extends Canvas {
    private final GameController controller;
    private final int cellSize = 20;

    /**
     * Конструктор класса.
     *
     * @param controller - объект класса Game.
     */
    public GameView(GameController controller) {
        this.controller = controller;

        setWidth(controller.getWidth() * cellSize);
        setHeight(controller.getHeight() * cellSize);
    }

    /**
     * Метод для рисования объектов на поле.
     * Каждый объект имеет свой цвет.
     * При победе или проигрыше на поле
     * выводится текст с информацией.
     */
    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setFill(Color.RED);
        for (Point p : controller.getFoodCoordinates()) {
            gc.fillOval(
                    p.xCoord * cellSize,
                    p.yCoord * cellSize,
                    cellSize,
                    cellSize
            );
        }

        gc.setFill(Color.AQUA);
        for (Point p : controller.getObstacleCoordinates()) {
            gc.fillRect(
                    p.xCoord * cellSize,
                    p.yCoord * cellSize,
                    cellSize,
                    cellSize
            );
        }

        gc.setFill(Color.CORAL);
        for (Point p : controller.getSnakeCoordinates()) {
            gc.fillRect(
                    p.xCoord * cellSize,
                    p.yCoord * cellSize,
                    cellSize,
                    cellSize
            );
        }

        gc.setFill(Color.ORANGE);
        Point head = controller.getHead();
        gc.fillRect(
                head.xCoord * cellSize,
                head.yCoord * cellSize,
                cellSize,
                cellSize
        );

        if (controller.isGameOver()) {
            drawText(gc, "GAME OVER");
        }

        if (controller.isGameWon()) {
            drawText(gc, "YOU WIN");
        }
    }

    /**
     * Отрисовка текста. Текст розового цвета.
     * Текст выводится в центре поля.
     *
     * @param gc - объект JavaFX для рисования.
     * @param text - текстовая строка.
     */
    private void drawText(GraphicsContext gc, String text) {
        gc.setFill(Color.DEEPPINK);
        gc.fillText(text, getWidth() / 2 - 40, getHeight() / 2);
    }
}