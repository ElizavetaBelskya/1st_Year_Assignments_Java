

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ModifiableCollection<T> extends AbstractCollection<T> {
    private final int CAPACITY = 1000;
    private final float EXPANSION = 1.5f;
    private T[] arr = (T[]) (new Object[2]);
    private int size = 0;

    @Override
    public Iterator<T> iterator() {
        return new ModifiableCollectionIterator();
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T elem) {
        if (size + 1 > CAPACITY) {
            throw new CapacityOverflowException("New elements cannot be added to the array: " +
                    "there will be an overflow of capacity");
        } else {
            if (size + 1 >= arr.length) {
                T[] newArr;
                if (arr.length * EXPANSION < CAPACITY) {
                    newArr = (T[]) (new Object[(int) (arr.length * EXPANSION)]);
                } else {
                    newArr = (T[]) (new Object[CAPACITY]);
                }

                for (int i = 0; i < size; i++) {
                    newArr[i] = arr[i];
                }
                arr = newArr;
            }
            arr[size] = elem;
            size++;
        }
        return true;
    }

    private class ModifiableCollectionIterator implements Iterator<T> {
        private int cursor = 0;
        private boolean nextWasCalled = false;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            try {
                T obj = arr[cursor];
                cursor++;
                nextWasCalled = true;
                return obj;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if (nextWasCalled) {
                for (int i = cursor - 1; i < size - 1; i++) {
                    arr[i] = arr[i + 1];
                }
                arr[size - 1] = null;
                size--;
                cursor--;
                nextWasCalled = false;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
