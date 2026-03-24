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
     * Параметры пиццерии.
     */
    public static class PizzeriaConfig {
        private int warehouseCapacity;
        private long pizzeriaTimeMs;

        /**
         * Вместимость склада.
         *
         * @return вместимость склада.
         */
        public int getWarehouseCapacity() {
            return warehouseCapacity;
        }

        /**
         * Продолжительность рабочего для пиццерии.
         *
         * @return время работы.
         */
        public long getPizzeriaTimeMs() {
            return pizzeriaTimeMs;
        }
    }

    /**
     * Параметры пекаря.
     */
    public static class BakerConfig {
        private int id;
        private int speedMs;

        /**
         * Идентификатор пекаря.
         *
         * @return идентификатор пекаря.
         */
        public int getId() {
            return id;
        }

        /**
         * Скорость работы пекаря.
         *
         * @return скорость работы.
         */
        public int getSpeedMs() {
            return speedMs;
        }
    }

    /**
     * Параметры курьера.
     */
    public static class CourierConfig {
        private int id;
        private int capacity;

        /**
         * Идентификатор курьера.
         *
         * @return идентификатор курьера.
         */
        public int getId() {
            return id;
        }

        /**
         * Вместимость багажника курьера.
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