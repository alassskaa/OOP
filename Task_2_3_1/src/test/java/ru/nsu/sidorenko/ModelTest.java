package ru.nsu.sidorenko;

import java.util.ArrayList;
import ru.nsu.sidorenko.model.Direction;
import ru.nsu.sidorenko.model.Food;
import ru.nsu.sidorenko.model.Game;
import java.util.List;
import ru.nsu.sidorenko.model.Obstacle;
import ru.nsu.sidorenko.model.Point;
import ru.nsu.sidorenko.model.Snake;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс тестов для написанной программы.
 * Тестируются только файлы model, так как
 * файлы controller и view требуют запущенного
 * игрового окна.
 */
public class ModelTest {

    @Test
    void testSnakeMove() {
        Snake snake = new Snake(5, 5);
        snake.move();
        assertEquals(6, snake.getHead().xCoord);
        assertEquals(5, snake.getHead().yCoord);
    }

    @Test
    void testSnakeGrow() {
        Snake snake = new Snake(5, 5);
        int len = snake.length();
        snake.grow();
        assertEquals(len + 1, snake.length());
    }

    @Test
    void testSnakeReverse() {
        Snake snake = new Snake(5, 5);
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    void testSnakeDirection() {
        Snake snake = new Snake(5, 5);
        snake.setDirection(Direction.UP);
        assertEquals(Direction.UP, snake.getDirection());
    }

    @Test
    void testGameOverWall() {
        Game game = new Game(5, 5, 0, 0, 100);
        for (int i = 0; i < 10; i++) {
            game.step();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    void testStartNewGameReset() {
        Game game = new Game(30, 20, 3, 5, 15);
        for (int i = 0; i < 100; i++) {
            game.step();
        }
        game.startNewGame();
        assertFalse(game.isGameOver());
        assertFalse(game.isGameWon());
    }

    @Test
    void testFoodCountAfterStart() {
        Game game = new Game(30, 20, 3, 5, 15);
        assertEquals(3, game.getFoods().size());
    }

    @Test
    void testPointEquals() {
        assertEquals(new Point(3, 4), new Point(3, 4));
        assertNotEquals(new Point(3, 4), new Point(3, 5));
    }

    @Test
    void testPointHashCode() {
        assertEquals(new Point(3, 4).hashCode(), new Point(3, 4).hashCode());
    }

    @Test
    void testFoodPosition() {
        Food food = new Food(3, 4);
        assertEquals(new Point(3, 4), food.getPosition());
    }

    @Test
    void testFoodSetPosition() {
        Food food = new Food(3, 4);
        food.setPosition(7, 8);
        assertEquals(new Point(7, 8), food.getPosition());
    }

    @Test
    void testObstacleStoresPositions() {
        List<Point> points = List.of(new Point(1, 2), new Point(3, 4));
        Obstacle obstacle = new Obstacle(points);
        assertEquals(2, obstacle.getPositions().size());
        assertEquals(new Point(1, 2), obstacle.getPositions().get(0));
    }

    @Test
    void testObstacleIsDefensiveCopy() {
        List<Point> points = new ArrayList<>(List.of(new Point(1, 2)));
        Obstacle obstacle = new Obstacle(points);
        points.add(new Point(5, 5));
        assertEquals(1, obstacle.getPositions().size());
    }
}
