package ru.nsu.sidorenko.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.sidorenko.model.Direction;
import ru.nsu.sidorenko.model.Food;
import ru.nsu.sidorenko.model.Game;
import ru.nsu.sidorenko.model.Obstacle;
import ru.nsu.sidorenko.model.Point;
import ru.nsu.sidorenko.view.GameView;

/**
 * Контроллер игры. В данном классе реализована
 * логика игры.
 */
public class GameController {
    private final Game game;
    private Timeline timeline;
    private final Queue<Object> directionQueue = new LinkedList<>();

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
     * Каждые 150 миллисекунд происходит шаг игры.
     *
     * @param stage - объект класса Stage.
     */
    public void start(Stage stage) {
        GameView view = new GameView(this);

        StackPane root = new StackPane(view);
        Scene scene = new Scene(root);

        Timeline pause = new Timeline(
                new KeyFrame(Duration.seconds(2), ev -> {
                    game.startNewGame();
                    view.draw();
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
                    view.draw();
                    break;
            }
            if (dir != null && directionQueue.size() < 2) {
                directionQueue.add(dir);
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            if (!directionQueue.isEmpty()) {
                game.getSnake().setDirection((Direction) directionQueue.poll());
            }
            game.step();
            view.draw();

            if (game.isGameOver() || game.isGameWon()) {
                timeline.stop();
                pause.setCycleCount(1);
                pause.play();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        view.draw();
    }


    /**
     * Получение ширины поля.
     *
     * @return - ширина поля.
     */
    public int getWidth() {
        return game.getWidth();
    }

    /**
     * Получение высоты поля.
     *
     * @return - высота поля.
     */
    public double getHeight() {
        return game.getHeight();
    }

    /**
     * Получение координат элементов еды.
     *
     * @return - расположение еды.
     */
    public List<Point> getFoodCoordinates() {
        List<Point> result = new ArrayList<>();
        for (Food f : game.getFoods()) {
            result.add(f.getPosition());
        }
        return result;
    }

    /**
     * Получение координат препятствий.
     *
     * @return - расположение препятствий.
     */
    public List<Point> getObstacleCoordinates() {
        List<Point> result = new ArrayList<>();

        for (Obstacle o : game.getObstacles()) {
            result.addAll(o.getPositions());
        }

        return result;
    }

    /**
     * Получение расположения змейки.
     *
     * @return - расположение змейки.
     */
    public List<Point> getSnakeCoordinates() {
        return (List<Point>) game.getSnake().getSnake();
    }

    /**
     * Получение расположения головы змейки.
     *
     * @return координаты головы.
     */
    public Point getHead() {
        return game.getSnake().getHead();
    }

    /**
     * Проверка на поражение.
     *
     * @return false или true.
     */
    public boolean isGameOver() {
        return game.isGameOver();
    }

    /**
     * Проверка на победу.
     *
     * @return false или true.
     */
    public boolean isGameWon() {
        return game.isGameWon();
    }
}