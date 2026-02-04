package ru.nsu.sidorenko;

/**
 * Реализация текстового представления цитат.
 * Цитаты в Markdown - строка с символом > в начале.
 * Символ ставится перед каждой строкой цитаты.
 */
public class Quote extends Element {
    private final Element content;

    /**
     * Конструктор класса.
     *
     * @param content - содержимое цитаты, строка.
     */
    public Quote(Element content) {
        this.content = content;
    }

    @Override
    public String serialize() {
        return "> " + content.serialize();
    }
}
