package ru.nsu.sidorenko;

/**
 * Класс для представления информации
 * о вычислительном узле.
 */
public class WorkerInfo {
    private final String host;
    private final int port;

    /**
     * Конструктор класса.
     *
     * @param host - хост вычислительного узла.
     * @param port - порт вычислительного узла.
     */
    public WorkerInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Геттер для хоста, на котором
     * работает вычислительный узел.
     *
     * @return хост.
     */
    public String getHost() {
        return host;
    }

    /**
     * Геттер для порта вычислительного
     * узла.
     *
     * @return порт узла.
     */
    public int getPort() {
        return port;
    }

}