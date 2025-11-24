package ru.nsu.sidorenko;

import java.util.Objects;

/**
 * Класс-элемент итератора хэш-таблицы. Является немутабельным классом.
 * Используется для хранения пары ключ-значение исключительно при итерировании по элементам таблицы.
 * Внутреннее представление хэш-таблицы испольует внутренний класс Node,
 * который является мутабельным.
 *
 * @param <K> - ключ
 * @param <V> - значение

 * @see HashTable
 */
public final class Entry<K, V> {
    private final K key;
    private final V value;

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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entry)) {
            return false;
        }
        Entry<?, ?> e = (Entry<?, ?>) o;
        return Objects.equals(key, e.key) && Objects.equals(value, e.value);
    }
}
