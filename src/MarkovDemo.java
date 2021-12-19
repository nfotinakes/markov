import java.util.ArrayList;
import java.util.HashMap;

public class MarkovDemo {
    public static void main(String[] args) {
        Markov markov = new Markov();

        markov.addFromFile("spam.txt");
        System.out.println(markov);

        for (int i = 0; i < 10; i++){
            System.out.println(markov.getSentence());
        }








        /*test.addFromFile("spam.txt");
        System.out.println(test.getWords());
        System.out.println(test.randomWord("get"));
        System.out.println(test.getSentence());*/

    }
}
