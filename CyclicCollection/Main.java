import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        CycleCollection<Integer> a = new CycleCollection<>(4);
        a.add(11);
        a.add(3333);
        a.add(126);
        a.add(3333);
        CycleCollection<Integer> b = new CycleCollection<>(10);
        b.add(126);
        b.add(3333);
        b.add(11);
        b.add(77);
        b.add(190);
//        System.out.println(a.toString());
//        a.add(2);
//        a.add(99);
//        System.out.println(a.toString());
//        a.remove(2);
//        System.out.println(a.toString());
//        a.add(888);
//        System.out.println(a.toString());
        a.stream().sorted().skip(2).forEach(s -> System.out.println(s));
        Set<Integer> set = b.stream().filter(s -> s < 250).collect(Collectors.toSet());
        System.out.println(b.stream().filter(s -> s>500).count());
    }
}
