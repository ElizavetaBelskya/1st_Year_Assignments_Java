

interface ArraysStructure<T> {
    T get(int index);

    int size();

    void add(int index, T elem);

    void add(T elem);

    void set(int index, T elem);

    void remove(int index);

    boolean contains(T elem);

    int indexOf(T elem);

    boolean isEmpty();

    void clear();
}

