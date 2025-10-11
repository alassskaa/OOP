package ru.nsu.sidorenko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class BlackJackTest {

    private BlackJack game;

    @BeforeEach
    void setUp() {
        game = new BlackJack();
    }

    @Test
    void testPlayerBlackjackInstantWin() {
        Deck fakeDeck = new FakeDeck(
                new Card("Червы", "Туз", 11),
                new Card("Пики", "Король", 10),
                new Card("Бубны", "Девятка", 9),
                new Card("Крести", "Семерка", 7)
        );

        game.setDeck(fakeDeck);
        setInput("0\n0\n");

        assertDoesNotThrow(() -> game.game());
    }

    @Test
    void testPlayerTakesCardAndBusts() {
        Deck fakeDeck = new FakeDeck(
                new Card("Червы", "Десятка", 10),
                new Card("Пики", "Дама", 10),
                new Card("Бубны", "Десятка", 10), // добор
                new Card("Крести", "Пятерка", 5)
        );

        game.setDeck(fakeDeck);
        setInput("1\n0\n0\n");

        assertDoesNotThrow(() -> game.game());
    }

    @Test
    void testDealerBlackjack() {
        Deck fakeDeck = new FakeDeck(
                new Card("Червы", "Пятерка", 5),
                new Card("Пики", "Шестерка", 6),
                new Card("Бубны", "Туз", 11),
                new Card("Крести", "Король", 10)
        );

        game.setDeck(fakeDeck);
        setInput("0\n0\n");

        assertDoesNotThrow(() -> game.game());
    }

    @Test
    void testEndGameContinue() {
        setInput("0\n");
        boolean result = game.endGame(1);
        assertFalse(result);
    }

    @Test
    void testPlayerMoveDrawCard() {
        Deck fakeDeck = new FakeDeck(
                new Card("Бубны", "Пятерка", 5),
                new Card("Червы", "Десятка", 10),
                new Card("Пики", "Девятка", 9),
                new Card("Крести", "Семерка", 7)
        );
        game.setDeck(fakeDeck);

        game.dealer.addCard(fakeDeck.drawCard());
        game.dealer.addCard(fakeDeck.drawCard());

        game.playerMove(1);

        assertTrue(game.player.getScore() >= 5);
    }

    @Test
    void testPlayerMoveStop() {
        game.playerMove(0);
        assertFalse(game.playerTurn);
    }

    @Test
    void testDealerMoveRevealsCard() {
        Deck fakeDeck = new FakeDeck(
                new Card("Червы", "Семерка", 7),
                new Card("Пики", "Восьмерка", 8),
                new Card("Бубны", "Девятка", 9),
                new Card("Крести", "Шестерка", 6)
        );

        game.setDeck(fakeDeck);
        game.dealer.addCard(fakeDeck.drawCard());
        game.dealer.addCard(fakeDeck.drawCard());
        game.dealerMove();

        assertTrue(game.dealerShowAll);
    }

    /**
     * Вспомогательный метод для имитации пользовательского ввода
     */
    private void setInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
        game.scanner = new Scanner(System.in);
    }
}
