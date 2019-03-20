import java.io.*;
import java.util.*;

/**
 * @author Scott O'Brien
 * @version 3/12/2019
 * This project uses a data structure known as a Trie. It will implement a spell checker and an inner class named
 * Lexicon that is implemented as a Trie.
 */
public class SpellChecker {

    public static void spellCorrect(String originalWord, int ln, Lexicon lex){
        List<String> suggestions = new ArrayList<>();
        char[] c = originalWord.toCharArray();

        System.out.println("Line " + ln + ": \"" + originalWord + "\" "  + "is not spelled correctly!");
        System.out.println("Suggestions:");

        for (int i = 0; i < c.length-1 ; i++) {
            String corrected = swapChars(i,i+1,c);
            if (lex.containsWord(corrected) == true){
                suggestions.add(corrected);
            }
        }

    }

    // method that allows us to swap characters in a string with given index values so we can do any 2 paired index val
    // ex: i & i+1 where i == 0, indexOne = 0, indexTwo = 1
    public static String swapChars(int indexOne, int indexTwo, char[] c) {
        char temp = c[indexOne];
        c[indexOne] = c[indexTwo];
        c[indexOne] = temp;
        String swappedString = new String(c);

        return swappedString;
    }

    static final int SIZE = 26;

    public static void main(String[] args) throws FileNotFoundException {
        // here we init a string list and a new Lexicon to use for our spell checker.
        List<String> stringList = new ArrayList<>();
        final Lexicon lex = new Lexicon();

        File file = new File("C:\\Users\\Kazoo\\Documents\\Homework\\enable1augmented.txt");

        Scanner input = new Scanner(file);

        while (input.hasNextLine()){
            stringList.add(input.nextLine());
        }
        input.close();

        stringList.forEach(lex::insert);

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter filename with ext. to spellcheck: ");
        File data = new File(scan.nextLine());
        input = new Scanner(data);
        while (input.hasNextLine()){
            String newWord = null;
            int ln = 0;
            String line = input.nextLine();
            String[] splitIntoWords = line.split("//s");
            for (int i = 0; i < splitIntoWords.length; i++) {
                newWord = splitIntoWords[i];
                if (lex.containsWord(newWord) == false) {
                    spellCorrect(newWord,ln,lex);
                }
            }


        }



    }

    static class Lexicon {
        /**
         * Lexicon has constructor and one public method, and 1 or more private helper methods. Will read all words from
         * enable1augmented.txt and build a Trie containing them.
         * containsWord (String word) is a public boolean method which returns true if the word is in the lexicon, false
         * otherwise.
         * @param LexNode This param contains our root of our Trie.
         */

        final LexNode root;

        public Lexicon (){
            this.root = new LexNode();
        }

        //public boolean containsWord returns true or false depending on whether or not a word is within the lexicon.
        //we will call this to check our trie if the word we want to spell check is spelt correctly.
        public boolean containsWord(final String word){
            LexNode current = root;
            for (int i = 0; i < word.length(); i++) {
                if (current==null || current.next(word.charAt(i))==null){
                    return false;
                }
            }
            return true;
        }

        //void insert is what we use to insert all of our words into the trie, pretty straight forward.
        public void insert(final String word){
            LexNode current = root;
            for (int i = 0; i < word.length(); i++) {
                if (current.lexNodes[word.charAt(i) - 'a'] == null) {
                    current.lexNodes[word.charAt(i) - 'a'] = new LexNode();
                }
                current = current.next(word.charAt(i));
            }
        }

    }

    //LexNode class is we create our arrays of nodes the same size as the alphabet (26).
    static class LexNode {
        final LexNode[] lexNodes = new LexNode[SIZE];

        public LexNode next(final char c) {
            return lexNodes[c-'a'];
        }
    }

}


