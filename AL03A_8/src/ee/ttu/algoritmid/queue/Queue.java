package ee.ttu.algoritmid.queue;

public class Queue {

    // Don't change those lines
    final Stack stack1;
    final Stack stack2;

    public Queue() {
        this.stack1 = new Stack();
        this.stack2 = new Stack();
    }

    public void enqueue(int number) {
        stack1.push(number);
    }

    public int dequeue() {
        if (stack1.isEmpty()) return 0;
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        int returnValue = stack2.pop();
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
        return returnValue;
    }

    public boolean isEmpty() {
        return stack1.isEmpty();
    }
}
