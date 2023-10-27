

import java.util.Arrays;

public class ExpandableArray<T> implements ArraysStructure {
    private final int CAPACITY = 1000;
    private final float EXPANSION = 1.5f;
    private int length = 0;
    private Container[] arr = new Container[2];
    public int size() {
        return length;
    }

    public void add(Object elem) throws CapacityOverflowException {
        Container<T> elemContainer = new Container<>((T) elem);
        if (arr.length + 1 > CAPACITY) {
            throw new CapacityOverflowException("New elements cannot be added to the array: " +
                    "there will be an overflow of capacity");
        } else {
            if (arr.length <= length + 1) {
                Container[] newArr = new Container[(int) (arr.length * EXPANSION)];
                for (int i = 0; i < length; i++) {
                    newArr[i] = arr[i];
                }
                arr = newArr;
            }
            arr[length] = elemContainer;
            length++;
        }
    }

    public void add(int index, Object elem) throws CapacityOverflowException {
        Container<T> elemContainer = new Container<>((T) elem);
        if (arr.length + 1 > CAPACITY) {
            throw new CapacityOverflowException("New elements cannot be added to the array: " +
                    "there will be an overflow of capacity");
        } else {
            Container[] newArr = new Container[(int) (arr.length * 1.5)];
            for (int i = 0; i < index; i++) {
                newArr[i] = arr[i];
            }
            newArr[index] = elemContainer;
            for (int i = index; i < length; i++) {
                newArr[i + 1] = arr[i];
            }
            arr = newArr;
            length++;
        }
    }

    public void set(int index, Object elem) {
        Container<T> elemContainer = new Container<>((T) elem);
        arr[index] = elemContainer;
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
        Container<T> elem;
        if ((index > -1) && (index < length)) {
            elem = arr[index];
        } else {
            throw new ExpandableArrayIndexOutOfBoundsException("The array does not contain an element with this index"
                    + "Length of array:" + this.length + "; Index: " + index);
        }
        return elem.getValue();
    }

    public boolean contains(Object elem) {
        for (int i = 0; i < length; i++) {
            if ((arr[i].getValue()).equals(elem)) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(Object elem) {
        int index = -1;
        for (int i = 0; i < length; i++) {
            if ((arr[i].getValue()).equals(elem)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void clear() {
        arr = new Container[arr.length];
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
        ExpandableArray<T> array = ((ExpandableArray<T>) o);
        if (array.size() != length) {
            return false;
        } else {
            for (int i = 0; i < length; i++) {
                if (!arr[i].getValue().equals(array.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public String toString() {
        Object[] newArr = new Object[length];
        for (int i = 0; i < length; i++) {
            newArr[i] = arr[i].getValue();
        }
        return Arrays.toString(newArr);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

}