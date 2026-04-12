package ru.nsu.sidorenko.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import ru.nsu.sidorenko.model.Direction;
import ru.nsu.sidorenko.model.Game;

/**
 * Контроллер игры. В данном классе реализована
 * логика игры.
 */
public class GameController {
    private final Game game;
    private Timeline timeline;
    private final java.util.Queue<Direction> directionQueue = new java.util.LinkedList<>();


    /**
     * Конструктор класса. Сохраняет игру.
     *
     * @param game - объект класса Game.
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * Метод для реализации логики игры.
     * Внутри него реализована логика нажатий на
     * кнопки клавиатуры, поражения и победы.
     * Каждые 150 секунд происходит шаг игры.
     *
     * @param scene - сцена (содержимое окна игры).
     * @param redrawCallback - функция перерисовки экрана.
     */
    public void start(Scene scene, Runnable redrawCallback) {
        Timeline pause = new Timeline(
                new KeyFrame(Duration.seconds(2), ev -> {
                    game.startNewGame();
                    redrawCallback.run();
                    timeline.play();
                })
        );

        scene.setOnKeyPressed(event -> {
            Direction dir = null;
            switch (event.getCode()) {
                case UP: case W:
                    dir = Direction.UP;
                    break;
                case DOWN: case S:
                    dir = Direction.DOWN;
                    break;
                case LEFT: case A:
                    dir = Direction.LEFT;
                    break;
                case RIGHT: case D:
                    dir = Direction.RIGHT;
                    break;
                case R:
                    game.startNewGame();
                    break;
            }

            if (dir != null && directionQueue.size() < 2) {
                directionQueue.add(dir);
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            if (!directionQueue.isEmpty()) {
                game.getSnake().setDirection(directionQueue.poll());
            }
            game.step();
            redrawCallback.run();

            if (game.isGameOver() || game.isGameWon()) {
                timeline.stop();
                pause.setCycleCount(1);
                pause.play();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}