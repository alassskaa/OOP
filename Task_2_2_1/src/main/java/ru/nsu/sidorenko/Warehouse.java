package ru.nsu.sidorenko;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс для описания работы склада.
 * У склада есть максимальная вместимость.
 * Склад открыт только в определённые часы.
 */
public class Warehouse {
    private final Queue<Orders> pizzas = new LinkedList<>();
    private final int capacity;
    private boolean closed = false;

    /**
     * Конструктор класса Warehouse.
     *
     * @param capacity - вместимость склада.
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Метод, реализующий имитацию добавления пекарем пиццы на склад.
     *
     * @param order - заказ, для которого изготовлена пицца.
     * @throws InterruptedException - исключение при ошибке.
     */
    public synchronized void putPizza(Orders order) throws InterruptedException {
        while (pizzas.size() == capacity) {
            wait();
        }

        pizzas.add(order);
        System.out.println("[" + order.getId() + "] STORED");
        notifyAll();
    }

    /**
     * Метод, реализующий имитацию получения курьером пиццы со склада.
     *
     * @return заказ, который забрал курьер.
     * @throws InterruptedException - исключение при ошибке.
     */
    public synchronized Orders getPizza() throws InterruptedException {
        while (pizzas.isEmpty() && !closed) {
            wait();
        }

        if (pizzas.isEmpty()) {
            return null;
        }

        Orders order = pizzas.poll();
        notifyAll();
        return order;
    }

    /**
     * Метод для имитации закрытия склада.
     */
    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}
