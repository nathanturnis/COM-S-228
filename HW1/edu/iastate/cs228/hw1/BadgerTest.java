package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nathan Turnis
 */

class BadgerTest {

    @Test
    void next() {
        try {
            Plain p = new Plain("public2-6x6.txt");
            Plain newP = new Plain(6);
            Living living = p.grid[1][2].next(newP);
            assertEquals(living.who(), State.EMPTY);
            newP = new Plain(6);
            living = p.grid[5][3].next(newP);
            assertEquals(((Animal) living).myAge(), 1);
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }
}