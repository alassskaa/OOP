package ru.nsu.sidorenko;

import java.util.Objects;

/**
 * Абстрактный класс для всех элементов.
 * Обязывает всех наследников иметь метод serialize, который реализует строковое представление.
 * В классе реализованы методы:
 * equals - позволяет сравнивать объекты классов-наследников Elements.
 * hashCode - позволяет кодировать хэш-кодом объекты классов-наследников Elements.
 */
public abstract class Element {

    /**
     * Метод, реализующий текстовое представление объекта класса-наследника.
     *
     * @return текстовое представление.
     */
    public abstract String serialize();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Element element = (Element) obj;
        return Objects.equals(serialize(), element.serialize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialize());
    }
}
