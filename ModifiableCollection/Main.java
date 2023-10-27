

import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        ModifiableCollection<Integer> nums = new ModifiableCollection<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        System.out.println(nums.toString());
        Iterator<Integer> it = nums.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            it.remove();
            System.out.println(nums.toString());
        }
        System.out.println(nums.toString());
    }
}
