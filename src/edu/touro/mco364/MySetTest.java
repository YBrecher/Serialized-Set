package edu.touro.mco364;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MySetTest {
    private MySet test = new MySet();

    @org.junit.jupiter.api.Test
    void size() {
        int result = test.size();
        assertEquals(0, result);
        test.add(4);
        test.add(6);
        result = test.size();
        assertEquals(2, result);
        test.add("B");
        result = test.size();
        assertEquals(3, result);
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        boolean result = test.isEmpty();
        assertTrue(result);
        test.add('v');
        result = test.isEmpty();
        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    void contains() {
        test.add("A");
        test.add("B");
        test.add("C");
        boolean result = test.contains("B");
        assertTrue(result);
        result = test.contains(7);
        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    void add() {
        test.add("A");
        assertEquals("A", test.get(0));
        test.add("A");
        test.add(12);
        assertEquals(12, test.get(1));
        for (int i = 0; i < 25; i++){
            test.add("Hello" +i);
        }
        assertEquals("Hello20", test.get(22));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        test.add("A");
        test.add("B");
        test.add("C");
        test.remove("B");
        assertEquals("C", test.get(1));
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        test.add("A");
        test.add("B");
        test.add("C");

        LinkedList col = new LinkedList();
        col.add("A");
        col.add("B");
        col.add("C");

        assertTrue(test.containsAll(col));
        col.add("D");
        assertFalse(test.containsAll(col));

    }

    @org.junit.jupiter.api.Test
    void addAll() {
        test.add(1);
        test.add(2);
        test.add(3);

        LinkedList col1 = new LinkedList();
        col1.add(1);
        col1.add(2);
        col1.add(3);
        col1.add("A");

        ArrayList col2 = new ArrayList();
        col2.add("A");
        col2.add("B");
        col2.add("C");

        test.addAll(col1);
        assertEquals(4, test.size());

        test.addAll(col2);
        assertEquals(6, test.size());

    }

    @org.junit.jupiter.api.Test
    void removeAll() {
        test.add("A");
        test.add(3);
        test.add("B");
        test.add(5);

        ArrayList col = new ArrayList();
        col.add("A");
        col.add("B");

        test.removeAll(col);
        assertEquals(3, test.get(0));
        assertEquals(5, test.get(1));

    }

    @org.junit.jupiter.api.Test
    void retainsAll() {
        test.add("A");
        test.add(3);
        test.add("B");
        test.add(5);

        ArrayList col = new ArrayList();
        col.add("A");
        col.add("B");

        test.retainAll(col);
        assertEquals("A", test.get(0));
        assertEquals("B", test.get(1));

    }

    @org.junit.jupiter.api.Test
    void clear() {
        test.add(10);
        test.clear();
        assertEquals(0,test.size());

    }

    @org.junit.jupiter.api.Test
    void iterator() {
        test.add("A");
        test.add("B");
        test.add("C");
        Iterator iter = test.iterator();
        Object result = iter.next();
        assertEquals("A",result);
        result = iter.next();
        assertEquals("B",result);
        result = iter.next();
        assertEquals("C",result);
        assertFalse(iter.hasNext());

    }

    @org.junit.jupiter.api.Test
    void toArray() {
        test.add("A");
        test.add("B");
        test.add("C");
        Object list[] = test.toArray();
        assertEquals("B", list[1]);
    }

    @org.junit.jupiter.api.Test
    void toArrayIntoArray() {
        test.add("A");
        test.add("B");
        test.add("C");
        String array[] = new String[10];
        test.toArray(array);
        assertEquals("C", array[2]);
    }

    @org.junit.jupiter.api.Test
    void Serializable() throws IOException, ClassNotFoundException {
        test.add("A");
        test.add("B");
        test.add("C");
        test.add(1);
        test.add(2);


        try(FileOutputStream fos = new FileOutputStream("MySet.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)) // Decorator Design Pattern
        {
            test.myWriteObject(oos);
        }

        try(FileInputStream fis = new FileInputStream("MySet.txt");
            ObjectInputStream ois = new ObjectInputStream(fis)) // Decorator Design Pattern
        {
            MySet newSet =  test.myReadObject(ois);

           assertEquals("A", newSet.get(0));
           assertEquals("B", newSet.get(1));
           assertEquals("C", newSet.get(2));
           assertEquals(1, newSet.get(3));
           assertEquals(2, newSet.get(4));

        }

    }



}