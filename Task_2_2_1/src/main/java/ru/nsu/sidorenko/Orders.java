package ru.nsu.sidorenko;

/**
 * Класс для работы с заказами.
 * Каждому заказу присваивается идентификатор.
 */
public class Orders {
    private final int id;

    /**
     * Конструктор класса.
     *
     * @param id - идентификатор заказа.
     */
    public Orders(int id) {
        this.id = id;
    }

    /**
     * Геттер класса для определения идентификатора заказа.
     *
     * @return идентификатор заказа.
     */
    public int getId() {
        return id;
    }
}
