package ru.kpfu.itis.belskaya;

import java.util.*;
import java.util.stream.Collectors;

public class MySet<T> extends AbstractSet<T> {
    private final int CAPACITY = 1000;
    private final float EXPANSION = 1.5f;
    private T[] arr = (T[])(new Object[2]);
    private int length = 0;


    public MySet() {
    }

    public MySet(Collection<T> col) {
        Iterator<T> iter = col.iterator();
        while (iter.hasNext()) {
            add(iter.next());
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return (length == 0);
    }

    @Override
    public boolean contains(Object o) {
        T elem = (T) o;
        for (int i = 0; i < length; i++) {
            if (elem.equals(arr[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] newArr = new Object[length];
        Iterator<T> iter = iterator();
        int i = 0;
        while (iter.hasNext()) {
            newArr[i] = iter.next();
            i++;
        }
        return newArr;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) throws IllegalArgumentException {
        try {
            if (a.length < length) {
                a = (T1[]) (new Object[length]);
                for (int i = 0; i < length; i++) {
                    a[i] = (T1) arr[i];
                }
            } else if (a.length <= length) {
                for (int i = 0; i < length; i++) {
                    a[i] = (T1) arr[i];
                }
            } else {
                for (int i = 0; i < length; i++) {
                    a[i] = (T1) arr[i];
                }
                for (int i = length; i < a.length; i++) {
                    a[i] = null;
                }
            }
        } catch (ArrayStoreException e) {
            throw new IllegalArgumentException("It is not possible to add set elements to this array");
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        boolean wasInSet = false;
        if (arr.length + 1 > CAPACITY) {
            throw new IllegalStateException("New elements cannot be added to the set: " +
                    "there will be an overflow of capacity");
        } else if (t == null) {
            throw new NullPointerException("This set does not support adding null elements");
        } else {
            for (int i = 0; i < length; i++) {
                if (arr[i].equals(t)) {
                    wasInSet = true;
                    break;
                }
            }
            if (!wasInSet) {
                if (length + 1 >= arr.length) {
                    T[] newArr = (T[])(new Object[(int) (arr.length * EXPANSION)]);
                    for (int i = 0; i < length; i++) {
                        newArr[i] = arr[i];
                    }
                    arr = newArr;
                }
                arr[length] = t;
                length++;
            }
        }
        return !wasInSet;
    }

    @Override
    public boolean remove(Object o) throws NullPointerException {
        boolean wasInSet = false;
        if (o == null) {
            throw new NullPointerException("This set does not support adding null elements");
        } else {
            int index = 0;
            for (int i = 0; i < length; i++) {
                if (arr[i].equals(o)) {
                    wasInSet = true;
                    index = i;
                    break;
                }
            }
            if (wasInSet) {
                for (int i = index; i < length - 1; i++) {
                    arr[i] = arr[i+1];
                }
                arr[length] = null;
                length--;
            }
        }
        return wasInSet;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean contains = true;
        T [] arrayForCollection = (T []) (new Object[c.size()]);
        T[] collectionArray = c.toArray(arrayForCollection);
        for (int i = 0; i < c.size(); i++) {
            if (!contains(collectionArray[i])) {
                contains = false;
                break;
            }
        }
        return contains;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        T [] arrayForCollection = (T []) (new Object[c.size()]);
        T[] collectionArray = c.toArray(arrayForCollection);
        for (int i = 0; i < c.size(); i++) {
            add(collectionArray[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Object [] arrayForCollection = new Object[c.size()];
        Object[] collectionArray = c.toArray(arrayForCollection);
        int oldLength = length;
        for (int i = 0; i < c.size(); i++) {
            remove(collectionArray[i]);
        }
        return length < oldLength;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int oldLength = length;
        T[] elementsForRemoving = (T[]) (new Object[length]);
        for (int i = 0; i < length; i++) {
            if (!c.contains(arr[i])) {
                elementsForRemoving[i] = arr[i];
            }
        }
        for (int i = 0; i < oldLength; i++) {
            if (elementsForRemoving[i] != null) {
                remove(elementsForRemoving[i]);
            }
        }
        return length < oldLength;
    }

    @Override
    public void clear() {
        arr = (T []) (new Object[2]);
        length = 0;
    }

    @Override
    public String toString() {
        T[] newArr = (T[])(new Object[length]);
        for (int i = 0; i < length; i++) {
            newArr[i] = arr[i];
        }
        return Arrays.toString(newArr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MySet)) return false;
        if (!super.equals(o)) return false;
        MySet<?> set = (MySet<?>) o;
        return equalsForIncoherentArrays(arr, set.arr);
    }

    private   <T> boolean equalsForIncoherentArrays(T[] first, T[] second) {
        if (first.length != second.length) {
            return false;
        }
        for (int j = 0; j < length; j++) {
            boolean founded = false;
            for (int i = 0; i < second.length; i++) {
                if (first[j].equals(second[i])) {
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < length; i++) {
            result += 31*result + arr[i].hashCode();
        }
        return result;
    }

    private class SetIterator implements Iterator<T> {
        private int cursor = 0;
        private int [] randomOrder = order();
        private int[] order() {
            int i, j;
            int[] order = new int[length];
            for (i = 0; i < order.length; ) {
                int randomNumber = (new Random()).nextInt(length);
                for (j = 0; j < i; j++) {
                    if (order[j] == randomNumber) {
                        break;
                    }
                }
                if (j == i) {
                    order[i] = randomNumber;
                    i++;
                }
            }
            return order;
        }
        @Override
        public boolean hasNext() {
            return cursor < length;
        }

        @Override
        public T next() {
            try {
                T obj = arr[randomOrder[cursor]];
                cursor++;
                return obj;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }


    }
}
