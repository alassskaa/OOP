package ru.nsu.sidorenko;

/**
 * Базовый элемент документа, обычный текстовый блок без форматирования.
 */
public class Text extends Element {
    private final String content;

    /**
     * Конструктор, создающий элемент класса: блок текста.
     *
     * @param content - содержимое текстового блока.
     */
    public Text(String content) {
        this.content = content;
    }

    @Override
    public String serialize() {
        return content;
    }
}
