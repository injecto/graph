package com.injecto.exercise.graph;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UndirectedGraphTest {
    @Test
    void addVertexTest() {
        // arrange
        var graph = new UndirectedGraph<String>();
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
        var graph = new UndirectedGraph<String>();

        // act
        var added = graph.addEdge("Src", "Dst");
        var addedReverse = graph.addEdge("Dst", "Src");
        var addedRepeatedly = graph.addEdge("Dst", "Src");

        // assert
        assertTrue(added);
        assertFalse(addedReverse);
        assertFalse(addedRepeatedly);
    }

    @Test
    void getPathInEmptyGraph() {
        // arrange
        var graph = new UndirectedGraph<String>();

        // act
        var path = graph.getPath("Src", "Dst");

        // assert
        assertEquals(Collections.emptyList(), path);
    }

    @Test
    void getPathForDestinationSameAsSource() {
        // arrange
        var graph = new UndirectedGraph<String>();
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
        var graph = new UndirectedGraph<String>();
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
        var graph = new UndirectedGraph<String>();
        String vertex = "V";
        graph.addEdge(vertex, vertex);

        // act
        var path = graph.getPath(vertex, "Nowhere");

        // assert
        assertEquals(Collections.emptyList(), path);
    }

    @Test
    void getPathInRing() {
        // arrange
        var graph = new UndirectedGraph<String>();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        graph.addEdge("E", "A");

        // act
        var path = graph.getPath("A", "E");
        var reversePath = graph.getPath("E", "A");
        var anotherPath = graph.getPath("A", "C");

        // assert
        assertEquals(List.of("A", "E"), path);
        assertEquals(List.of("E", "A"), reversePath);
        assertEquals(List.of("A", "B", "C"), anotherPath);
    }

    @Test
    void verticesIterationTest() {
        // arrange
        var graph = new UndirectedGraph<String>();
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
