package ru.vsu.cs.ovsyannikov.util;

public interface MyList<E> extends Iterable<E> {

    void add(E element);

    void add(int index, E element);

    void clear();

    E get(int index);

    int indexOf(E element);

    E remove(int index);

    E set(int index, E element);

    int size();

    <T> T[] toArray(T[] a);

    String toString();

    void swapNodes(int left, int right);

    void process();
}
