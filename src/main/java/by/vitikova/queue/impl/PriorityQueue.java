package by.vitikova.queue.impl;

import by.vitikova.exception.EmptyQueueException;
import by.vitikova.exception.InvalidInitialCapacityException;
import by.vitikova.queue.Queue;

import java.util.Arrays;
import java.util.Comparator;

import static by.vitikova.constant.Constant.*;

/**
 * Класс PriorityQueue реализует приоритетную очередь на основе массива.
 *
 * @param <E> тип хранимых в очереди элементов.
 */
public class PriorityQueue<E> implements Queue<E> {

    /**
     * Начальная ёмкость по умолчанию.
     */
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    /**
     * Компаратор для определения порядка элементов.
     */
    private final Comparator<? super E> comparator;
    /**
     * Массив для хранения элементов очереди.
     */
    private E[] queue;
    /**
     * Емкость очереди.
     */
    private int capacity;
    /**
     * Размер очереди.
     */
    private int size;

    /**
     * Конструктор по умолчанию создаёт приоритетную очередь с начальной ёмкостью 8.
     */
    public PriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    /**
     * Конструктор инициализирует очередь с заданной ёмкостью.
     * Начальная ёмкость должна быть больше 0.
     *
     * @param initialCapacity начальная ёмкость очереди.
     * @throws IllegalArgumentException если начальная ёмкость меньше 1.
     */
    public PriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Создаёт новый экземпляр приоритетной очереди с заданным компаратором.
     *
     * <p>Приоритетная очередь будет использовать предоставленный компаратор для определения порядка
     * элементов в очереди. Если компаратор равен {@code null}, то будет использоваться естественный порядок
     * элементов, предполагающий, что элементы реализуют интерфейс {@link Comparable}.</p>
     *
     * @param comparator компаратор, используемый для определения порядка элементов в очереди.
     *                   Если равен {@code null}, используется естественный порядок элементов.
     * @throws InvalidInitialCapacityException если компаратор равен {@code null} и элементы не реализуют {@link Comparable}.
     */
    public PriorityQueue(Comparator<? super E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }

    /**
     * Конструктор инициализирует очередь с заданной ёмкостью и компаратором.
     * Возможен null для компаратора, в этом случае будет использован естественный порядок.
     *
     * @param initialCapacity начальная ёмкость очереди.
     * @param comparator      компаратор для конфигурации порядка элементов.
     * @throws InvalidInitialCapacityException если начальная ёмкость меньше 1.
     */
    @SuppressWarnings("unchecked")
    public PriorityQueue(int initialCapacity, Comparator<? super E> comparator) {
        if (initialCapacity < 1) {
            throw new InvalidInitialCapacityException(NULL_ELEMENT_ERROR_MESSAGE);
        }
        queue = (E[]) new Object[initialCapacity];
        this.comparator = comparator;
        this.capacity = initialCapacity;
        this.size = 0;
    }

    /**
     * Добавляет элемент в очередь.
     * При добавлении элемента порядок приоритетов восстанавливается.
     *
     * @param element элемент для добавления в очередь.
     */
    @Override
    public void add(E element) {
        size++;
        resize();
        queue[size - 1] = element;
        siftUp(size - 1);
    }

    /**
     * Возвращает элемент с наивысшим приоритетом без его удаления из очереди.
     *
     * @return элемент с наивысшим приоритетом.
     * @throws EmptyQueueException если очередь пуста.
     */
    @Override
    public E peek() {
        validateNotEmpty();
        return queue[0];
    }

    /**
     * Возвращает и удаляет элемент с наивысшим приоритетом из очереди.
     *
     * @return элемент с наивысшим приоритетом.
     * @throws EmptyQueueException если очередь пуста.
     */
    @Override
    public E poll() {
        validateNotEmpty();
        E min = queue[0];
        queue[0] = queue[--size];
        queue[size] = null;
        siftDown(0);
        return min;
    }

    /**
     * Восстанавливает порядок приоритетов, поднимая элемент вверх.
     *
     * @param i индекс элемента, который поднимается.
     */
    private void siftUp(int i) {
        while (i > 0 && compare(i, (i - 1) / 2) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    /**
     * Восстанавливает порядок приоритетов, опуская элемент вниз.
     *
     * @param i индекс элемента, который опускается.
     */
    private void siftDown(int i) {
        int heapSize = size;
        while (2 * i + 1 < heapSize) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int j = left;
            if (right < heapSize && compare(right, left) < 0) {
                j = right;
            }
            if (compare(i, j) <= 0) {
                break;
            }
            swap(i, j);
            i = j;
        }
    }

    /**
     * Сравнивает два элемента по их индексам.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     * @return отрицательное число, если первый элемент меньше, ноль если равны, положительное, если больше.
     * @throws ClassCastException если элементы не могут быть сравнены.
     */
    @SuppressWarnings("unchecked")
    private int compare(int index1, int index2) {
        E element1 = queue[index1];
        E element2 = queue[index2];

        if (comparator != null) {
            return comparator.compare(element1, element2);
        } else if (element1 instanceof Comparable) {
            return ((Comparable<E>) element1).compareTo(element2);
        } else {
            throw new ClassCastException(COMPARABLE_ERROR_MESSAGE);
        }
    }

    /**
     * Увеличивает ёмкость массива, если он переполнен.
     */
    private void resize() {
        if (size >= queue.length) {
            capacity = queue.length * 2;
            queue = Arrays.copyOf(queue, capacity);
        }
    }

    /**
     * Обмен значениями двух элементов в массиве.
     *
     * @param index1 индекс первого элемента.
     * @param index2 индекс второго элемента.
     */
    private void swap(int index1, int index2) {
        E temp = queue[index1];
        queue[index1] = queue[index2];
        queue[index2] = temp;
    }

    /**
     * Проверяет, пуста ли очередь.
     *
     * @throws EmptyQueueException если очередь пуста.
     */
    private void validateNotEmpty() {
        if (size == 0) {
            throw new EmptyQueueException(EMPTY_QUEUE_ERROR_MESSAGE);
        }
    }
}