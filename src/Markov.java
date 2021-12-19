import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Title: Markov.java - Markov Text Generation
 * Author: Nicholas Fotinakes
 * Abstract: This class accepts a file input to parse words into a Markov Chain
 * organized in a HashMap which can then be used to autogenerate sentences.
 * Note: Referenced geeksforgeeks.com to learn replaceAll can use \\s+ to
 * trim long spaces of whitespace
 * Date: 11/4/2021
 */
public class Markov {

    private static final String PUNCTUATION = "__$";        // Key value for starting words
    private static final String PUNCTUATION_MARKS = ".!?";  // To check for words ending in punctuation
    private HashMap<String, ArrayList<String>> words;       // HashMap to create Markov Chain
    private String prevWord;                                // Hold previous word to populate chain

    /**
     * Constructor that creates a new HashMap for Markov Chain starting with
     * the PUNCTUATION key and empty ArrayList, and set prevWord
     */
    public Markov(){
        words = new HashMap<String, ArrayList<String>>();
        words.put(PUNCTUATION, new ArrayList<String>());
        prevWord = PUNCTUATION;
    }

    /**
     * The getWords method returns the words HashMap Markov Chain
     * @return words HashMap
     */
    HashMap<String, ArrayList<String>> getWords(){
        return words;
    }

    /**
     * The addFromFile method takes a filename and then creates a Scanner to read
     * and organize each line from the file. This is sent to addLine method to continue
     * building the HashMap
     * @param filename the name of the file we want to read
     */
    public void addFromFile(String filename){
        // Create File object from filename and a null Scanner
        File file = new File(filename);
        Scanner inputFile;

        // Create Scanner object from file to read a line and trim spaces
        // then send to addLine method. Catch any filename errors
        try {
            inputFile = new Scanner(file);
            while(inputFile.hasNext()){
                String input = inputFile.nextLine();
                input = input.replaceAll("\\s+", " ");
                input = input.trim();
                addLine(input);
            }
            inputFile.close();  // Close file
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file " + e);
        }
    }

    /**
     * The addLine method splits the file line sent from addFromFile by spaces into
     * a String array and sends each word to addWord method
     * @param str the trimmed file line from addFromFile method
     */
    void addLine(String str){
        // While line is not empty, split into singular words and send to addWords
        if(str.length() != 0){
            String[] splitValues = str.split(" ");
            for(String s : splitValues){
                addWord(s);
            }
        }
    }

    /**
     * The addWord method populates the HashMap to create the Markov Chain
     * @param str the word sent from addLine
     */
    void addWord(String str){
        // Series of checks to populate HashMap
        // First check if word ends with a punctuation mark
        if(endsWithPunctuation(str)){
            // If so and no key exists, create new key and empty ArrayList
            if (!words.containsKey(prevWord)) {
                words.put(prevWord, new ArrayList<String>());
            }
            // Otherwise, add word to corresponding key and set previous word
            words.get(prevWord).add(str);
            prevWord = PUNCTUATION;
            // Check if the previous word was a starting word
        } else if(prevWord.equals(PUNCTUATION)) {
            // If so add the word to PUNCTUATION key and set prevWord
            words.get(PUNCTUATION).add(str);
            prevWord = str;
            // Check if word already has a key created
        } else if(words.containsKey(prevWord)){
            // If so add value to key and set prevWord
            words.get(prevWord).add(str);
            prevWord = str;
            // If key doesn't exist, create one and empty ArrayList, then add value and set prevWord
        } else {
            words.put(prevWord, new ArrayList<String>() );
            words.get(prevWord).add(str);
            prevWord = str;
        }
    }

    /**
     * The getSentence method builds a random sentence from the created HashMap
     * Markov chain
     * @return the auto generated sentence
     */
    public String getSentence(){
        StringBuilder outputString = new StringBuilder(); // StringBuilder to create string
        ArrayList<String> puncList;                       // List to hold all starting words
        puncList = words.get(PUNCTUATION);                // Populate list
        Random rand = new Random();                       // Random object to choose words
        int size = words.get(PUNCTUATION).size();         // Size of starting words
        int holder;                                       // To hold index number
        holder = rand.nextInt(size);                      // Choose random index
        String currentWord;                               // To hold selected word

        // Choose random word from starting words and begin creating sentence
        currentWord = puncList.get(holder);
        // While chosen word doesn't end in sentence, keep adding words to String
        // using randomWord method until a word ending with punctuation appears
        while(!endsWithPunctuation(currentWord)){
            outputString.append(currentWord);
            outputString.append(" ");
            currentWord = randomWord(currentWord);
        }
        // After building add the final punctuation word and return the sentence
        outputString.append(currentWord);
        return outputString.toString();
    }

    /**
     * The randomWord method selects a random word from the Markov chain
     * @param str the previous word used from getSentence
     * @return the next chosen word based off the previous word
     */
    String randomWord(String str){
        ArrayList<String> wordList;         // List to hold word values from key
        int size = words.get(str).size();   // size of the list associated with key
        int holder;                         // index holder
        wordList = words.get(str);          // Populate list
        Random rand = new Random();         // Random object to select random word
        holder = rand.nextInt(size);        // The random index chosen
        return  wordList.get(holder);       // Return the random word chosen
    }

    /**
     * The endsWithPunctuation method check a string word to see if it ends with
     * a punctuation mark
     * @param str The word to check
     * @return  true or false depending on if word ends in punctuation
     */
    public static boolean endsWithPunctuation(String str){
        // The last character in a string
        char end = str.charAt(str.length() - 1);
        // Check the character against all punctuation characters saved in
        // the PUNCTUATION_MARKS field, return true if found
        for(int i = 0; i <PUNCTUATION_MARKS.length(); i++){
            if(end == PUNCTUATION_MARKS.charAt(i)){
                return true;
            }
        }
        // Return false if no punctuation found
        return false;
    }

    /**
     * The toString returns the Markov chain as a string
     * @return the HashMap "words"
     */
    @Override
    public String toString(){
        return words.toString();
    }






}
