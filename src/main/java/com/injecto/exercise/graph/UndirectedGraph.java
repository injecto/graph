package com.injecto.exercise.graph;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class UndirectedGraph<V> extends DirectedGraph<V> {
    @Override
    public boolean addEdge(V source, V destination) {
        boolean oneDirectionAdded = super.addEdge(source, destination);
        if (oneDirectionAdded) {
            super.addEdge(destination, source);
        }
        return oneDirectionAdded;
    }
}
