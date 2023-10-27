

public class Container <T> {
    private T obj;
    public Container(T obj) {
        this.obj = obj;
    }

    public Container() {

    }

    public T getValue() {
        return obj;
    }

    public void setValue(T obj) {
        this.obj = obj;
    }


}
