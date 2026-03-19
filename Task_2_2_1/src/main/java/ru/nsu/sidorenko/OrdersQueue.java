package ru.nsu.sidorenko;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс для реализации очереди заказов.
 * Чтобы операции были потокобезопасны,
 * использованы синхронизированные блоки кода.
 */
public class OrdersQueue {
    private final Queue<Orders> queue = new LinkedList<>();
    private boolean closed = false;

    /**
     * Метод для имитации добавления заказа в очередь.
     *
     * @param order - заказ.
     */
    public synchronized void put(Orders order) {
        if (closed) {
            return;
        }
        queue.add(order);
        notifyAll();
    }

    /**
     * Метод для имитации извлечения заказа из очереди.
     *
     * @return результат (null или заказ).
     * @throws InterruptedException исключение при ошибке.
     */
    public synchronized Orders take() throws InterruptedException {
        while (queue.isEmpty() && !closed) {
            wait();
        }

        if (queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    /**
     * Метод для проверки, пуста ли очередь.
     *
     * @return true или false.
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Метод для закрытия очереди заказов.
     */
    public synchronized void close() {
        closed = true;
        notifyAll();
    }
}
