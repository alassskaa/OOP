package ru.nsu.sidorenko;

import java.util.List;

/**
 * Класс для конфигурации файлов, необходимых для
 * имитации работы пиццерии.
 * В нем реализованы три класса: для пекарей, для курьеров и для всей пиццерии.
 * Данные читаются из файла JSON.
 */
public class Config {
    /**
     * Класс для конфигурации файлов для пиццерии.
     */
    public static class PizzeriaConfig {
        private int warehouseCapacity;
        private long pizzeriaTimeMs;

        /**
         * Конфигурация вместимости склада.
         *
         * @return вместимость склада.
         */
        public int getWarehouseCapacity() {
            return warehouseCapacity;
        }

        /**
         * Конфигурация времени работы пиццерии.
         *
         * @return время работы.
         */
        public long getPizzeriaTimeMs() {
            return pizzeriaTimeMs;
        }
    }

    /**
     * Класс для конфигурации файлов для пекарей.
     */
    public static class BakerConfig {
        private int id;
        private int speedMs;

        /**
         * Конфигурация идентификатора пекаря.
         *
         * @return идентификатор пекаря.
         */
        public int getId() {
            return id;
        }

        /**
         * Конфигурация скорости работы пекаря.
         *
         * @return скорость работы.
         */
        public int getSpeedMs() {
            return speedMs;
        }
    }

    /**
     * Класс для конфигурации файлов для курьеров.
     */
    public static class CourierConfig {
        private int id;
        private int capacity;

        /**
         * Конфигурация идентификатора курьера.
         *
         * @return идентификатор курьера.
         */
        public int getId() {
            return id;
        }

        /**
         * Конфигурация вместимости багажника курьера.
         *
         * @return вместимость багажника.
         */
        public int getCapacity() {
            return capacity;
        }
    }

    private PizzeriaConfig pizzeriaConfig;
    private List<BakerConfig> bakers;
    private List<CourierConfig> couriers;

    /**
     * Конфигурация для пиццерии.
     *
     * @return конфигурация для пиццерии.
     */
    public PizzeriaConfig getPizzeriaConfig() {
        return pizzeriaConfig;
    }

    /**
     * Конфигурация для пекарей.
     *
     * @return конфигурация для пекарей.
     */
    public List<BakerConfig> getBakers() {
        return bakers;
    }

    /**
     * Конфигурация для курьеров.
     *
     * @return конфигурация для курьеров.
     */
    public List<CourierConfig> getCouriers() {
        return couriers;
    }
}