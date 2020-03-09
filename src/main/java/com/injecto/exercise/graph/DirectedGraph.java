package com.injecto.exercise.graph;

import javax.annotation.Nullable;
import java.util.*;

public class DirectedGraph<V> implements Graph<V> {
    private final Map<V, Set<V>> vertices = new HashMap<>();

    public boolean addVertex(V vertex) {
        return vertices.putIfAbsent(vertex, new HashSet<>()) == null;
    }

    public boolean addEdge(V source, V destination) {
        var destinations = vertices.computeIfAbsent(source, v -> new HashSet<>());
        addVertex(destination);
        return destinations.add(destination);
    }

    public List<V> getPath(V source, V destination) {
        return breadthFirstSearch(source, destination);
    }

    private List<V> breadthFirstSearch(V source, V destination) {
        if (!vertices.containsKey(source) || !vertices.containsKey(destination)) {
            return Collections.emptyList();
        }

        var searchQueue = new ArrayDeque<Edge<V>>();
        searchQueue.add(new Edge<>(source, null));

        var visited = new HashSet<V>();
        visited.add(source);

        var probe = searchQueue.poll();
        while (probe != null) {
            if (probe.destination.equals(destination)) {
                return probe.getPath();
            }

            for (V adjacent : vertices.get(probe.destination)) {
                if (visited.add(adjacent)) {
                    var adjacentEdge = new Edge<>(adjacent, probe);
                    searchQueue.add(adjacentEdge);
                }
            }

            probe = searchQueue.poll();
        }

        return Collections.emptyList();
    }

    private static class Edge<V> {
        private V destination;
        private @Nullable Edge<V> traceback;

        private Edge(V destination, @Nullable Edge<V> traceback) {
            this.destination = destination;
            this.traceback = traceback;
        }

        private List<V> getPath() {
            var reversedPath = new ArrayList<V>();
            reversedPath.add(destination);

            var traceback = this.traceback;
            while (traceback != null) {
                reversedPath.add(traceback.destination);
                traceback = traceback.traceback;
            }

            Collections.reverse(reversedPath);
            return reversedPath;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?> edge = (Edge<?>) o;
            return destination.equals(edge.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(destination);
        }
    }
}
