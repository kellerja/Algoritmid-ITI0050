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
}
