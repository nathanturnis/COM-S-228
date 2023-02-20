package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nathan Turnis
 *
 */

class WildlifeTest {

    @Test
    void updatePlain() {

        try {
            Plain oldPlain = new Plain("samples3x3.txt");
            Plain newPlain = new Plain(oldPlain.getWidth());
            for(int i = 0; i < oldPlain.getWidth(); i++){
                for(int j = 0; j < oldPlain.getWidth(); j++){
                    Wildlife.updatePlain(oldPlain, newPlain);
                }
            }
            Plain expectedPlain = new Plain("samples3x3-1cycle.txt");
            assertEquals(newPlain, expectedPlain);
        } catch(FileNotFoundException e){
            System.out.println("File not found");
        }

    }
}