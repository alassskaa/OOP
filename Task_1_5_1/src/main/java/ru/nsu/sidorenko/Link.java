package ru.nsu.sidorenko;

/**
 * Реализация текстового представления ссылки.
 * Ссылка в Markdown соответствует формату:
 * [text](url), где:
 * text - текст, описывающий контент, если при переходе по ссылке произошла ошибка.
 * url - ссылка в формате URL.
 */
public class Link extends Element {
    private final String text;
    private final String url;

    /**
     * Конструктор класса.
     *
     * @param text - текст, описывающий контент при ошибке перехода по ссылке.
     * @param url - ссылка в формате URL.
     */
    public Link(String text, String url) {
        this.text = text;
        this.url = url;
    }

    @Override
    public String serialize() {
        return "[" + text + "](" + url + ")";
    }
}
