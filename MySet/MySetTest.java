

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class MySetTest {

    public MySet<Integer> set;

    @Before
    public void fillSet() {
        set = new MySet<>();
        for (int i = 1; i <= 10; i++) {
            set.add(i);
        }
    }

    @Test
    public void testContains() {
        boolean result = set.contains(4);
        Assert.assertTrue(result);
    }

    @Test
    public void testContainsForExcluded() {
        boolean result = set.contains(62);
        Assert.assertFalse(result);
    }

    @Test
    public void testContainsForAnotherType() {
        boolean result = set.contains((short) 5);
        Assert.assertFalse(result);
    }

    @Test
    public void testToArray() {
        Object[] actuals = set.toArray();
        Object[] expected = new Object[]{1,2,3,4,5,6,7,8,9,10};
        Assert.assertTrue(equalsForIncoherentArrays(actuals, expected));
    }

    @Test
    public void TestToArrayWithType() {
        Integer[] array = new Integer[10];
        Integer[] actuals = set.toArray(array);
        Integer[] expected = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        Assert.assertTrue(equalsForIncoherentArrays(actuals, expected));
    }

    @Test
    public void TestToArrayWithTypeLessThanLength() {
        Integer[] array = new Integer[3];
        Integer[] actuals = set.toArray(array);
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10};
        Assert.assertTrue(equalsForIncoherentArrays(actuals, expected));
    }

    @Test(expected = NullPointerException.class)
    public void addNullExpression() {
        set.add(null);
    }

    @Test(expected = IllegalStateException.class)
    public void TestAddMoreThanCapacity(){
        for (int i = 11; i <= 1001; i++) {
            set.add(i);
        }
    }

    @Test
    public void TestAddExistingElement() {
        Assert.assertFalse(set.add(1));
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNull() {
        set.remove(null);
    }

    @Test
    public void testRemoveNonexistentElement() {
        Assert.assertFalse(set.remove(-200));
    }

    @Test
    public void testRemoveExistingElement() {
        Assert.assertTrue(set.remove(2));
    }

    @Test
    public void testContainsAll() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(7);
        Assert.assertTrue(set.containsAll(list));
    }

    @Test
    public void testContainsAllForIncorrectData() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(98);
        Assert.assertFalse(set.containsAll(list));
    }

    @Test
    public void testEquals() {
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
        Assert.assertTrue(set.equals(set2));
    }

    @Test
    public void testHashCode() {
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
        Assert.assertTrue(set.hashCode() == set2.hashCode());
    }

    @Test
    public void testAddAll() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);
        list.add(300);
        set.addAll(list);
        Integer[] expected = {1,2,3,4,5,6,7,8,9,10, 100, 200, 300};
        Integer[] actuals = new Integer[13];
        Assert.assertTrue(equalsForIncoherentArrays(set.toArray(actuals), expected));
    }

    @Test(expected = NullPointerException.class)
    public void testAddAllWithNull() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(null);
        set.addAll(list);
    }

    @Test
    public void testRemoveAll() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(99);
        set.removeAll(list);
        Assert.assertEquals(set.size(), 8);
    }

    @Test
    public void testClear() {
        set.clear();
        Assert.assertEquals(set.size(), 0);
    }

    @Test
    public void testRetainAll() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(9);
        set.retainAll(list);
        Assert.assertEquals(set.size(), 3);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(set.toString(), "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]");
    }

    public <T> boolean equalsForIncoherentArrays(T[] actuals, T[] expected) {
        if (actuals.length != expected.length) {
            return false;
        }
        for (T firstElement: actuals) {
            boolean founded = false;
            for (int i = 0; i < expected.length; i++) {
                if (firstElement.equals(expected[i])) {
                    expected[i] = null;
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

}
