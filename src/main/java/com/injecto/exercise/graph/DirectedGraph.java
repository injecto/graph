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

        var searchQueue = new ArrayDeque<SearchPath<V>>();
        searchQueue.add(new SearchPath<>(source, null));

        var visited = new HashSet<V>();
        visited.add(source);

        var probe = searchQueue.poll();
        while (probe != null) {
            if (probe.destination.equals(destination)) {
                return probe.getPath();
            }

            for (V adjacent : vertices.get(probe.destination)) {
                if (visited.add(adjacent)) {
                    var adjacentEdge = new SearchPath<>(adjacent, probe);
                    searchQueue.add(adjacentEdge);
                }
            }

            probe = searchQueue.poll();
        }

        return Collections.emptyList();
    }

    @Override
    public Iterator<V> iterator() {
        return vertices.keySet().iterator();
    }

    private static class SearchPath<V> {
        private V destination;
        private @Nullable SearchPath<V> traceback;

        private SearchPath(V destination, @Nullable SearchPath<V> traceback) {
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
            SearchPath<?> searchPath = (SearchPath<?>) o;
            return destination.equals(searchPath.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(destination);
        }
    }
}
