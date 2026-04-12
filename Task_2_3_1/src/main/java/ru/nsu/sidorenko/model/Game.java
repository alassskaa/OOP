package ru.nsu.sidorenko.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Метод, в котором описана основная логика игры.
 */
public class Game {
    private final int width;
    private final int height;
    private final int foodCount;
    private final int obstacleCount;
    private final int winLen;

    private Snake snake;
    private List<Food> foods;
    private List<Obstacle> obstacles;

    private boolean gameOver;
    private boolean gameWon;
    private final Random random;

    /**
     * Конструктор класса.
     *
     * @param width - ширина игрового поля.
     * @param height - высота игрового поля.
     * @param foodCount - количество элементов еды на поле.
     * @param obstacleCount - количество препятствий на поле.
     * @param winLen - длина змейки, необходимая для победы.
     */
    public Game(int width, int height, int foodCount, int obstacleCount, int winLen) {
        this.width = width;
        this.height = height;
        this.foodCount = foodCount;
        this.obstacleCount = obstacleCount;
        this.winLen = winLen;

        this.random = new Random();
        startNewGame();
    }

    /**
     * Метод, реализующий запуск новой игры.
     * Змейка в каждой новой игре появляется в центре поля.
     */
    public void startNewGame() {
        snake = new Snake(width / 2, height / 2);
        foods = new ArrayList<>();
        obstacles = new ArrayList<>();
        gameOver = false;
        gameWon = false;

        generateObstacles(obstacleCount);
        generateFood(foodCount);
    }

    /**
     * Основной цикл игры.
     * При ударе об края поля игра проиграна.
     * При ударе головы змейки о ее хвост игра проиграна.
     * При ударе о препятствие игра проиграна.
     * При сборе элемента еды змейка вырастает на 1.
     * При достижении необходимой длины змейки игрок побеждает.
     */
    public void step() {
        if (gameOver || gameWon) {
            return;
        }

        snake.move();
        Point head = snake.getHead();

        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            gameOver = true;
            return;
        }

        for (Point p : snake.getSnake()) {
            if (p != head && p.equals(head)) {
                gameOver = true;
                return;
            }
        }

        for (Obstacle o : obstacles) {
            for (Point op : o.getPositions()) {
                if (head.equals(op)) {
                    gameOver = true;
                    return;
                }
            }
        }

        for (Food f : foods) {
            if (head.equals(f.getPosition())) {
                snake.grow();
                moveFood(f);
                break;
            }
        }

        if (snake.length() >= winLen) {
            gameWon = true;
        }
    }

    /**
     * Метод для генерации еды.
     * Еда генерируется случайно на свободных клетках.
     *
     * @param count - количество необходимых элементов еды.
     */
    private void generateFood(int count) {
        foods.clear();

        for (int i = 0; i < count; i++) {
            Point p;
            p = randomPoint();
            while (isOccupied(p)) {
                p = randomPoint();
            }
            foods.add(new Food(p.x, p.y));
        }
    }

    /**
     * Перемещение элемента еды на другую свободную клетку.
     * Происходит после того, как змейка съедает еду.
     *
     * @param food - элемент, который был съеден.
     */
    private void moveFood(Food food) {
        Point p;
        p = randomPoint();
        while (isOccupied(p)) {
            p = randomPoint();
        }
        food.setPosition(p.x, p.y);
    }

    /**
     * Генерация препятствий на свободных клетках.
     * Генерируются препятствия случайной длины (от 1 до 6).
     * Расположение препятствия (горизонтально или вертикально)
     * выбирается случайно.
     *
     * @param count - необходимое количество препятствий.
     */
    private void generateObstacles(int count) {
        obstacles.clear();

        for (int i = 0; i < count; i++) {

            int len = 1 + random.nextInt(5);
            boolean horizontal = random.nextBoolean();

            List<Point> positions;

            do {
                positions = new ArrayList<>();
                int x = random.nextInt(width);
                int y = random.nextInt(height);

                for (int j = 0; j < len; j++) {
                    int nx, ny;
                    if (horizontal) {
                        nx = x + j;
                        ny = y;
                    } else {
                        nx = x;
                        ny = y + j;
                    }

                    if (nx >= width || ny >= height) {
                        break;
                    }

                    positions.add(new Point(nx, ny));
                }

            } while (!positionsValid(positions));

            obstacles.add(new Obstacle(positions));
        }
    }

    /**
     * Проверка корректности расположения препятствия.
     * Если расположение препятствия совпадает (хотя бы
     * частично) с расположением какого-либо другого
     * элемента, позиция считается некорректной.
     *
     * @param positions - расположение препятствия.
     * @return false или true.
     */
    private boolean positionsValid(List<Point> positions) {
        if (positions.isEmpty()) {
            return false;
        }

        for (Point p : positions) {
            if (isOccupied(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверка занятости одной клетки.
     *
     * @param p - координаты клетки.
     * @return false или true.
     */
    private boolean isOccupied(Point p) {

        for (Point s : snake.getSnake()) {
            if (s.equals(p)) {
                return true;
            }
        }

        for (Food f : foods) {
            if (f.getPosition().equals(p)) {
                return true;
            }
        }

        for (Obstacle o : obstacles) {
            for (Point op : o.getPositions()) {
                if (op.equals(p)) return true;
            }
        }

        return false;
    }

    /**
     * Генерация координат случайной клетки.
     *
     * @return случайные координаты.
     */
    private Point randomPoint() {
        return new Point(random.nextInt(width), random.nextInt(height));
    }

    /**
     * Геттер для змейки.
     *
     * @return snake.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Геттер для элемента еды.
     *
     * @return food.
     */
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Геттер для препятствия.
     *
     * @return obstacle.
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Проверка на проигрыш.
     *
     * @return false или true;
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Проверка на победу.
     *
     * @return false или true.
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * Геттер ширины поля.
     *
     * @return width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Геттер высоты поля.
     *
     * @return height.
     */
    public int getHeight() {
        return height;
    }
}