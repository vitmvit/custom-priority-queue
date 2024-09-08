package by.vitikova.util;

public class MathUtil {

    /**
     * Вычисляет индекс родительского узла в двоичном дереве по заданному индексу дочернего узла.
     *
     * @param childIndex индекс дочернего узла в двоичном дереве.
     * @return индекс родительского узла.
     */
    public static int calculateParentIndexFromChildIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    /**
     * Вычисляет индекс дочернего узла в двоичном дереве по заданному индексу родительского узла и смещению.
     *
     * @param parentIndex индекс родительского узла в двоичном дереве.
     * @param childOffset смещение для вычисления индекса дочернего узла (обычно 1 для левого ребенка, 2 для правого).
     * @return индекс дочернего узла.
     */
    public static int calculateChildIndexFromParentIndex(int parentIndex, int childOffset) {
        return 2 * parentIndex + childOffset;
    }
}