package ru.kpfu.itis.belskaya;

public class Main {
    public static void main(String[] args) {
        MySet<Integer> set = new MySet<>();
        for (int i = 1; i <= 10; i++) {
            set.add(i);
        }
        MySet<Integer> set2 = new MySet<>();
        set2.add(1);
        set2.add(3);
        set2.add(2);
        set2.add(9);
        set2.add(8);
        set2.add(10);
        set2.add(7);
        set2.add(6);
        set2.add(5);
        set2.add(4);
        set.stream().forEach(x -> System.out.println(x));
    }
}
