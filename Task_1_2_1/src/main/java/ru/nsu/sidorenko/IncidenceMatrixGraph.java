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
 * Реализация графа через матрицу инцидентности.
 * Матрица имеет размерность (вершины × рёбра), где каждая колонка
 * представляет одно ребро.
 * -1 означает начало ребра, 1 - конец ребра, 0 - не инцидентна.
 */
public class IncidenceMatrixGraph implements Graph {
    private final Map<Integer, Integer> vertexToIndex;
    private final Map<Integer, Integer> indexToVertex;
    private final List<Edge> edges;
    private int vertexCount;
    private int maxIndex;

    /**
     * Внутренний класс для представления ребра.
     */
    private static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null
                    || getClass() != obj.getClass()) {
                return false;
            }
            Edge edge = (Edge) obj;
            return from == edge.from && to == edge.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

    /**
     * Конструктор создаёт пустой граф.
     */
    public IncidenceMatrixGraph() {
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new HashMap<>();
        this.edges = new ArrayList<>();
        this.vertexCount = 0;
        this.maxIndex = 0;
    }

    /**
     * Проверяет, можно ли добавить новую вершину в зависимости от уже существующих.
     * Если можно - добавляет.
     *
     * @param vertex - добавляемая вершина
     */
    private void ensureCapacity(int vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            int index = maxIndex++;
            vertexToIndex.put(vertex, index);
            indexToVertex.put(index, vertex);
        }
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

        edges.removeIf(edge -> edge.from == vertex
                || edge.to == vertex);

        vertexToIndex.remove(vertex);
        indexToVertex.remove(index);

        if (index < maxIndex - 1) {
            int lastIndex = maxIndex - 1;
            int lastVertex = indexToVertex.get(lastIndex);

            vertexToIndex.put(lastVertex, index);
            indexToVertex.put(index, lastVertex);
            indexToVertex.remove(lastIndex);
        }

        maxIndex--;
        vertexCount--;
        return true;
    }

    @Override
    public boolean addEdge(int from, int to) {
        if (!vertexToIndex.containsKey(from)
                || !vertexToIndex.containsKey(to)) {
            throw new IllegalArgumentException("Vertices must exist in the graph");
        }

        Edge edge = new Edge(from, to);
        if (edges.contains(edge)) {
            return false;
        }

        edges.add(edge);
        return true;
    }

    @Override
    public boolean removeEdge(int from, int to) {
        Edge edge = new Edge(from, to);
        return edges.remove(edge);
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (!vertexToIndex.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }

        List<Integer> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.from == vertex) {
                neighbors.add(edge.to);
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
        return edges.contains(new Edge(from, to));
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public int getEdgeCount() {
        return edges.size();
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

        vertexToIndex.clear();
        indexToVertex.clear();
        edges.clear();
        vertexCount = 0;
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
        return Objects.hash(vertexToIndex, edges, vertexCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IncidenceMatrixGraph {\n");
        sb.append("  Vertices: ").append(vertexCount).append("\n");
        sb.append("  Edges: ").append(edges.size()).append("\n");
        sb.append("  Edges list:\n");
        
        List<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort((e1, e2) -> {
            if (e1.from != e2.from) {
                return Integer.compare(e1.from, e2.from);
            }
            return Integer.compare(e1.to, e2.to);
        });
        
        for (Edge edge : sortedEdges) {
            sb.append("    ").append(edge.from).append(" -> ").append(edge.to).append("\n");
        }
        
        List<Integer> sortedVertices = new ArrayList<>(vertexToIndex.keySet());
        Collections.sort(sortedVertices);
        sb.append("  Adjacency:\n");
        for (int vertex : sortedVertices) {
            List<Integer> neighbors = getNeighbors(vertex);
            Collections.sort(neighbors);
            sb.append("    ").append(vertex).append(" -> ").append(neighbors).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
