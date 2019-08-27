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
            if (current != dummyHead) {
                str.append(", ");
            } else {
                str.append("]");
            }
        }
        return str.toString();
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
            /* Instantiate new iterators to compare objects
            ListIterator<E> thisIterator = new ListIterator<E>(this);
            ListIterator<E> otherIterator = new ListIterator<E>((MyDoublyLinkedList<E>) o);
            while (thisIterator.hasNext() && otherIterator.hasNext()) {
                if (thisIterator.next() != otherIterator.next()) {
                    return false;
                }
            } */
            return true;
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
            super.size++;
        }
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
        super.size++;
    }

    // Implement removeFirst method
    @Override
    public E removeFirst() {
        // Conditional to test if linkedlist is empty
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            Node<E> temp = dummyHead.next;
            dummyHead.next = dummyHead;
            dummyHead.prev = dummyHead;
            size = 0;
            super.size = 0;
            return temp.element;
        } else {
            Node<E> first = dummyHead.next;
            dummyHead.next = first.next;
            first.next.prev = dummyHead;
            size--;
            super.size--;
            return first.element;
        }
    }

    // Implement removeLast method
    @Override
    public E removeLast() {
        // Conditional to test if linkedlist is empty
        if (size == 0) {
            // Should an exception be thrown or should I return null? TBD....
            throw new NoSuchElementException();
        } else if (size == 1) {
            // Reset LinkedList to initial state
            Node<E> temp = dummyHead.next;
            dummyHead.next = dummyHead;
            dummyHead.prev = dummyHead;
            size = 0;
            super.size = 0;
            return temp.element;
        } else {
            // Iterate through list
            Node<E> current = dummyHead.next;
            while (current.next != dummyHead) {
                current = current.next;
            }
            // Stitch up end of list, return last element
            current.prev.next = dummyHead;
            dummyHead.prev = current.prev;
            size--;
            super.size--;
            return current.element;
        }
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator(index);
    }

    public class ListIterator<E> implements java.util.ListIterator<E> {
        // Declare current node, last node accessed and index
        private Node current = dummyHead.next;
        private Node last = null;
        public boolean canRemove = false;
        private int i = 0;

        public ListIterator(int index ) {
            i = index;
        }

        // Define method for determining if a list has a next node
        @Override
        public boolean hasNext() {
            return (i < size);
        }

        // Define method for returning next element in list
        @Override
        public E next() {
            if (!this.hasNext()) {
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
            return (i > 0);
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
            if (!canRemove) {
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
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
            super.size++;
        }
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean contains(E e) {
        if (e == null) {
            Node<E> current = dummyHead.next;
            for (int i = 0; i < size; i++) {
                if (current.element == null) {
                    return true;
                }
                current = current.next;
            }
        } else {
            Node<E> current = dummyHead.next;
            for (int i = 0; i < size; i++) {
                if (e.equals(current.element)) {
                    return true;
                }
                current = current.next;
            }
            current = current.next;
        }
        return false;
    }

    // Implement get method
    @Override
    public E get(int index) {
        // Test out of bounds
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current = dummyHead.next;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return current.element;
            }
            current = current.next;
        }
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
        if (e == null) {
            Node<E> last = dummyHead.prev;
            for (int i = size; i > 0; i--) {
                if (last.element == null) {
                    return i;
                }
                last = last.prev;
            }
        } else {
            Node<E> last = dummyHead.prev;
            for (int i = size - 1; i > 0; i--) {
                if (e.equals(last.element)) {
                    return i;
                }
                last = last.prev;
            }

        }
        return -1;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size-1) {
            return removeLast();
        } else {
            Node<E> previous = dummyHead.next;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            Node<E> current = previous.next;
            previous.next = current.next;
            current.next.prev = previous;
            size--;
            super.size--;
            return current.element;
        }
    }

    @Override
    public Object set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            // Create new node from e
            Node<E> newNode = new Node<E>(e);

            // Set variable for target replacement node
            Node<E> target = dummyHead.next;

            // Iterate through list until target is found
            for(int i=0; i < index; i++) {
                target = target.next;
            }

            // Stitch up nodes in between, return target
            target.prev.next = newNode;
            target.next.prev = newNode;
            newNode.next = target.next;
            newNode.prev = target.prev;
            return target.element;
        }
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
