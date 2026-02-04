package ru.nsu.sidorenko;

import java.util.List;

/**
 * Реализация текстового представления ненумерованных списков.
 * Ненумерованные списки в Markdown соответствуют формату:
 * - content
 * - content
 * - ...
 */
public class UnorderedList extends Element {
    private final List<Element> items;

    /**
     * Конструктор класса.
     *
     * @param items - список элементов списка.
     */
    public UnorderedList(List<Element> items) {
        this.items = items;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            sb.append("- ").append(items.get(i).serialize());
            if (i != items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
