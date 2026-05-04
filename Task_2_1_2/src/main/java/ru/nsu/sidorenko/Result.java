package ru.nsu.sidorenko;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Класс для представления результата обработки
 * участка массива вычислительным узлом.
 */
public class Result implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID taskId;
    private final boolean result;

    /**
     * Конструктор класса.
     *
     * @param taskId - идентификатор задания.
     * @param result - результат обработки участка.
     */
    public Result(UUID taskId, boolean result) {
        this.taskId = taskId;
        this.result = result;
    }

    /**
     * Геттер для идентификатора задания.
     *
     * @return идентификатор задания.
     */
    public UUID getTaskId() {
        return taskId;
    }

    /**
     * Геттер для результата обработки участка
     * массива.
     *
     * @return результат обработки.
     */
    public boolean getResult() {
        return result;
    }
}