package ru.nsu.sidorenko;

import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация текстового представления нумерованных списков.
 * Нумерованные списки в Markdown соответствуют формату:
 * 1. content
 * 2. content
 * 3. ...
 */
public class OrderedList extends Element {
    private final List<Element> items;

    /**
     * Конструктор класса, создающий список из указанных элементов.
     *
     * @param items - список из элементов списка.
     */
    public OrderedList(List<Element> items) {
        this.items = items;
    }

    @Override
    public String serialize() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            result.append(i + 1)
                    .append(". ")
                    .append(items.get(i).serialize());

            if (i < items.size() - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }
}
