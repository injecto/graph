package com.injecto.exercise.graph;

import java.util.List;

public interface Graph<V> extends Iterable<V> {
    /**
     * @return false if vertex is already in the graph
     */
    boolean addVertex(V vertex);

    /**
     * Add edge. If source or destination is not in the graph, it will be added.
     * @return false if edge is already in the graph
     */
    boolean addEdge(V source, V destination);

    /**
     * Return a path through vertices from source to destination. Result contains the source as it's first element,
     * and the destination as it's last element.
     * @return Empty list if path not exist.
     */
    List<V> getPath(V source, V destination);
}
