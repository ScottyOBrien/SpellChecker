import java.io.*;
import java.util.*;

/**
 * @author Scott O'Brien
 * @version 3/12/2019
 * This project uses a data structure known as a Trie. It will implement a spell checker and an inner class named
 * Lexicon that is implemented as a Trie.
 */
public class SpellChecker {

    // this method performs all of our suggestion techniques for an individual word
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

        char[] cCopy = c;
        for (int i = 0; i < c.length-1; i++) {
            for (int ascii = 97; ascii != 123 ; ascii++) {
                String corrected = insertAthruZ(i, cCopy, ascii);
                if (lex.containsWord(corrected)){
                    suggestions.add(corrected);
                }
            }

        }

    }

    public static String insertAthruZ (int index, char[] c, int ascii){
            char testChar = (char) ascii;
            c[index] = testChar;
            String insertedString = new String(c);

            return insertedString;
    }

    // method that allows us to swap characters in a string with given index values so we can do any 2 paired index val
    // ex: i & i+1 where i == 0, indexOne = 0, indexTwo = 1
    public static String swapChars(int indexOne, int indexTwo, char[] c) {
        char[] copy = c.clone();
        char temp = copy[indexOne];
        copy[indexOne] = copy[indexTwo];
        copy[indexTwo] = temp;
        String swappedString = new String(copy);

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
        String nextWord = null;
        int ln = 0;
        while (input.hasNextLine()){
            String line = input.nextLine();
            String[] splitIntoWords = line.split(" ");
            for (int i = 0; i < splitIntoWords.length; i++) {
                nextWord = splitIntoWords[i];
                nextWord.replaceAll("[^a-zA-Z]", "").toLowerCase();

                if (lex.containsWord(nextWord) == false) {
                    spellCorrect(nextWord,ln,lex);
                }
            }
            ln++;


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
                else
                    current=current.next(word.charAt(i));
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


