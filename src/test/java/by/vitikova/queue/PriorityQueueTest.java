package by.vitikova.queue;

import by.vitikova.exception.EmptyQueueException;
import by.vitikova.queue.impl.PriorityQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static by.vitikova.constant.Constant.EMPTY_QUEUE_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriorityQueueTest {

    @Test
    public void addShouldAddNewNumberElementInQueue() {
        Queue<Integer> priorityQueue = new PriorityQueue<>(3);

        priorityQueue.add(30);
        assertEquals(30, priorityQueue.peek());

        priorityQueue.add(20);
        assertEquals(20, priorityQueue.peek());

        priorityQueue.add(10);
        assertEquals(10, priorityQueue.peek());
    }

    @Test
    public void addShouldAddNewStringElementInQueue() {
        Queue<String> priorityQueue = new PriorityQueue<>(3);

        priorityQueue.add("tree");
        assertEquals("tree", priorityQueue.peek());

        priorityQueue.add("two");
        assertEquals("tree", priorityQueue.peek());

        priorityQueue.add("one");
        assertEquals("one", priorityQueue.peek());
    }

    @Test
    public void peekShouldReturnMinNumberElement() {
        Queue<Integer> priorityQueue = new PriorityQueue<>(3);

        priorityQueue.add(30);
        priorityQueue.add(20);
        priorityQueue.add(10);

        assertEquals(10, priorityQueue.peek());
    }

    @Test
    public void peekShouldReturnMinStringElement() {
        Queue<String> priorityQueue = new PriorityQueue<>(3);

        priorityQueue.add("tree");
        priorityQueue.add("two");
        priorityQueue.add("one");
        assertEquals("one", priorityQueue.peek());
    }

    @Test
    public void pollShouldDeleteMinElement() {
        Queue<Integer> priorityQueue = new PriorityQueue<>(3);

        priorityQueue.add(30);
        priorityQueue.add(20);
        priorityQueue.add(10);

        assertEquals(Integer.valueOf(10), priorityQueue.poll());
        assertEquals(Integer.valueOf(20), priorityQueue.peek());

        assertEquals(Integer.valueOf(20), priorityQueue.poll());
        assertEquals(Integer.valueOf(30), priorityQueue.peek());

        assertEquals(Integer.valueOf(30), priorityQueue.poll());
        assertTrue(isQueueEmpty(priorityQueue));
    }

    @Test
    public void pollShouldReturnThrowWhereEmptyQueue() {
        Queue<Integer> priorityQueue = new PriorityQueue<>(3);

        Throwable thrown = Assertions.assertThrows(EmptyQueueException.class, () -> priorityQueue.poll());

        Assertions.assertEquals(EMPTY_QUEUE_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void peekShouldReturnThrowWhereEmptyQueue() {
        Queue<Integer> priorityQueue = new PriorityQueue<>(3);

        Throwable thrown = Assertions.assertThrows(EmptyQueueException.class, () -> priorityQueue.peek());

        Assertions.assertEquals(EMPTY_QUEUE_ERROR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void addWithComparator() {
        Queue<Integer> reversePriorityQueue = new PriorityQueue<>(Comparator.reverseOrder());

        reversePriorityQueue.add(10);
        reversePriorityQueue.add(20);
        reversePriorityQueue.add(30);

        assertEquals(Integer.valueOf(30), reversePriorityQueue.peek());
        assertEquals(Integer.valueOf(30), reversePriorityQueue.poll());
        assertEquals(Integer.valueOf(20), reversePriorityQueue.peek());
    }

    @Test
    public void resizeTest() {
        Queue<Integer> priorityQueue = new PriorityQueue<>(3);
        for (int i = 0; i < 20; i++) {
            priorityQueue.add(i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals(Integer.valueOf(i), priorityQueue.poll());
        }

        assertTrue(isQueueEmpty(priorityQueue));
    }

    private boolean isQueueEmpty(Queue<Integer> priorityQueue) {
        try {
            priorityQueue.peek();
            return false;
        } catch (EmptyQueueException e) {
            return true;
        }
    }
}