package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с картами на руках у участников.
 */
public class Hand {

    private List<Card> cards = new ArrayList<>();

    /**
     * Метод, позволяющий добавить новую карту в конец списка.
     *
     * @param card - карта, которую хотим добавить
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Геттер, позволяющий другим полям прочитать поле cards.
     *
     * @return возвращает список карт
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Метод, при помощи которого показывается только первая карта дилера.
     *
     * @return возвращает первую карту дилера
     */
    public Card getFirstCard() {
        return cards.get(0);
    }

    /**
     * Метод, возвращающий сумму карт на руках.
     * Тузы учитываются по описанным в задании правилам: если с ними сумма превышает 21, то их значение
     * меняется с 11 на 1.
     *
     * @return возвращает сумму карт на руках
     */
    public int getValue() {
        int total = 0;
        int aces = 0;

        for (Card card : cards) {
            int value = card.getValue();
            if (card.isAce()) {
                aces++;
            }
            total += value;
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    /**
     * Метод, проверяющий, не превышает ли количество очков на руках 21.
     *
     * @return возвращает True, если превышает, False, если нет
     */
    public boolean isLoss() {
        return getValue() > 21;
    }

    /**
     * Метод, определяющий, является ли комбинация на руках блекджеком.
     *
     * @return возвращает True или False
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    /**
     * Очистка руки игрока для начала нового раунда.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Метод, с помощью которого получаем нужную по счету карту из руки игрока/дилера.
     *
     * @param index - индекс нужной карты в колоде
     * @return вернет нужную карту
     */
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        } else {
            return null;
        }
    }

    /**
     * Метод, переопределяющий toString, так, чтобы выводилась строка с информацией
     * о картах на руках через запятую.
     *
     * @return возвращает строку с информацией о картах на руках (значение и масть)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : cards) {
            sb.append(c).append(", ");
        }
        return sb.toString();
    }
}
