package ru.vsu.cs.ovsyannikov.test;

import ru.vsu.cs.ovsyannikov.util.MyLinkedList;
import ru.vsu.cs.ovsyannikov.util.MyList;

import java.util.Random;

public class MyLinkedListTest {

    public static void main(String[] args) {

        // Create of my linked list implementation
        MyList<Integer> list = new MyLinkedList<>();

        // Add some objects to list
        list.add(1);
        list.add(2);
        list.add(3);

        // Add object via id
        list.add(0, 7);
        list.add(2, 8);
        list.add(list.size() - 1, 9);

        // Print array
        System.out.println(list + " Size: " + list.size());

        list.remove(0);
        list.remove(list.size() - 1);

        // Print array
        System.out.println("List after removing head and tail elements: " + list + " Size: " + list.size());

        // Foreach test
        System.out.print("Print list with foreach: \n[");
        for (Integer i : list) {
            System.out.print(i + " | ");
        }
        System.out.println("]");

        // Get elements by id
        System.out.print("Print list with for: \n(");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + ", ");
        }
        System.out.println(")");

        // 25 random swaps
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            int left = random.nextInt(list.size());
            int right = random.nextInt(list.size());
            list.swapNodes(left, right);
            System.out.println("Swap " + left + " and " + right + " nodes: " + list);
        }

        // Cleaning list
        list.clear();

        // Print array
        System.out.println("List after cleaning: " + list + " Size: " + list.size());
    }

}
