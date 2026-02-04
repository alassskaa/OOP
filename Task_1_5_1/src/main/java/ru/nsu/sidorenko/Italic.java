package ru.nsu.sidorenko;

/**
 * Реализация текстового представления курсива.
 * Курсив в Markdown - по одной "звёздочке" в начале и в конце блока текста.
 */
public class Italic extends Text {

    /**
     * Конструктор класса, использующий конструктор родительского класса Text.
     *
     * @param content - содержимое текстового блока.
     */
    public Italic(String content) {
        super(content);
    }

    @Override
    public String serialize() {
        return "*" + super.serialize() + "*";
    }
}
