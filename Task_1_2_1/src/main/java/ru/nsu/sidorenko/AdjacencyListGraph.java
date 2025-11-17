package ru.nsu.sidorenko;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Реализация графа через список смежности.
 * Каждая вершина хранит список своих соседей.
 */
public class AdjacencyListGraph implements Graph {
    private final Map<Integer, Set<Integer>> adjacencyList;
    private final Set<Integer> vertices;
    private int edgeCount;

    /**
     * Конструктор создаёт пустой граф.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
        this.edgeCount = 0;
    }

    @Override
    public boolean addVertex(int vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        adjacencyList.put(vertex, new HashSet<>());
        return true;
    }

    @Override
    public boolean removeVertex(int vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }

        int incomingEdges = 0;
        for (Set<Integer> neighbors : adjacencyList.values()) {
            if (neighbors.remove(vertex)) {
                incomingEdges++;
            }
        }

        int outgoingEdges = adjacencyList.get(vertex).size();
        
        edgeCount -= (incomingEdges + outgoingEdges);
        adjacencyList.remove(vertex);
        vertices.remove(vertex);
        return true;
    }

    @Override
    public boolean addEdge(int from, int to) {
        if (!vertices.contains(from)
                || !vertices.contains(to)) {
            throw new IllegalArgumentException("Vertices must exist in the graph");
        }
        
        if (adjacencyList.get(from).add(to)) {
            edgeCount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(int from, int to) {
        if (!vertices.contains(from)
                || !vertices.contains(to)) {
            return false;
        }

        if (adjacencyList.get(from).remove(to)) {
            edgeCount--;
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (!vertices.contains(vertex)) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }
        return new ArrayList<>(adjacencyList.get(vertex));
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public boolean hasEdge(int from, int to) {
        if (!vertices.contains(from)
                || !vertices.contains(to)) {
            return false;
        }
        return adjacencyList.get(from).contains(to);
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public void readFromFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.isEmpty()) {
            return;
        }

        adjacencyList.clear();
        vertices.clear();
        edgeCount = 0;

        int vertexCount = Integer.parseInt(lines.get(0).trim());

        for (int i = 0; i < vertexCount; i++) {
            addVertex(i);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+"); //1 или больше пробелов
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
        if (!(obj instanceof Graph other)) { //проверка и присваивание сразу
            return false;
        }

        if (this.getVertexCount() != other.getVertexCount()
                || this.getEdgeCount() != other.getEdgeCount()) {
            return false;
        }

        List<Integer> thisVertices = this.getVertices();
        List<Integer> otherVertices = other.getVertices();
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
        return Objects.hash(adjacencyList, vertices, edgeCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyListGraph {\n");
        sb.append("  Vertices: ").append(vertices.size()).append("\n");
        sb.append("  Edges: ").append(edgeCount).append("\n");
        sb.append("  Adjacency list:\n");
        
        List<Integer> sortedVertices = new ArrayList<>(vertices);
        Collections.sort(sortedVertices);
        
        for (int vertex : sortedVertices) {
            List<Integer> neighbors = new ArrayList<>(adjacencyList.get(vertex));
            Collections.sort(neighbors);
            sb.append("    ").append(vertex).append(" -> ").append(neighbors).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
