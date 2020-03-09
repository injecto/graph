package com.injecto.exercise.graph;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SynchronizedGraphTest {
    @Test
    void linkedListConsistencyTest() {
        // arrange
        var graph = new SynchronizedGraph<Integer>(new UndirectedGraph<>());
        int edgesNum = 500_000;

        // act & assert

        var futures = IntStream.range(0, edgesNum).mapToObj(i ->
            CompletableFuture.runAsync(() -> {
                var src = i;
                var dst = i + 1;
                assertTrue(graph.addEdge(src, dst));
                assertEquals(List.of(src, dst), graph.getPath(src, dst));
            })).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(futures).join();

        var resultList = IntStream.range(0, edgesNum + 1).boxed().collect(Collectors.toList());

        var vertices = new HashSet<Integer>();
        for (Integer v : graph) {
            vertices.add(v);
        }

        assertEquals(new HashSet<>(resultList), vertices);
        assertEquals(resultList, graph.getPath(0, edgesNum));
    }
}
