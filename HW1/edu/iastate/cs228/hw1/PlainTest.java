package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nathan Turnis
 */

class PlainTest {

    @Test
    void getWidth() {
        Plain p = new Plain(3);
        assertTrue(p.getWidth() == 3);
    }

    @Test
    void randomInit() {
        Plain p = new Plain(3);
        p.randomInit();
        System.out.println(p.toString());
    }

    @Test
    void testToString() {
        try {
            Plain p = new Plain("public1-3x3.txt");
            //System.out.println(p.toString());
            assertEquals(p.toString(), "G  B0 F0 \n" + "F0 F0 R0 \n" + "F0 E  G  \n");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }

    @Test
    void write() {
        Plain p = new Plain(3);
        p.randomInit();
        System.out.println(p.toString());
        try {
            p.write("randomgen.txt");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }
}