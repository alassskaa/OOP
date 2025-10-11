package ru.nsu.sidorenko;

/**
 * Класс для работы с картами в колоде.
 */
public class Card {

    private final String suit;
    private final String rank;
    private final int value;

    /**
     * Метод, создающий обьект класса Card, полями которого являются следующие параметры.
     *
     * @param suit - масть
     * @param rank - значение
     * @param value - стоимость в игре
     */
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Возвращает стоимость карты в игре.
     *
     * @return вернет стоимость карты.
     */
    public int getValue() {
        return value;
    }

    /**
     * Метод, использующийся для проверки, является ли карта тузом.
     *
     * @return True или False
     */
    public boolean isAce() {
        return rank.equals("Туз");
    }

    /**
     * Метод, переопределяющий toString так, чтобы выводилась информация о карте.
     *
     * @return вернет информацию о карте (значение и масть)
     */
    @Override
    public String toString() {
        return rank + " " + suit;
    }
}
