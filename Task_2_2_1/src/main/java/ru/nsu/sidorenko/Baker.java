package ru.nsu.sidorenko;

/**
 * Класс для описания работы пекарей.
 * У каждого пекаря свой идентификатор, скорость приготовления блюд.
 * Во время работы пекарей выводятся сообщения о процессе.
 */
public class Baker implements Runnable {
    private final int id;
    private final int speed;
    private final OrdersQueue queue;
    private final Warehouse warehouse;

    /**
     * Конструктор класса.
     *
     * @param id - идентификатор пекаря.
     * @param speed - скорость пекаря.
     * @param queue - очередь заказов.
     * @param warehouse - склад.
     */
    public Baker(int id, int speed, OrdersQueue queue, Warehouse warehouse) {
        this.id = id;
        this.speed = speed;
        this.queue = queue;
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Orders order = queue.take();

                if (order == null) {
                    break;
                }

                System.out.println("[" + order.getId() + "] COOKING by Baker-" + id);
                Thread.sleep(speed);
                warehouse.putPizza(order);
                System.out.println("[" + order.getId() + "] READY by Baker-" + id);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
