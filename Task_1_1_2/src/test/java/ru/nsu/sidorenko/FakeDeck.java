package ru.nsu.sidorenko;

import java.util.LinkedList;
import java.util.List;

/**
 * Тестовая колода для игры, позволяющая заранее задать последовательность карт.
 * Используется для тестирования поведения игры.
 */
public class FakeDeck extends Deck {

    private LinkedList<Card> cards = new LinkedList<>();

    /**
     * Создает фиктивную колоду с заданной последовательностью карт.
     *
     * @param initialCards Массив карт, которые будут выдаваться в порядке добавления.
     */
    public FakeDeck(Card... initialCards) {
        for (Card c : initialCards) {
            cards.add(c);
        }
    }

    /**
     * Берет карту из колоды. Возвращает карты в том порядке, в котором они были переданы.
     * Если колода пуста, возвращается карта-заглушка с номиналом 0.
     *
     * @return Следующая карта из колоды или карта-заглушка, если колода пуста.
     */
    @Override
    public Card drawCard() {
        if (cards.isEmpty()) {
            return new Card("Пусто", "Пусто", 0);
        }
        return cards.removeFirst();
    }
}
