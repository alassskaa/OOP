package ru.nsu.sidorenko;

/**
 * Реализация текстового представления списка задач.
 * Список задач в Markdown соответствует следующему формату:
 * - [x] task 1
 * - [ ] task 2
 * - ...
 * Где х стоит у выполненных задач.
 */
public class TaskListItem extends Element {
    private final String text;
    private final boolean checked;

    /**
     * Конструктор класса.
     *
     * @param text - задачи.
     * @param checked - отметка о выполнении задачи (выполнена или нет).
     */
    public TaskListItem(String text, boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    @Override
    public String serialize() {
        String check;
        if (checked) {
            check = "x";
        } else {
            check = " ";
        }
        return "- [" + check + "] " + text;
    }
}
