package ru.nsu.sidorenko;

/**
 * Реализация текстового представления блока кода.
 * Блок кода в Markdown - три обратных апострофа в начале и в конце блока кода.
 * Выводится и язык, на котором написан код.
 */
public class CodeBlock extends Element {
    private final String language;
    private final String code;

    /**
     * Конструктор класса, заполняющий поля класса.
     *
     * @param language - язык, на котором написан код.
     * @param code - блок кода.
     */
    public CodeBlock(String language, String code) {
        this.language = language;
        this.code = code;
    }

    @Override
    public String serialize() {
        return "```" + language + "\n" + code + "\n```";
    }
}
