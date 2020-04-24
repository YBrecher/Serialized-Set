package edu.touro.mco364;

import java.io.*;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MySet set = new MySet();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("1");
        set.add("2");


        try(FileOutputStream fos = new FileOutputStream("MySet.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)) // Decorator Design Pattern
        {
            oos.writeObject(set);
        }

        try(FileInputStream fis = new FileInputStream("MySet.txt");
            ObjectInputStream ois = new ObjectInputStream(fis)) // Decorator Design Pattern
        {
            MySet newSet = (MySet) ois.readObject();

            System.out.println(newSet.get(0));
            System.out.println(newSet.get(1));
            System.out.println(newSet.get(2));
            System.out.println(newSet.get(3));
            System.out.println(newSet.get(4));
            System.out.println(newSet.get(5));

        }

    }
}
