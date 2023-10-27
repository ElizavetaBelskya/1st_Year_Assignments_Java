package ru.kpfu.itis.belskaya.expandableArray;

import java.util.Arrays;

public class NewExpandableArray<T> implements ArraysStructure<T> {
    private final int CAPACITY = 1000;
    private final float EXPANSION = 1.5f;
    private int length = 0;
    private T [] arr = (T[])(new Object[2]);

    public int size() {
        return length;
    }

    public void add(T elem) throws CapacityOverflowException {
        if (arr.length + 1 > CAPACITY) {
            throw new CapacityOverflowException("New elements cannot be added to the array: " +
                    "there will be an overflow of capacity");
        } else {
            if (length + 1 >= arr.length) {
                T[] newArr = (T[])(new Object[(int) (arr.length * EXPANSION)]);
                for (int i = 0; i < length; i++) {
                    newArr[i] = arr[i];
                }
                arr = newArr;
            }
            arr[length] = elem;
            length++;
        }
    }

    public void add(int index, T elem) throws CapacityOverflowException {
        if (arr.length + 1 > CAPACITY) {
            throw new CapacityOverflowException("New elements cannot be added to the array: " +
                    "there will be an overflow of capacity");
        } else {
            if (arr.length + 1 > CAPACITY) {
                throw new CapacityOverflowException("New elements cannot be added to the array: " +
                        "there will be an overflow of capacity");
            } else {
                int newLength;
                if (length + 1 >= arr.length) {
                    newLength = (int) (arr.length * EXPANSION);
                } else {
                    newLength = arr.length;
                }
                T[] newArr = (T[])(new Object[newLength]);
                for (int i = 0; i < index; i++) {
                    newArr[i] = arr[i];
                }
                newArr[index] = elem;
                for (int i = index; i < length; i++) {
                    newArr[i + 1] = arr[i];
                }
                arr = newArr;
                length++;
            }
        }
    }

    public void set(int index, T elem) {
        arr[index] = elem;
    }

    public void remove(int index) throws ExpandableArrayIndexOutOfBoundsException {
        if ((index > -1) && (index < length)) {
            for (int i = index; i < length; i++) {
                arr[i] = arr[i + 1];
            }
            arr[length] = null;
            length--;
        } else {
            throw new ExpandableArrayIndexOutOfBoundsException("The array does not contain an element with this index."
                    + " Length of array: " + this.length + "; Index: " + index);
        }
    }

    public T get(int index) throws ExpandableArrayIndexOutOfBoundsException {
        T elem;
        if ((index > -1) && (index < length)) {
            elem = arr[index];
        } else {
            throw new ExpandableArrayIndexOutOfBoundsException("The array does not contain an element with this index"
                    + "Length of array:" + this.length + "; Index: " + index);
        }
        return elem;
    }

    public boolean contains(T elem) {
        for (int i = 0; i < length; i++) {
            if (elem.equals(arr[i])) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(T elem) {
        int index = -1;
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(elem)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void clear() {
        arr = (T[])(new Object[arr.length]);
        length = 0;
    }

    public boolean isEmpty() {
        return (length == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || (this.getClass() != o.getClass())) {
            return false;
        }
        NewExpandableArray array = ((NewExpandableArray) o);
        if (array.size() != length) {
            return false;
        } else {
            for (int i = 0; i < length; i++) {
                if (arr[i].equals(array.get(i))) {
                    return false;
                }
            }
            return true;
        }
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
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

}
