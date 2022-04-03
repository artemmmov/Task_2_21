package ru.vsu.cs.ovsyannikov.util;

import java.util.*;
import java.util.function.Consumer;

public final class MyLinkedList<E> implements MyList<E> {

    private int size = 0;

    private Node<E> first;

    private Node<E> last;

    @Override
    public void add(E element) {
        linkLast(element);
    }

    @Override
    public void add(int index, E element) {
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    @Override
    public void clear() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        Iterator<E> iterator = iterator();
        iterator.forEachRemaining(p -> joiner.add(p.toString()));
        return joiner.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyListIterator(0);
    }

    /**
     * Swap nodes in list. USED FOR TASK 1
     *
     * @param left  - left node id
     * @param right - right node id
     */
    @Override
    public void swapNodes(int left, int right) {
        Node<E> prevNode1 = null;
        Node<E> prevNode2 = null;
        Node<E> node1 = first;
        Node<E> node2 = first;

        // Checks if list is empty
        if (first == null) {
            return;
        }

        // If left and right are equal, then list will remain the same
        if (left == right) {
            return;
        }

        // Search for node1 index
        int tmp1 = 0;
        while (node1 != null && tmp1 != left) {
            prevNode1 = node1;
            node1 = node1.next;
            tmp1++;
        }

        //Search for node2 index
        int tmp2 = 0;
        while (node2 != null && tmp2 != right) {
            prevNode2 = node2;
            node2 = node2.next;
            tmp2++;
        }

        if (node1 != null && node2 != null) {

            // If previous node to node1 is not null then, it will point to node2
            if (prevNode1 != null) {
                prevNode1.next = node2;
            } else {
                first = node2;
            }

            // If previous node to node2 is not null then, it will point to node1
            if (prevNode2 != null) {
                prevNode2.next = node1;
            } else {
                first = node1;
            }

            // Swaps the next nodes of node1 and node2
            Node<E> temp = node1.next;
            node1.next = node2.next;
            node2.next = temp;

        } else {
            System.out.println("Swapping is not possible");
        }
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Out of bounds: " + index);
        }
    }

    private Node<E> node(int index) {
        Node<E> x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private Node<E> findPrevNode(Node<E> successor) {
        Node<E> pred = first;
        if (pred != null) {
            for (int i = 0; i < size; i++) {
                if (Objects.equals(pred.next, successor)) {
                    break;
                } else {
                    pred = pred.next;
                }
            }
        }
        return pred;
    }

    private void linkBefore(E e, Node<E> successor) {
        Node<E> pred = findPrevNode(successor);
        final Node<E> newNode = new Node<>(e, successor);
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    private E unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = findPrevNode(x);

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            x.next = null;
        }

        x.item = null;
        size--;
        return element;
    }

    private class MyListIterator implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;

        MyListIterator(int index) {
            next = (index == size) ? null : node(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastReturned = next = (next == null) ? last : findPrevNode(next);
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();

            Node<E> lastNext = lastReturned.next;
            unlink(lastReturned);
            if (next == lastReturned)
                next = lastNext;
            else
                nextIndex--;
            lastReturned = null;
        }

        public void set(E e) {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.item = e;
        }

        public void add(E e) {
            lastReturned = null;
            if (next == null)
                linkLast(e);
            else
                linkBefore(e, next);
            nextIndex++;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            while (nextIndex < size) {
                action.accept(next.item);
                lastReturned = next;
                next = next.next;
                nextIndex++;
            }
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }



}
