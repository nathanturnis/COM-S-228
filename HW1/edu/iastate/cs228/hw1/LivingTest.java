package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nathan Turnis
 */

class LivingTest {

    @Test
    void census() {

        try {
            Plain p = new Plain("public1-3x3.txt");
            //test 3x3 middle
            int livingCount1[] = new int[5];
            p.grid[1][1].census(livingCount1);
            int[] expectedArray1 = new int[] {1, 1, 4, 2, 1};
            assertArrayEquals(livingCount1, expectedArray1);

            //test 2x2 top right
            int livingCount2[] = new int[5];
            p.grid[0][0].census(livingCount2);
            int[] expectedArray2 = new int[] {1, 0, 2, 1, 0};
            assertArrayEquals(livingCount2, expectedArray2);

            //test 2x2 bottom right
            int livingCount3[] = new int[5];
            p.grid[2][2].census(livingCount3);
            int[] expectedArray3 = new int[] {0, 1, 1, 1, 1};
            assertArrayEquals(livingCount3, expectedArray3);


            p = new Plain("public2-6x6.txt");
            //test 3x2 left
            int livingCount4[] = new int[5];
            p.grid[3][0].census(livingCount4);
            int[] expectedArray4 = new int[]{2, 3, 0, 0, 1};
            assertArrayEquals(livingCount4, expectedArray4);

            //test 2x3 top
            int livingCount5[] = new int[5];
            p.grid[0][1].census(livingCount5);
            int[] expectedArray5 = new int[] {2, 2, 2, 0, 0};
            assertArrayEquals(livingCount5, expectedArray5);

            //test 3x2 right
            int livingCount6[] = new int[5];
            p.grid[2][5].census(livingCount6);
            int[] expectedArray6 = new int[] {1, 1, 1, 2, 1};
            assertArrayEquals(livingCount6, expectedArray6);

            //test 2x3 bottom
            int livingCount7[] = new int[5];
            p.grid[5][3].census(livingCount7);
            int[] expectedArray7 = new int[] {1, 3, 0, 1, 1};
            assertArrayEquals(livingCount7, expectedArray7);

            //text 1x1 plain
            p = new Plain("public4-1x1.txt");
            int livingCount8[] = new int[5];
            p.grid[0][0].census(livingCount8);
            int[] expectedArray8 = new int[]{1, 0, 0, 0, 0};
            assertArrayEquals(livingCount8, expectedArray8);
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        }
    }
}