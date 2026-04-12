package ru.nsu.sidorenko.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import ru.nsu.sidorenko.model.Food;
import ru.nsu.sidorenko.model.Game;
import ru.nsu.sidorenko.model.Obstacle;
import ru.nsu.sidorenko.model.Point;

/**
 * Класс для отрисовки игрового поля.
 * Расширяет класс Canvas.
 */
public class GameView extends Canvas {
    private final Game game;
    private final int cellSize = 20;

    /**
     * Конструктор класса.
     *
     * @param game - объект класса Game.
     */
    public GameView(Game game) {
        this.game = game;

        setWidth(game.getWidth() * cellSize);
        setHeight(game.getHeight() * cellSize);
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
        for (Food f : game.getFoods()) {
            Point p = f.getPosition();
            gc.fillOval(p.xCoord * cellSize, p.yCoord * cellSize, cellSize, cellSize);
        }

        gc.setFill(Color.AQUA);
        for (Obstacle o : game.getObstacles()) {
            for (Point p : o.getPositions()) {
                gc.fillRect(p.xCoord * cellSize, p.yCoord * cellSize, cellSize, cellSize);
            }
        }

        gc.setFill(Color.CORAL);
        for (Point p : game.getSnake().getSnake()) {
            gc.fillRect(p.xCoord * cellSize, p.yCoord * cellSize, cellSize, cellSize);
        }

        gc.setFill(Color.ORANGE);
        Point head = game.getSnake().getHead();
        gc.fillRect(head.xCoord * cellSize, head.yCoord * cellSize, cellSize, cellSize);

        if (game.isGameOver()) {
            drawText(gc, "GAME OVER");
        }

        if (game.isGameWon()) {
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