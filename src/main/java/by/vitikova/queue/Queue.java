package by.vitikova.queue;

public interface Queue<E> {

    void add(E element);

    E peek();

    E poll();
}