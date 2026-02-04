package ru.nsu.sidorenko;

/**
 * Реализация текстового представления изображения.
 * Изображение в Markdown соответствует формату:
 * ![altText](url), где:
 * altText - альтернативный текст, содержащий описание изображения,
 * который пользователь увидит, если произошла ошибка при открытии изображения по url;
 * url - ссылка на изображение в формате URL.
 */
public class Image extends Element {
    private final String altText;
    private final String url;

    /**
     * Конструктор класса.
     *
     * @param altText - альтернативный текст, описывающий изображение при ошибке загрузки.
     * @param url - ссылка на изображение в формате URL.
     */
    public Image(String altText, String url) {
        this.altText = altText;
        this.url = url;
    }

    @Override
    public String serialize() {
        return "![" + altText + "](" + url + ")";
    }
}
