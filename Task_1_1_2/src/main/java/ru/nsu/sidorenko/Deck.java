package ru.nsu.sidorenko;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс для создания и работы с колодой карт.
 */
public class  Deck {

    private List<Card> cards = new ArrayList<>();

    /**
     * Создание колоды. Ничего не возвращает и не использует, только создает колоду
     * и перемешивает её.
     */
    public Deck() {
        String[] suits = {"Червы", "Крести", "Бубны", "Пики"};
        String[] ranks = {"Двойка", "Тройка", "Четверка", "Пятерка", "Шестерка", "Семерка", "Восьмерка", "Девятка", "Десятка", "Валет", "Дама", "Король", "Туз"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < ranks.length; j++) {
                cards.add(new Card(suits[i], ranks[j], values[j]));
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
