package iastate.cs228.hw4;

/**
 * @author Nathan Turnis
 */
public class MsgTree {

    public char payloadChar;
    public MsgTree left;
    public MsgTree right;

    private static int staticCharIdx = 0;

    /**
     * Constructor for building a tree from a string
     * @param encodingString
     */
    public MsgTree(String encodingString) {
        if(encodingString.length() <= 1 || encodingString == null) {
            return;
        }

        this.payloadChar = encodingString.charAt(staticCharIdx++);
        while(staticCharIdx < encodingString.length()) {
           if(this.payloadChar == '^') {
               this.left = new MsgTree(encodingString);
               this.right = new MsgTree(encodingString);
               return;

           } else {
               this.left = null;
               this.right = null;
               return;
           }
        }
    }

    /**
     * Constructor for a single node with null children
     * @param payloadChar
     */
    public MsgTree(char payloadChar) {
        this.payloadChar = payloadChar;
        left = null;
        right = null;
    }

    /**
     * Prints characters and their binary codes
     * @param root
     * @param code
     */
    public static void printCodes(MsgTree root, String code) {
        if (root == null) return;

        if(root.payloadChar != '^') {
            System.out.println(String.format("%-11s", root.payloadChar) + String.format("%-10s", code));
        }
        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    /**
     * Decodes the given binary message using a MsgTree
     * @param codes
     * @param msg
     */
    public void decode(MsgTree codes, String msg) {
        MsgTree tree = codes;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if(c == '0') {
                tree = tree.left;
            } else {
                tree = tree.right;
            }

            if(tree.payloadChar != '^') {
                stringBuilder.append(tree.payloadChar);
                tree = codes;
            }
        }
        System.out.println(stringBuilder);
        statistics(msg, stringBuilder.toString());
    }

    /**
     * Extra credit method that display statistics
     * @param encodedStr
     * @param decodedStr
     */
    public void statistics(String encodedStr, String decodedStr) {
        System.out.println("\nSTATISTICS:");
        double avgBitsChar = encodedStr.length() / (double) decodedStr.length();
        int totalChar = decodedStr.length();
        double spaceSavings = ((1 - decodedStr.length() / (double) encodedStr.length()) * 100);
        System.out.println(String.format("%-20s", "Avg bits/char:") + String.format("\t%.1f", avgBitsChar));
        System.out.println(String.format("%-20s", "Total characters:") + "\t" + totalChar);
        System.out.println(String.format("%-20s", "Space savings:") + String.format("\t%.1f", spaceSavings) + "%");
    }
}
