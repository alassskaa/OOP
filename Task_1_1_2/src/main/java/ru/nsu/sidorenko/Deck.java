package ru.nsu.sidorenko;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс для создания и работы с колодой карт.
 */
public class  Deck {

    private static final String[] SUITS = {"Червы", "Крести", "Бубны", "Пики"};
    private static final String[] RANKS = {"Двойка", "Тройка", "Четверка", "Пятерка", "Шестерка", "Семерка", "Восьмерка", "Девятка", "Десятка", "Валет", "Дама", "Король", "Туз"};
    private static final int[] VALUES = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private final List<Card> cards = new ArrayList<>();

    /**
     * Создание колоды. Ничего не возвращает и не использует, только создает колоду
     * и перемешивает её.
     */
    public Deck() {

        for (int i = 0; i < SUITS.length; i++) {
            for (int j = 0; j < RANKS.length; j++) {
                cards.add(new Card(SUITS[i], RANKS[j], VALUES[j]));
            }
        }

        shuffle();
    }

    /**
     * Перемешивание колоды карт.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Берет верхнюю карту колоды, удаляет ее из колоды и возвращает.
     *
     * @return вернет верхнюю карту колоды
     */
    public Card drawCard() {
        return cards.remove(0);
    }
}
