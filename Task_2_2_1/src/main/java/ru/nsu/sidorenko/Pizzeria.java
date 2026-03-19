package ru.nsu.sidorenko;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для описания работы пиццерии.
 * Имитируется очередь заказов, которые выдаются пекарям.
 * После изготовления пиццы, пекари кладут ее на склад.
 * Курьеры забирают пиццы со склада и развозят заказы.
 */
public class Pizzeria {
    private final OrdersQueue ordersQueue = new OrdersQueue();
    private Warehouse warehouse;
    private final List<Thread> bakerThreads = new ArrayList<>();
    private final List<Thread> courierThreads = new ArrayList<>();

    /**
     * Метод, имитирующий запуск работы пиццерии.
     * Внутри него читаются конфигурационные данные.
     * Затем происходит имитация работы.
     * После каждого действия выводится сообщение.
     *
     * @param configFilePath - файл конфигурации пиццерии.
     * @throws Exception исключение при ошибке.
     */
    public void start(String configFilePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream(configFilePath);
        if (is == null) {
            throw new RuntimeException("Config file not found: " + configFilePath);
        }
        Config config = mapper.readValue(is, Config.class);

        Config.PizzeriaConfig pizzeriaConfig = config.getPizzeriaConfig();
        warehouse = new Warehouse(pizzeriaConfig.getWarehouseCapacity());

        for (Config.BakerConfig bc : config.getBakers()) {
            Baker baker = new Baker(bc.getId(), bc.getSpeedMs(), ordersQueue, warehouse);
            Thread t = new Thread(baker, "Baker-" + bc.getId());
            bakerThreads.add(t);
            t.start();
        }

        for (Config.CourierConfig cc : config.getCouriers()) {
            Courier courier = new Courier(cc.getId(), cc.getCapacity(), warehouse);
            Thread t = new Thread(courier, "Courier-" + cc.getId());
            courierThreads.add(t);
            t.start();
        }

        Thread orderThread = createOrderThread(pizzeriaConfig.getPizzeriaTimeMs());
        orderThread.start();
        orderThread.join();

        for (Thread t : bakerThreads) {
            t.join();
        }

        synchronized (warehouse) {
            warehouse.close();
        }

        for (Thread t : courierThreads) {
            t.join();
        }

        System.out.println("Pizzeria CLOSED");
    }

    /**
     * Метод для создания очереди заказов.
     *
     * @param workingTimeMs - время работы пиццерии.
     * @return созданный поток.
     */
    private Thread createOrderThread(long workingTimeMs) {
        return new Thread(() -> {
            int orderId = 1;
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < workingTimeMs) {
                Orders order = new Orders(orderId++);
                ordersQueue.put(order);
                System.out.println("[" + order.getId() + "] NEW_ORDER");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            ordersQueue.close();
            System.out.println("OrdersQueue CLOSED");
        }, "OrderGenerator");
    }
}