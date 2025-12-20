package ru.nsu.sidorenko;

/**
 * Реализация текстового представления заголовка.
 * Заголовок в Markdown - символ(ы) '#' в начале строки.
 * Уровень заголовка определяется количеством символов '#' (от 1 до 6).
 */
public class Heading extends Element {
    private final String text;
    private final int level;

    /**
     * Конструктор класса, заполняющий поля.
     *
     * @param text - содержимое заголовка.
     * @param level - уровень заголовка.
     */
    public Heading(String text, int level) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Heading level must be between 1 and 6.");
        }
        this.text = text;
        this.level = level;
    }

    @Override
    public String serialize() {
        return "#".repeat(level) + " " + text;
    }
}
