package ru.nsu.sidorenko;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GraphTest {
    private Graph adjacencyListGraph;
    private Graph adjacencyMatrixGraph;
    private Graph incidenceMatrixGraph;

    @BeforeEach
    void setUp() {
        adjacencyListGraph = new AdjacencyListGraph();
        adjacencyMatrixGraph = new AdjacencyMatrixGraph();
        incidenceMatrixGraph = new IncidenceMatrixGraph();
    }

    @Test
    void testAddVertex() {
        assertTrue(adjacencyListGraph.addVertex(1));
        assertTrue(adjacencyListGraph.addVertex(2));
        assertFalse(adjacencyListGraph.addVertex(1));
        
        assertTrue(adjacencyMatrixGraph.addVertex(1));
        assertTrue(adjacencyMatrixGraph.addVertex(2));
        assertFalse(adjacencyMatrixGraph.addVertex(1));
        
        assertTrue(incidenceMatrixGraph.addVertex(1));
        assertTrue(incidenceMatrixGraph.addVertex(2));
        assertFalse(incidenceMatrixGraph.addVertex(1));
    }

    @Test
    void testRemoveVertex() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        assertTrue(adjacencyListGraph.removeVertex(1));
        assertFalse(adjacencyListGraph.hasVertex(1));
        assertFalse(adjacencyListGraph.removeVertex(3));
        
        adjacencyMatrixGraph.addVertex(1);
        adjacencyMatrixGraph.addVertex(2);
        assertTrue(adjacencyMatrixGraph.removeVertex(1));
        assertFalse(adjacencyMatrixGraph.hasVertex(1));
        
        incidenceMatrixGraph.addVertex(1);
        incidenceMatrixGraph.addVertex(2);
        assertTrue(incidenceMatrixGraph.removeVertex(1));
        assertFalse(incidenceMatrixGraph.hasVertex(1));
    }

    @Test
    void testAddEdge() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        assertTrue(adjacencyListGraph.addEdge(1, 2));
        assertTrue(adjacencyListGraph.hasEdge(1, 2));
        assertFalse(adjacencyListGraph.addEdge(1, 2));
        assertThrows(IllegalArgumentException.class, () -> adjacencyListGraph.addEdge(1, 3));
        
        adjacencyMatrixGraph.addVertex(1);
        adjacencyMatrixGraph.addVertex(2);
        assertTrue(adjacencyMatrixGraph.addEdge(1, 2));
        assertTrue(adjacencyMatrixGraph.hasEdge(1, 2));
        
        incidenceMatrixGraph.addVertex(1);
        incidenceMatrixGraph.addVertex(2);
        assertTrue(incidenceMatrixGraph.addEdge(1, 2));
        assertTrue(incidenceMatrixGraph.hasEdge(1, 2));
    }

    @Test
    void testRemoveEdge() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addEdge(1, 2);
        assertTrue(adjacencyListGraph.removeEdge(1, 2));
        assertFalse(adjacencyListGraph.hasEdge(1, 2));
        assertFalse(adjacencyListGraph.removeEdge(1, 2));
        
        adjacencyMatrixGraph.addVertex(1);
        adjacencyMatrixGraph.addVertex(2);
        adjacencyMatrixGraph.addEdge(1, 2);
        assertTrue(adjacencyMatrixGraph.removeEdge(1, 2));
        
        incidenceMatrixGraph.addVertex(1);
        incidenceMatrixGraph.addVertex(2);
        incidenceMatrixGraph.addEdge(1, 2);
        assertTrue(incidenceMatrixGraph.removeEdge(1, 2));
    }

    @Test
    void testGetNeighbors() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addVertex(3);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(1, 3);
        
        List<Integer> neighbors = adjacencyListGraph.getNeighbors(1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(3));
        
        List<Integer> neighbors2 = adjacencyListGraph.getNeighbors(2);
        assertTrue(neighbors2.isEmpty());
    }

    @Test
    void testGetVertexCount() {
        assertEquals(0, adjacencyListGraph.getVertexCount());
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        assertEquals(2, adjacencyListGraph.getVertexCount());
        adjacencyListGraph.removeVertex(1);
        assertEquals(1, adjacencyListGraph.getVertexCount());
    }

    @Test
    void testGetEdgeCount() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addVertex(3);
        assertEquals(0, adjacencyListGraph.getEdgeCount());
        adjacencyListGraph.addEdge(1, 2);
        assertEquals(1, adjacencyListGraph.getEdgeCount());
        adjacencyListGraph.addEdge(1, 3);
        assertEquals(2, adjacencyListGraph.getEdgeCount());
        adjacencyListGraph.removeEdge(1, 2);
        assertEquals(1, adjacencyListGraph.getEdgeCount());
    }

    @Test
    void testTopologicalSort() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addVertex(3);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(2, 3);
        
        List<Integer> sorted = GraphAlgorithms.topologicalSort(adjacencyListGraph);
        assertEquals(3, sorted.size());
        assertEquals(1, sorted.get(0));
        assertEquals(2, sorted.get(1));
        assertEquals(3, sorted.get(2));
    }

    @Test
    void testTopologicalSortWithCycle() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addVertex(3);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(2, 3);
        adjacencyListGraph.addEdge(3, 1);
        
        assertThrows(IllegalStateException.class, () -> GraphAlgorithms.topologicalSort(adjacencyListGraph));
    }

    @Test
    void testReadFromFile() throws IOException {
        File file = File.createTempFile("graph", ".txt");
        try {
            String content = "3\n0 1\n1 2\n0 2\n";
            Files.write(file.toPath(), content.getBytes());
            
            adjacencyListGraph.readFromFile(file);
            assertEquals(3, adjacencyListGraph.getVertexCount());
            assertEquals(3, adjacencyListGraph.getEdgeCount());
            assertTrue(adjacencyListGraph.hasEdge(0, 1));
            assertTrue(adjacencyListGraph.hasEdge(1, 2));
            assertTrue(adjacencyListGraph.hasEdge(0, 2));
            
            adjacencyMatrixGraph.readFromFile(file);
            assertEquals(3, adjacencyMatrixGraph.getVertexCount());
            assertEquals(3, adjacencyMatrixGraph.getEdgeCount());
            
            incidenceMatrixGraph.readFromFile(file);
            assertEquals(3, incidenceMatrixGraph.getVertexCount());
            assertEquals(3, incidenceMatrixGraph.getEdgeCount());
        } finally {
            file.delete();
        }
    }

    @Test
    void testEquals() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addEdge(1, 2);
        
        Graph graph2 = new AdjacencyListGraph();
        graph2.addVertex(1);
        graph2.addVertex(2);
        graph2.addEdge(1, 2);
        
        assertEquals(adjacencyListGraph, graph2);

        Graph graph3 = new AdjacencyListGraph();
        graph3.addVertex(1);
        graph3.addVertex(2);
        graph3.addVertex(3);
        graph3.addEdge(1, 2);
        
        assertNotEquals(adjacencyListGraph, graph3);
    }

    @Test
    void testEqualsDifferentImplementations() {
        adjacencyListGraph.addVertex(0);
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addEdge(0, 1);
        adjacencyListGraph.addEdge(1, 2);
        
        adjacencyMatrixGraph.addVertex(0);
        adjacencyMatrixGraph.addVertex(1);
        adjacencyMatrixGraph.addVertex(2);
        adjacencyMatrixGraph.addEdge(0, 1);
        adjacencyMatrixGraph.addEdge(1, 2);
        
        incidenceMatrixGraph.addVertex(0);
        incidenceMatrixGraph.addVertex(1);
        incidenceMatrixGraph.addVertex(2);
        incidenceMatrixGraph.addEdge(0, 1);
        incidenceMatrixGraph.addEdge(1, 2);
        
        assertEquals(adjacencyListGraph, adjacencyMatrixGraph);
        assertEquals(adjacencyListGraph, incidenceMatrixGraph);
        assertEquals(adjacencyMatrixGraph, incidenceMatrixGraph);
    }

    @Test
    void testToString() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addEdge(1, 2);
        
        String str = adjacencyListGraph.toString();
        assertNotNull(str);
        assertTrue(str.contains("AdjacencyListGraph"));
        assertTrue(str.contains("Vertices: 2"));
        assertTrue(str.contains("Edges: 1"));
    }

    @Test
    void testRemoveVertexWithEdges() {
        adjacencyListGraph.addVertex(1);
        adjacencyListGraph.addVertex(2);
        adjacencyListGraph.addVertex(3);
        adjacencyListGraph.addEdge(1, 2);
        adjacencyListGraph.addEdge(2, 3);
        adjacencyListGraph.addEdge(1, 3);
        
        assertEquals(3, adjacencyListGraph.getEdgeCount());
        adjacencyListGraph.removeVertex(2);
        assertEquals(1, adjacencyListGraph.getEdgeCount());
        assertTrue(adjacencyListGraph.hasEdge(1, 3));
        assertFalse(adjacencyListGraph.hasEdge(1, 2));
        assertFalse(adjacencyListGraph.hasEdge(2, 3));
    }

    @Test
    void testAllImplementationsTopologicalSort() {
        Graph[] graphs = {
            new AdjacencyListGraph(),
            new AdjacencyMatrixGraph(),
            new IncidenceMatrixGraph()
        };
        
        for (Graph graph : graphs) {
            graph.addVertex(1);
            graph.addVertex(2);
            graph.addVertex(3);
            graph.addEdge(1, 2);
            graph.addEdge(2, 3);
            
            List<Integer> sorted = GraphAlgorithms.topologicalSort(graph);
            assertEquals(3, sorted.size());
            assertEquals(1, sorted.get(0));
            assertEquals(2, sorted.get(1));
            assertEquals(3, sorted.get(2));
        }
    }
}
