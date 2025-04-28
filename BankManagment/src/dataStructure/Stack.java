package dataStructure;

import java.util.EmptyStackException;

public class Stack<T> {
    private Node<T> top;
    private int size;

    // Node class to store data and the reference to the next node
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    // Constructor to initialize the stack
    public Stack() {
        top = null;
        size = 0;
    }

    // Push operation to add an element to the top of the stack
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    // Pop operation to remove and return the top element of the stack
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    // Peek operation to return the top element without removing it
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.data;
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return top == null;
    }

    // Return the size of the stack
    public int size() {
        return size;
    }

}
