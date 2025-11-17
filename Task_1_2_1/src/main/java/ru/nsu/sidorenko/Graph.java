package ru.nsu.sidorenko;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для представления графа.
 * Поддерживает операции добавления и удаления вершин и рёбер,
 * получение соседей вершин и чтение из файла.
 */
public interface Graph {
    /**
     * Добавляет вершину в граф.
     *
     * @param vertex - вершина для добавления
     * @return true, если вершина была добавлена, false если уже существует
     */
    boolean addVertex(int vertex);

    /**
     * Удаляет вершину из графа.
     *
     * @param vertex - вершина для удаления
     * @return true, если вершина была удалена, false если не существовала
     */
    boolean removeVertex(int vertex);

    /**
     * Добавляет ребро в граф.
     *
     * @param from - начальная вершина
     * @param to - конечная вершина
     * @return true, если ребро было добавлено, false если уже существует
     */
    boolean addEdge(int from, int to);

    /**
     * Удаляет ребро из графа.
     *
     * @param from - начальная вершина
     * @param to - конечная вершина
     * @return true, если ребро было удалено, false если не существовало
     */
    boolean removeEdge(int from, int to);

    /**
     * Получает список всех соседей вершины (вершин, в которые есть рёбра из данной).
     *
     * @param vertex - вершина
     * @return вернет список соседей
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Проверяет наличие вершины в графе.
     *
     * @param vertex - вершина
     * @return true, если вершина существует в графе
     */
    boolean hasVertex(int vertex);

    /**
     * Проверяет наличие ребра в графе.
     *
     * @param from - начальная вершина
     * @param to - конечная вершина
     * @return true, если ребро существует в графе
     */
    boolean hasEdge(int from, int to);

    /**
     * Получает количество вершин в графе.
     *
     * @return количество вершин
     */
    int getVertexCount();

    /**
     * Получает количество рёбер в графе.
     *
     * @return количество рёбер
     */
    int getEdgeCount();

    /**
     * Получает список всех вершин графа.
     *
     * @return список вершин
     */
    List<Integer> getVertices();

    /**
     * Читает граф из файла.
     * Формат файла: первая строка - количество вершин,
     * последующие строки - рёбра в формате "from - to"
     *
     * @param file - файл для чтения
     * @throws IOException если произошла ошибка чтения
     */
    void readFromFile(File file) throws IOException;

}
