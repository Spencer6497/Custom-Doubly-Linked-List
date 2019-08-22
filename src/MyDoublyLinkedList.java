import java.util.NoSuchElementException;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E> implements Cloneable {
    int size = 0;
    Node<E> dummyHead;

    // Doubly Linked List constructor
    public MyDoublyLinkedList(){
        dummyHead = new Node<E>(null);
        dummyHead.next = dummyHead;
        dummyHead.prev = dummyHead;
        size = 0;
    }

    // Implement Equals method for MyList
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof MyList)) {
            return false;
        }
        if (((MyList) o).size() != this.size()) {
            return false;
        } else {
            // Use iterator for this part
        }

    }

    // implement getFirst method
    @Override
    public E getFirst() {
        return dummyHead.next.element;
    }

    // Implement getLast method
    @Override
    public E getLast() {
        return dummyHead.prev.element;
    }

    // Implement addFirst method
    @Override
    public void addFirst(E e) {
        Node<E> newFirst = new Node<E>(e);

        // Link new node to both dummy and old first
        newFirst.next = dummyHead.next;
        newFirst.prev = dummyHead;

        // Stitch up dummy and old first
        dummyHead.next.prev = newFirst;
        dummyHead.next = newFirst;

        size++;
    }

    // Implement addLast method
    @Override
    public void addLast(E e) {
        Node<E> newLast = new Node<E>(e);

        // Link new node to both dummy and old last
        newLast.next = dummyHead;
        newLast.prev = dummyHead.prev;

        // Stitch up dummy and old last
        dummyHead.prev.next = newLast;
        dummyHead.prev = newLast;

        size++;
    }

    // Implement removeFirst method
    @Override
    public E removeFirst() {
        // Create container to store popped element
        E removedElement = dummyHead.next.element;

        // Store newFirst in variable
        Node<E> newFirst = dummyHead.next.next;

        // Perform stitch operation
        dummyHead.next = newFirst;
        newFirst.prev = dummyHead;

        // Decrement size and return statement
        size--;
        return removedElement;
    }

    @Override
    public E removeLast() {
        // Create container to store popped element
        E removedElement = dummyHead.prev.element;

        // Store newLast in variable
        Node<E> newLast = dummyHead.prev.prev;

        // Perform stitch operation
        dummyHead.prev = newLast;
        newLast.next = dummyHead;

        // Decrement size and return statement
        size--;
        return removedElement;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator();
    }

    public class ListIterator<E> implements java.util.ListIterator<E> {
        // Declare current node, last node accessed and index
        private Node current = dummyHead;
        private Node last = null;
        private int i = 0;

        // Define method for determining if a list has a next node
        @Override
        public boolean hasNext() {
            return (i < size);
        }

        // Define method for returning next element in list
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            last = current;
            E e = (E) current.element;
            current = current.next;
            i++;
            return e;
        }

        // Define method for determining if a list has a previous node
        @Override
        public boolean hasPrevious() {
            return (i > size);
        }

        // Define method for returning previous element in list
        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = current.prev;
            E e = (E) current.element;
            i--;
            last = current;
            return e;
        }

        // Define method for returning the next index in the list
        @Override
        public int nextIndex() {
            return i + 1;
        }

        // Define method for returning the previous index in the list
        @Override
        public int previousIndex() {
            return i - 1;
        }

        // Define method for removing the last element that was returned by next() or previous()
        @Override
        public void remove() {
            if (last == null) {
                throw new IllegalStateException();
            }
            Node<E> p = last.prev;
            Node<E> n = last.next;
            p.next = n;
            n.prev = p;
            size--;
            if (current == last) {
                current = n;
            } else {
                i--;
            }
            last = null;
        }

        @Override
        public void set(E e) {

        }

        // Define method for inserting specified element into list
        @Override
        public void add(E e) {

        }
    }

    @Override
    public void add(int index, E e) {

    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public int indexOf(E e) {
        return 0;
    }

    @Override
    public int lastIndexOf(E e) {
        return 0;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public Object set(int index, E e) {
        return null;
    }

    // Class definition for Node object
    public class Node<E> {
        E element;
        Node<E> prev, next;

        // Constructor
        public Node(E e) {
            this.element = e;
        }
    }
}
