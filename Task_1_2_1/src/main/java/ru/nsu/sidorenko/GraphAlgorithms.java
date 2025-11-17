package ru.nsu.sidorenko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Класс с алгоритмами для работы с графами.
 */
public final class GraphAlgorithms {

    /**
     * Приватный конструктор, чтобы запретить создание экземпляров класса.
     */
    private GraphAlgorithms() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Выполняет топологическую сортировку вершин ориентированного графа.
     * Использует алгоритм Кана.
     *
     * @param graph - ориентированный граф
     * @return список вершин в порядке топологической сортировки
     * @throws IllegalStateException если граф содержит циклы
     */
    public static List<Integer> topologicalSort(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        List<Integer> result = new ArrayList<>();

        Map<Integer, Integer> inDegree = new HashMap<>();
        List<Integer> vertices = graph.getVertices();

        for (int vertex : vertices) {
            inDegree.put(vertex, 0);
        }

        for (int vertex : vertices) {
            List<Integer> neighbors = graph.getNeighbors(vertex);
            for (int neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int vertex : vertices) {
            if (inDegree.get(vertex) == 0) {
                queue.offer(vertex);
            }
        }

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);

            List<Integer> neighbors = graph.getNeighbors(vertex);
            for (int neighbor : neighbors) {
                int newInDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, newInDegree);
                if (newInDegree == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (result.size() != graph.getVertexCount()) {
            throw new IllegalStateException(
                    "Graph contains a cycle, topological sort is impossible");
        }

        return result;
    }
}
