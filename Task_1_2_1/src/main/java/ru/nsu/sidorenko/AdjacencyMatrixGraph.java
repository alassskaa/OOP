package ru.nsu.sidorenko;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Реализация графа через матрицу смежности.
 * Граф представляется в виде двумерного массива, где matrix[i][j] = true,
 * если существует ребро из вершины i в вершину j.
 */
public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] matrix;
    private final Map<Integer, Integer> vertexToIndex;
    private final Map<Integer, Integer> indexToVertex;
    private int vertexCount;
    private int edgeCount;
    private int maxIndex;

    /**
     * Конструктор создаёт пустой граф.
     */
    public AdjacencyMatrixGraph() {
        this.matrix = new boolean[10][10];
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new HashMap<>();
        this.vertexCount = 0;
        this.edgeCount = 0;
        this.maxIndex = 0;
    }

    /**
     * Проверяет, можно ли добавить новую вершину в зависимости от размера матрицы.
     * Если можно - добавляет.
     *
     * @param vertex - добавляемая вершина
     */
    private void ensureCapacity(int vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            int index = maxIndex++;
            vertexToIndex.put(vertex, index);
            indexToVertex.put(index, vertex);

            if (index >= matrix.length) {
                int newSize = Math.max(index + 1, matrix.length * 2);
                boolean[][] newMatrix = new boolean[newSize][newSize];
                for (int i = 0; i < matrix.length; i++) {
                    System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix.length);
                }
                matrix = newMatrix;
            }
        }
    }

    /**
     * Возвращает индекс вершины в матрице смежности.
     *
     * @param vertex - вершина, индекс которой хотим получить
     * @return вернет индекс
     */
    private int getIndex(int vertex) {
        Integer index = vertexToIndex.get(vertex);
        if (index == null) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }
        return index;
    }

    @Override
    public boolean addVertex(int vertex) {
        if (vertexToIndex.containsKey(vertex)) {
            return false;
        }
        ensureCapacity(vertex);
        vertexCount++;
        return true;
    }

    @Override
    public boolean removeVertex(int vertex) {
        Integer index = vertexToIndex.get(vertex);
        if (index == null) {
            return false;
        }

        int removedEdges = 0;
        for (int i = 0; i < maxIndex; i++) {
            if (matrix[index][i]) {
                removedEdges++;
            }
            if (matrix[i][index]) {
                removedEdges++;
            }
        }
        if (matrix[index][index]) {
            removedEdges--;
        }

        vertexToIndex.remove(vertex);
        indexToVertex.remove(index);

        if (index < maxIndex - 1) {
            int lastIndex = maxIndex - 1;
            int lastVertex = indexToVertex.get(lastIndex);

            vertexToIndex.put(lastVertex, index);
            indexToVertex.put(index, lastVertex);
            indexToVertex.remove(lastIndex);

            System.arraycopy(matrix[lastIndex], 0, matrix[index], 0, maxIndex);
            for (int i = 0; i < maxIndex; i++) {
                matrix[i][index] = matrix[i][lastIndex];
            }
        }

        maxIndex--;
        vertexCount--;
        edgeCount -= removedEdges;
        return true;
    }

    @Override
    public boolean addEdge(int from, int to) {
        if (!vertexToIndex.containsKey(from)
                || !vertexToIndex.containsKey(to)) {
            throw new IllegalArgumentException("Vertices must exist in the graph");
        }

        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);

        if (matrix[fromIndex][toIndex]) {
            return false;
        }

        matrix[fromIndex][toIndex] = true;
        edgeCount++;
        return true;
    }

    @Override
    public boolean removeEdge(int from, int to) {
        if (!vertexToIndex.containsKey(from)
                || !vertexToIndex.containsKey(to)) {
            return false;
        }

        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);

        if (!matrix[fromIndex][toIndex]) {
            return false;
        }

        matrix[fromIndex][toIndex] = false;
        edgeCount--;
        return true;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }

        int vertexIndex = getIndex(vertex);
        List<Integer> neighbors = new ArrayList<>();

        for (int i = 0; i < maxIndex; i++) {
            if (matrix[vertexIndex][i]) {
                neighbors.add(indexToVertex.get(i));
            }
        }

        return neighbors;
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertexToIndex.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(int from, int to) {
        if (!vertexToIndex.containsKey(from)
                || !vertexToIndex.containsKey(to)) {
            return false;
        }

        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);
        return matrix[fromIndex][toIndex];
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertexToIndex.keySet());
    }

    @Override
    public void readFromFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.isEmpty()) {
            return;
        }

        matrix = new boolean[10][10];
        vertexToIndex.clear();
        indexToVertex.clear();
        vertexCount = 0;
        edgeCount = 0;
        maxIndex = 0;

        int vertexCount = Integer.parseInt(lines.get(0).trim());

        for (int i = 0; i < vertexCount; i++) {
            addVertex(i);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            if (parts.length >= 2) {
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                addEdge(from, to);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Graph other)) {
            return false;
        }

        if (this.getVertexCount() != other.getVertexCount()
                || this.getEdgeCount() != other.getEdgeCount()) {
            return false;
        }

        List<Integer> thisVertices = this.getVertices();
        List<Integer> otherVertices = other.getVertices();
        Collections.sort(thisVertices);
        Collections.sort(otherVertices);
        if (!thisVertices.equals(otherVertices)) {
            return false;
        }

        for (int from : thisVertices) {
            List<Integer> thisNeighbors = this.getNeighbors(from);
            List<Integer> otherNeighbors = other.getNeighbors(from);
            Collections.sort(thisNeighbors);
            Collections.sort(otherNeighbors);
            if (!thisNeighbors.equals(otherNeighbors)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexToIndex, edgeCount, vertexCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyMatrixGraph {\n");
        sb.append("  Vertices: ").append(vertexCount).append("\n");
        sb.append("  Edges: ").append(edgeCount).append("\n");
        
        List<Integer> sortedVertices = new ArrayList<>(vertexToIndex.keySet());
        Collections.sort(sortedVertices);
        
        sb.append("  Adjacency matrix:\n");
        for (int vertex : sortedVertices) {
            List<Integer> neighbors = getNeighbors(vertex);
            Collections.sort(neighbors);
            sb.append("    ").append(vertex).append(" -> ").append(neighbors).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
