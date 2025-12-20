package ru.nsu.sidorenko;

/**
 * Реализация частного случая элемента класса Text: жирный шрифт.
 * Жирный шрифт для Markdown - две "звёздочки" в начале и в конце блока текста.
 */
public class Bold extends Text {

    /**
     * Конструктор класса, использующий конструктор родительского класса Text.
     *
     * @param content - содержимое текстового блока.
     */
    public Bold(String content) {
        super(content);
    }

    @Override
    public String serialize() {
        return "**" + super.serialize() + "**";
    }
}
