import java.util.*;
import java.util.stream.Stream;

/**
 * Cycle Collection is a data structure with the following features:
 * the number of elements it stores is limited and is set by the user when creating a Cycle Collection object;
 * elements are added sequentially, they are indexed.
 * Adding is only possible to a cell with the following index,
 * but the value in any filled cell can be viewed from view using the get method and replaced with another using the set method.
 * In the case when the collection is full, a new element is added to the place of the first,
 * the next one is added to the place of the second, and so on.
 * At the beginning, the collection is filled with null. It is forbidden to add null elements to cells.
 * Deleting items is allowed.
 *
 * @param <T> the type of data that is placed in the collection
 * @author Elizaveta Belskaya
 */

public class CycleCollection<T> extends AbstractList<T> {
    /**
     * The constant number of elements of the collection, that is set by the user in the constructor
     */
    private int capacity;

    /**
     * Pointer to the next element to be modified
     */
    private int cursor;

    /**
     * Boolean expression that shows whether the collection is filled with elements
     */
    private boolean filled;

    /**
     * Encapsulated capacity-sized array in which collection items are stored
     */
    private T[] arr;

    /**
     * Constructor of an empty cycle collection with the specified initial capacity.
     *
     * @param capacity the constant number of elements of the collection
     * @throws IllegalArgumentException if capacity is non-positive
     */
    public CycleCollection(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("It is not possible to create a collection of a non-positive size.");
        } else {
            this.capacity = capacity;
            this.cursor = 0;
            filled = false;
            arr = (T[]) (new Object[capacity]);
            for (int i = 0; i < capacity; i++) {
                arr[i] = null;
            }
        }
    }

    /**
     * Constructor of a filled cycle collection based on the received collection.
     *
     * @param col a collection whose elements become elements of a new cycle collection
     */
    public CycleCollection(Collection<T> col) {
        this.capacity = col.size();
        this.cursor = 0;
        filled = true;
        arr = (T[]) (new Object[col.size()]);
        T[] newArray = (T[]) (new Object[col.size()]);
        T[] collectionArray = col.toArray(newArray);
        for (int i = 0; i < capacity; i++) {
            arr[i] = collectionArray[i];
        }
    }


    /**
     * The number of elements in the array. The maximum of them can be capacity.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        if (filled) {
            return capacity;
        } else {
            return cursor;
        }
    }

    /**
     * Returns <tt>true</tt> if this collection contains no elements
     *
     * @return <tt>true</tt> if this collection contains no elements
     */
    @Override
    public boolean isEmpty() {
        return (cursor == 0 && !filled);
    }

    /**
     * Returns an iterator over the elements of this cyclic collection,
     * passing through it from the beginning to the end n times.
     *
     * @param n number of iterations
     * @return an iterator over the elements of this cyclic collection, passing through it from the beginning to the end n times.
     */
    public Iterator<T> iterator(int n) {
        return new CycleIteratorRepeated(n);
    }

    /**
     * Returns an iterator over the elements of this cycle collection,
     * passing through the sequence an infinite number of times.
     *
     * @return an iterator over the elements of this cycle collection
     */
    @Override
    public Iterator<T> iterator() {
        return new EndlessCycleIterator();
    }

    /**
     * Returns <tt>true</tt> if this collection contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this collection contains at least one element <tt>e</tt>
     * such that <tt>(o.equals(e))</tt>.
     *
     * @param o the object to be found in the collection
     * @return <tt>true</tt> if this collection contains the specified element
     */
    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        for (int i = 0; i < (filled ? capacity : cursor); i++) {
            if (arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an array of elements from a cycle collection
     *
     * @return an array of elements from a cyclic collection
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        for (int i = 0; i < (size()); i++) {
            array[i] = arr[i];
        }
        return array;
    }

    /**
     * Returns <tt>true</tt> if an item has been added to the cycle collection
     *
     * @param elem an element of type T to be added to the cycle collection
     * @return <tt>true</tt> if the operation was successful
     * @throws IllegalArgumentException - if new elem is null
     */
    @Override
    public boolean add(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException();
        } else {
            if (cursor == capacity) {
                cursor = 1;
                arr[0] = elem;
                filled = true;
            } else {
                arr[cursor] = elem;
                cursor++;
            }
            return true;
        }
    }

    /**
     * Replaces the old value in an already filled cell with a new one
     *
     * @param index the index of the cell to put the new value in
     * @param elem  the new value
     * @return old cell value or null if cell was not filled
     */
    @Override
    public T set(int index, T elem) throws CycleOutOfBoundsException {
        if (elem == null) {
            throw new IllegalArgumentException();
        } else {
            if (index >= capacity) {
                throw new CycleOutOfBoundsException();
            } else if (!filled && (index > cursor)) {
                return null;
            } else {
                T oldValue = arr[index];
                arr[index] = elem;
                return oldValue;
            }
        }
    }

    /**
     * Returns the element at the specified position in this collection.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this collection
     * @throws CycleOutOfBoundsException if the index is greater than or equal to capacity.
     */
    @Override
    public T get(int index) {
        if (index >= size()) {
            throw new CycleOutOfBoundsException();
        } else {
            return arr[index];
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element in this
     * collection, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in this collection, or -1 if this list does not contain the element
     */
    @Override
    public int indexOf(Object o) {
        int id = -1;
        for (int i = 0; i < size(); i++) {
            if (arr[i].equals(o)) {
                id = i;
                break;
            }
        }
        return id;
    }

    /**
     * Returns the index of the last occurrence of the specified element in this
     * collection, or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in this collection, or -1 if this list does not contain the element
     */
    @Override
    public int lastIndexOf(Object o) {
        int id = -1;
        for (int i = size() - 1; i >= 0; i--) {
            if (arr[i].equals(o)) {
                id = i;
                break;
            }
        }
        return id;
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index the index of the element to be removed.
     * @return value of the deleted element or null if cell was not filled
     * @throws CycleOutOfBoundsException if the index is greater than or equal to size
     */
    @Override
    public T remove(int index) {
        if (index >= size()) {
            throw new CycleOutOfBoundsException();
        } else {
            T removedElem = arr[index];
            for (int i = index; i < size() - 1; i++) {
                arr[i] = arr[i + 1];
            }
            arr[size() - 1] = null;
            if (filled) {
                filled = false;
                cursor = capacity;
            }
            if (cursor > index) {
                cursor--;
            }
            return removedElem;
        }
    }

    /**
     * Removes all of the elements from this cycle collection. The collection will
     * be empty after this call returns.
     */
    @Override
    public void clear() {
        cursor = 0;
        for (int i = 0; i < capacity; i++) {
            arr[i] = null;
        }
    }

    /**
     * An iterator class that traverses the collection n times
     */
    private class CycleIteratorRepeated implements Iterator<T> {
        private int n;

        public CycleIteratorRepeated(int n) {
            this.n = n;
        }

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return ((cursor / size()) < n);
        }

        @Override
        public T next() {
            if (hasNext()) {
                T elem = arr[cursor % size()];
                cursor++;
                return elem;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    /**
     * An iterator class that traverses the collection an infinite number of times
     */
    private class EndlessCycleIterator implements Iterator<T> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T elem = arr[cursor % size()];
                cursor++;
                return elem;
            } else {
                throw new NoSuchElementException();
            }
        }

    }

    /**
     * Compares the specified object with this Cycle Collection for equality.
     * Returns <tt>true</tt> if and only if the specified object is also a Cycle Collection,
     * both lists have the same size, and all corresponding pairs of elements in the two Cycle Collections are equal.
     * (Two elements e1 and e2 are equal if (e1==null ? e2==null : e1.equals(e2)).)
     * In other words, two cycle collections are designed to be equal if they contain the same elements in the same order.
     *
     * @param o the object to be compared for equality with this collection
     * @return <tt>true</tt> if this collection is the same as o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || (this.getClass() != o.getClass())) {
            return false;
        }
        CycleCollection array = ((CycleCollection) o);
        if ((size() != array.size()) || (capacity != array.capacity)) {
            return false;
        } else {
            boolean isEqual;
            for (int i = 0; i < size(); i++) {
                if (arr[0].equals(array.get(i))) {
                    int k = i;
                    isEqual = true;
                    for (int j = 0; j < size(); j++) {
                        if (!arr[j].equals(array.get(k % size()))) {
                            isEqual = false;
                        }
                        ;
                        k++;
                    }
                    if (isEqual) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the hash code value for this collection.
     *
     * @return the hash code value for this collection
     */
    @Override
    public int hashCode() {
        int hash = 37;
        for (int i = 0; i < (filled ? capacity : cursor); i++) {
            hash += 37 * (arr[i].hashCode());
        }
        return hash;
    }

    /**
     * Returns a string representation of this collection.
     *
     * @return a string representation of this collection.
     */
    @Override
    public String toString() {
        T[] array = (T[]) toArray();
        String s = "[" + array[0].toString();
        for (int i = 1; i < size(); i++) {
            s += ", " + array[i].toString();
        }
        s += "]";
        return s;
    }

    /**
     * Returns a sequential Stream with this collection as its source.
     *
     * @return a sequential Stream with this cycle collection as its source.
     */
    @Override
    public Stream<T> stream() {
        T[] arrayForStream = (T[]) new Object[size()];
        for (int i = 0; i < size(); i++) {
            arrayForStream[i] = arr[i];
        }
        return Arrays.stream(arrayForStream);
    }

}
