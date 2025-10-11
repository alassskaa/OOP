package ru.nsu.sidorenko;

import java.util.LinkedList;
import java.util.List;

public class FakeDeck extends Deck {

    private LinkedList<Card> cards = new LinkedList<>();

    public FakeDeck(Card... initialCards) {
        for (Card c : initialCards) {
            cards.add(c);
        }
    }

    @Override
    public Card drawCard() {
        if (cards.isEmpty()) {
            return new Card("Пусто", "Пусто", 0);
        }
        return cards.removeFirst();
    }
}