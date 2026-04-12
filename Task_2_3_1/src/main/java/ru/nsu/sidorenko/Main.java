package ru.nsu.sidorenko;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.nsu.sidorenko.controller.GameController;
import ru.nsu.sidorenko.model.Game;
import ru.nsu.sidorenko.view.GameView;

/**
 * Главный класс, при помощи которого происходит запуск игры.
 * Расширяет класс Application - базовый класс JavaFx.
 * Запуск происходит через терминал при помощи команды:
 * ./gradle run
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Game game = new Game(30, 20, 7, 5, 5);
        GameView view = new GameView(game);
        GameController controller = new GameController(game);

        StackPane root = new StackPane(view);
        Scene scene = new Scene(root);

        controller.start(scene, view::draw);

        stage.setTitle("Snake");
        stage.setScene(scene);
        stage.show();
        view.draw();
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