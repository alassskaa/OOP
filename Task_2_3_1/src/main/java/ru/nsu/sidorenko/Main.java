package ru.nsu.sidorenko;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.sidorenko.controller.GameController;
import ru.nsu.sidorenko.model.Game;

/**
 * Главный класс, при помощи которого происходит запуск игры.
 * Расширяет класс Application - базовый класс JavaFx.
 * Запуск происходит через терминал при помощи команды:
 * ./gradle run
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Game game = new Game(30, 20, 7, 5, 15);
        GameController controller = new GameController(game);
        controller.start(stage);
    }

    /**
     * Точка входа. Запускает программу.
     * Запуск происходит при помощи метода launch().
     * Это ключевой метод JavaFX, который запускает
     * JavaFX runtime через метод launch().
     * Этот метод в дальнейшем вызывает метод start(Stage).
     *
     * @param args - аргументы командной строки.
     */
    public static void main(String[] args) {
        launch();
    }
}