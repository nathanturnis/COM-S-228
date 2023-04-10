package edu.iastate.cs228.hw3;

import java.util.Iterator;
import java.util.ListIterator;

public class MainStoutListTest {

    public static void main(String args[]){

        StoutList list = new StoutList(4);

        list.add("A");
        list.add("B");

        ListIterator<String> iter = list.listIterator();
        iter.next();
        iter.previous();
        iter.next();
        iter.add("C");


        System.out.println(list.toStringInternal(iter));

    }

}
