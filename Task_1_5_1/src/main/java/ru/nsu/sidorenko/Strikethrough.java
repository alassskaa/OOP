package ru.nsu.sidorenko;

/**
 * Реализация текстового представления зачеркнутого текста.
 * Зачеркнутый текст в Markdown - блок текста с символами "~~"
 * в начале и в конце блока текста.
 */
public class Strikethrough extends Text {

    /**
     * Конструктор класса.
     *
     * @param content - содержимое блока текста.
     */
    public Strikethrough(String content) {
        super(content);
    }

    @Override
    public String serialize() {
        return "~~" + super.serialize() + "~~";
    }
}
