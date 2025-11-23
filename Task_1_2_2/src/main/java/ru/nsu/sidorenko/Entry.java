package ru.nsu.sidorenko;

import java.util.Objects;

/**
 * Класс-элемент хэш-таблицы. Используется для хранения
 * пары ключ-значение.
 *
 * @param <K> - ключ
 * @param <V> - значение
 */
public final class Entry<K, V> {
    private final K key;
    private V value;

    /**
     * Конструктор для создания пары.
     *
     * @param key - ключ
     * @param value - значение
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Геттер, возвращающий ключ.
     *
     * @return ключ
     */
    public K getKey() {
        return key;
    }

    /**
     * Геттер, возвращающий значение.
     *
     * @return значение
     */
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(key) + "=" + String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry<?, ?> e = (Entry<?, ?>) o;
        return Objects.equals(key, e.key) && Objects.equals(value, e.value);
    }
}
