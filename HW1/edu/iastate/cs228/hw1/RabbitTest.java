package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nathan Turnis
 */

class RabbitTest {

    @Test
    void next() {

        try{
            Plain p = new Plain("public2-6x6.txt");
            Plain newP = new Plain(6);
            Living living = p.grid[1][3].next(newP);
            assertEquals(living.who(), State.BADGER);
            assertEquals(((Animal) living).myAge(), 0);
        } catch(FileNotFoundException e){

        }

    }
}