package dataStructure;
import java.util.Iterator;

class LinkedList<E> implements Iterable<E> {
    private class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    // Adds an element at the end of the linked list
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Removes an element from the linked list
    public boolean remove(E element) {
        if (head == null) {
            return false;
        }

        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }

        Node<E> current = head;
        while (current.next != null && !current.next.data.equals(element)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
            return true;
        }

        return false;
    }

    // Checks if the linked list contains an element
    public boolean contains(E element) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Returns the size of the linked list
    public int size() {
        return size;
    }

    // Returns an iterator for the linked list
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}
