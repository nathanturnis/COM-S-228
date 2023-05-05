package iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Nathan Turnis
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Please enter filename to decode: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.next();
        scanner.close();

        ArrayList<String> fileLines = new ArrayList<>();

        try {
            File file = new File(fileName);
            Scanner fileScnr = new Scanner(file);

            while(fileScnr.hasNextLine()) {
                fileLines.add(fileScnr.nextLine());
            }
            fileScnr.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist!");
        }

        String encodedString = "";
        for(int i = 0; i < fileLines.size() - 2; i++) {
            encodedString += fileLines.get(i) + '\n';
        }
        encodedString += fileLines.get(fileLines.size() - 2);
        String binaryString = fileLines.get(fileLines.size() - 1);

        MsgTree tree = new MsgTree(encodedString);

        System.out.println("character  code\n-------------------------");
        tree.printCodes(tree, "");

        System.out.println("\nMESSAGE:");
        tree.decode(tree, binaryString);
    }
}