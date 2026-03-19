package ru.nsu.sidorenko;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для проверки работы программы.
 */
public class PizzeriaTest {

    @Test
    void testMultipleCourierDeliveries() throws InterruptedException {
        OrdersQueue ordersQueue = new OrdersQueue();
        Warehouse warehouse = new Warehouse(4);

        AtomicInteger orderCounter = new AtomicInteger(1);

        Thread orderThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Orders order = new Orders(orderCounter.getAndIncrement());
                ordersQueue.put(order);
                System.out.println("[" + order.getId() + "] NEW_ORDER");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            ordersQueue.close();
            System.out.println("OrdersQueue CLOSED");
        });

        Baker baker1 = new Baker(1, 100, ordersQueue, warehouse);
        Baker baker2 = new Baker(2, 150, ordersQueue, warehouse);
        Thread bakerThread1 = new Thread(baker1);
        Thread bakerThread2 = new Thread(baker2);

        Courier courier1 = new Courier(1, 3, warehouse);
        Courier courier2 = new Courier(2, 2, warehouse);
        Thread courierThread1 = new Thread(courier1);
        Thread courierThread2 = new Thread(courier2);

        orderThread.start();
        bakerThread1.start();
        bakerThread2.start();
        courierThread1.start();
        courierThread2.start();

        orderThread.join();
        bakerThread1.join();
        bakerThread2.join();

        synchronized (warehouse) {
            warehouse.close();
        }

        courierThread1.join();
        courierThread2.join();

        assertTrue(warehouse.isEmpty());
    }
}