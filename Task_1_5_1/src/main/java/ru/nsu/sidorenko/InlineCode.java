package ru.nsu.sidorenko;

/**
 * Реализация текстового представления однострочного кода.
 * Однострочный код в Markdown - строка кода, окруженная обратными апострофами,
 * по одному в начале и в конце.
 */
public class InlineCode extends Text {

    /**
     * Конструктор класса, использующий конструктор родительского класса Text.
     *
     * @param content - содержимое строки кода.
     */
    public InlineCode(String content) {
        super(content);
    }

    @Override
    public String serialize() {
        return "`" + super.serialize() + "`";
    }
}
