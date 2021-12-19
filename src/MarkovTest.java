import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Title: MarkovTest.java
 * Author: Nicholas Fotinakes
 * Abstract: This class tests the Markov class functionality. Uses a simple txt
 * file titled 'helloWorld.txt' which contains only 'Hello world.'
 * Date: 11/4/2021
 */
class MarkovTest {
    // Fields used to test Markov
    Markov testChain;
    String filename;
    HashMap<String, ArrayList<String>> testMap;

    // Set up by populating fields and a simple HashMap with known value
    // to test against simple txt file with 'Hello world.'
    @BeforeEach
    void setUp() {
        System.out.println("Processing setup...");
        testChain = new Markov();
        testMap = new HashMap<>();
        filename = "helloWorld.txt";
        testMap.put("__$", new ArrayList<>());
        testMap.put("Hello", new ArrayList<>());
        testMap.get("__$").add("Hello");
        testMap.get("Hello").add("world.");
    }

    // Reset to null values
    @AfterEach
    void tearDown() {
        System.out.println("Processing teardown...");
        testChain = null;
        filename = null;
    }

    // Assert that a non-null object was created
    @Test
    void Markov(){
        assertNotNull(testChain);
    }

    // Test that the HashMap generated from file matches what we expect
    @Test
    void getWords() {
        testChain.addFromFile(filename);
        assertEquals(testChain.getWords().keySet(), testMap.keySet());

    }

    // Test that the only possible generated sentence matches what we expect
    @Test
    void getSentence() {
        testChain.addFromFile(filename);
        assertEquals(testChain.getSentence(), "Hello world.");
    }

    // Test that the expected word will be returned when randomWord calls
    @Test
    void randomWord() {
        testChain.addFromFile(filename);
        assertEquals(testChain.randomWord("Hello"), "world.");
    }

    // Test that endsWithPunctuation detects when a word ends with punctuation
    @Test
    void endsWithPunctuation() {
        String endsWith = "cool.";
        String doesNotEndWith = "radical";
        assertTrue(Markov.endsWithPunctuation(endsWith));
        assertFalse(Markov.endsWithPunctuation(doesNotEndWith));

    }

    // Test that the toString outputs correctly
    @Test
    void testToString() {
        testChain.addFromFile(filename);
        assertEquals(testChain.toString(), testMap.toString());
        assertEquals(testChain.toString().length(), testMap.toString().length());

    }
}