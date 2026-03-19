package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для описания работы курьеров.
 * У каждого курьера свой идентификатор, вместимость багажника.
 * Во время работы курьеров выводятся сообщения о процессе.
 */
public class Courier implements Runnable {
    private final int id;
    private final int capacity;
    private final Warehouse warehouse;

    /**
     * Конструктор класса.
     *
     * @param id - идентификатор курьера.
     * @param capacity - вместимость багажника курьера.
     * @param warehouse - склад.
     */
    public Courier(int id, int capacity, Warehouse warehouse) {
        this.id = id;
        this.capacity = capacity;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                List<Orders> boot = new ArrayList<>();

                Orders first = warehouse.removePizza();

                if (first == null) {
                    break;
                }

                boot.add(first);
                for (int i = 1; i < capacity; i++) {
                    Orders next = warehouse.removePizza();
                    if (next == null) break;
                    boot.add(next);
                }

                for (Orders order : boot) {
                    System.out.println("[" + order.getId() + "] PICKED_UP by Courier-" + id);
                }

                System.out.println("Courier-" + id + " DELIVERING " + boot.size() + " pizzas");
                Thread.sleep(8000);
                for (Orders order : boot) {
                    System.out.println("[" + order.getId() + "] DELIVERED by Courier-" + id);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
