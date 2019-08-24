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

    // Define to String method
    public String toString() {
        // Instantiate new StringBuilder object to make string
        StringBuilder str = new StringBuilder("[");

        // Start at element after dummyHead
        Node<E> current = dummyHead.next;
        for(int i = 0; i < size; i++) {
            str.append(current.element);
            current = current.next;
            if (current != null) {
                str.append(", ");
            } else {
                str.append("]");
            }
        }
        return str.toString();
    }

    /* Define toString method
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(E e : this) {
            sb.append(e + ", ");
        }
        return sb.toString();
    } */

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
            // Not fully build yet, returning false as default until iterator is built
            return false;// Use iterator for this part
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

        // if list is empty
        if (size == 0) {
             newFirst.prev = dummyHead;
             newFirst.next = dummyHead;
             dummyHead.next = newFirst;
             dummyHead.prev = newFirst;
             size++;
             super.size++;
        } else {
            newFirst.prev = dummyHead;
            newFirst.next = dummyHead.next;
            dummyHead.next = newFirst;
            dummyHead.next.prev = newFirst;
            size++;
        }
        /* Link new node to both dummy and old first
        newFirst.next = dummyHead.next;
        newFirst.prev = dummyHead;

        // Stitch up dummy and old first
        dummyHead.next.prev = newFirst;
        dummyHead.next = newFirst;

        size++; */
    }

    // Implement addLast method
    @Override
    public void addLast(E e) {
        Node<E> newLast = new Node<E>(e);

        // Link new node to both dummy and old last
        newLast.next = dummyHead;
        newLast.prev = dummyHead.prev;
        dummyHead.prev.next = newLast;
        dummyHead.prev = newLast;

        size++;
    }

    // Implement removeFirst method
    @Override
    public E removeFirst() {
        // Conditional to test if linkedlist is empty
        if (size == 0) {
            throw new NoSuchElementException();
        }
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
        // Conditional to test if linkedlist is empty
        if (size == 0) {
            throw new NoSuchElementException();
        }
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
        // Boundary cases
        if (index == 0) {
            addFirst(e);
        } else if (index >= size) {
            addLast(e);
        // Insert in middle
        } else {
            Node<E> current = dummyHead.next;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<E>(e);
            (current.next).next = temp;
            size++;
        }
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

    // Define indexOf method as discussed in class
    @Override
    public int indexOf(E e) {
        if (e == null) {
            Node<E> current = dummyHead.next;
            for(int i = 0; i < size; i++) {
                if (current.element == null) {
                    return i;
                }
                current = current.next;
            }
        } else {
            Node<E> current = dummyHead.next;
            for(int i = 0; i < size; i++) {
                if (e.equals(current.element)) {
                    return i;
                }
                current = current.next;
            }
        }
        return -1;
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
