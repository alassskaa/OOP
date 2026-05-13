package ru.nsu.sidorenko;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Класс для представления задания, отправляемого
 * контроллером вычислительному узлу. Одно и то же
 * задание имеет один и тот же taskId, даже если
 * оно отправляется заново на другой узел при retry.
 */
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID taskId;
    private final List<Integer> chunk;

    /**
     * Конструктор класса.
     *
     * @param taskId - идентификатор задания.
     * @param chunk - участок обрабатываемого массива.
     */
    public Task(UUID taskId, List<Integer> chunk) {
        this.taskId = taskId;
        this.chunk = chunk;
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
     * Геттер для номера обрабатываемого
     * участка массива.
     *
     * @return номер участка.
     */
    public List<Integer> getChunk() {
        return chunk;
    }
}