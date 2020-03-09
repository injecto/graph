package com.injecto.exercise.graph;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DirectedGraphTest {
    @Test
    void addVertexTest() {
        // arrange
        var graph = new DirectedGraph<String>();
        String vertex = "V";

        // act
        var added = graph.addVertex(vertex);
        var addedRepeatedly = graph.addVertex(vertex);

        // assert
        assertTrue(added);
        assertFalse(addedRepeatedly);
    }

    @Test
    void addEdgeTest() {
        // arrange
        var graph = new DirectedGraph<String>();

        // act
        var added = graph.addEdge("Src", "Dst");
        var addedReverse = graph.addEdge("Dst", "Src");
        var addedRepeatedly = graph.addEdge("Dst", "Src");

        // assert
        assertTrue(added);
        assertTrue(addedReverse);
        assertFalse(addedRepeatedly);
    }

    @Test
    void getPathInEmptyGraph() {
        // arrange
        var graph = new DirectedGraph<String>();

        // act
        var path = graph.getPath("Src", "Dst");

        // assert
        assertEquals(Collections.emptyList(), path);
    }

    @Test
    void getPathForDestinationSameAsSource() {
        // arrange
        var graph = new DirectedGraph<String>();
        String vertex = "V";
        graph.addVertex(vertex);

        // act
        var path = graph.getPath(vertex, vertex);

        // assert
        assertEquals(List.of(vertex), path);
    }

    @Test
    void getNotExistedPath() {
        // arrange
        var graph = new DirectedGraph<String>();
        String vertex = "V";
        graph.addVertex(vertex);

        // act
        var path = graph.getPath(vertex, "Nowhere");

        // assert
        assertEquals(Collections.emptyList(), path);
    }

    @Test
    void getNotExistedPathInCyclicGraph() {
        // arrange
        var graph = new DirectedGraph<String>();
        String vertex = "V";
        graph.addEdge(vertex, vertex);

        // act
        var path = graph.getPath(vertex, "Nowhere");

        // assert
        assertEquals(Collections.emptyList(), path);
    }

    @Test
    void getPathInLinkedListWithBackCycles() {
        // arrange
        var graph = new DirectedGraph<String>();
        graph.addEdge("A", "A");
        graph.addEdge("A", "B");
        graph.addEdge("B", "A");
        graph.addEdge("B", "C");
        graph.addEdge("C", "A");
        graph.addEdge("C", "D");
        graph.addEdge("D", "A");

        // act
        var path = graph.getPath("A", "D");
        var reversePath = graph.getPath("D", "A");

        // assert
        assertEquals(List.of("A", "B", "C", "D"), path);
        assertEquals(List.of("D", "A"), reversePath);
    }

    @Test
    void verticesIterationTest() {
        // arrange
        var graph = new DirectedGraph<String>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        var vertices = new HashSet<>();

        // act
        for (String vertex : graph) {
            vertices.add(vertex);
        }

        // assert
        assertEquals(Set.of("A", "B", "C"), vertices);
    }
}
