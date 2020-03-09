package com.injecto.exercise.graph;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ThreadSafe
public class SynchronizedGraph<V> implements Graph<V> {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    @GuardedBy("lock") private final Graph<V> delegate;

    public SynchronizedGraph(Graph<V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean addVertex(V vertex) {
        lock.writeLock().lock();
        try {
           return delegate.addVertex(vertex);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean addEdge(V source, V destination) {
        lock.writeLock().lock();
        try {
            return delegate.addEdge(source, destination);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<V> getPath(V source, V destination) {
        lock.readLock().lock();
        try {
            return delegate.getPath(source, destination);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Iterator<V> iterator() {
        lock.readLock().lock();
        var vertices = new HashSet<V>();
        try {
            for (V v : delegate) {
                vertices.add(v);
            }
        } finally {
            lock.readLock().unlock();
        }
        return vertices.iterator();
    }
}
