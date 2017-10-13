package ee.ttu.algoritmid.queue;

import java.util.ArrayDeque;

/**
 * DO NOT CHANGE THIS FILE
 */
public class Stack {

    private final ArrayDeque<Integer> innerStack = new ArrayDeque<>();

    public void push(Integer number) {
        innerStack.push(number);
    }

    public Integer pop() {
        return innerStack.pop();
    }

    public boolean isEmpty() {
        return innerStack.isEmpty();
    }

    public int size() {
        return innerStack.size();
    }

}
