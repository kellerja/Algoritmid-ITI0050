package ee.ttu.algoritmid.queue;

public class Deque {

    // Don't change those lines
    Stack stack1;
    Stack stack2;

    public Deque() {
        this.stack1 = new Stack();
        this.stack2 = new Stack();
    }

    public void pushFirst(int number) {
        stack1.push(number);
    }

    public void pushLast(int number) {
        stack2.push(number);
    }

    public int popFirst() {
        if (stack1.isEmpty()) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
        }
        return stack1.pop();
    }

    public int popLast() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public int popMin() {
        int min = Integer.MAX_VALUE;
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        while (!stack1.isEmpty()) {
            int current = stack1.pop();
            if (current < min) min = current;
            stack2.push(current);
        }
        boolean popped = false;
        while (!stack2.isEmpty()) {
            int current = stack2.pop();
            if (!popped && current == min) {
                popped = true;
                continue;
            }
            stack1.push(current);
        }
        return min;
    }

    public void reverse() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        Stack temp = stack1;
        stack1 = stack2;
        stack2 = temp;
    }

}
