import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class HangmanMain {
    public static void main(String[] args) {

        System.out.println(" ***Welcome to the Wheel of Fortune*** \r\n" +
                "Follow these rules: \r\n" +
                "1. Guess the letter regardless of case and press Enter.\r\n" +
                "2. The game calculates a guess and returns a reference.\r\n" +
                "3. Keep playing to win the game.\r\n");

        List<String> phraseList = null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Random Phrase Selection
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        String phrase = phraseList.get(r);
        System.out.println(phrase);

        // Generate Hidden Phrase
        StringBuilder hiddenCode = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) != ' ') {
                hiddenCode.append('*');
            } else {
                hiddenCode.append(' ');
            }
        }
        System.out.println("Hidden code: " + hiddenCode);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of chances you want to play with:");
        int nChance = scanner.nextInt();
        StringBuilder incorrectGuesses = new StringBuilder();

        StringBuilder guessCh = new StringBuilder(hiddenCode); // Added declaration for guessCh

        while(nChance > 0){
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + guessCh);

            System.out.println("Guess a character you think in hidden sentence :");

            String ch = scanner.next().toLowerCase();
            if(ch.length() == 1){
                char guessChar = ch.charAt(0);
                boolean found = false;
                for (int i = 0; i < phrase.length(); i++) { // Changed 'str' to 'phrase'
                    char phraseChar = phrase.charAt(i); // Changed 'strChar' to 'phraseChar' for clarity
                    if (Character.toLowerCase(guessChar) == Character.toLowerCase(phraseChar)) {
                        found = true;
                        guessCh.setCharAt(i, phraseChar);
                    }
                }
                if (!found){
                    nChance--;
                    incorrectGuesses.append(guessChar).append(" ");
                    System.out.println("INCORRECT Guess! " + nChance + " chances left!");
                }
                else{
                    System.out.println("CORRECT guess!");
                }
            }
            else{
                System.out.println("Invalid input. Please enter a single character.");
            }

            if (incorrectGuesses.length() > 0) {
                System.out.println("Incorrect guesses: " + incorrectGuesses);
            }

            if (!guessCh.toString().contains("*")) {
                System.out.println("Congratulations! You guessed the hidden sentence: " + guessCh);
                break;
            }
        }

        if (nChance == 0) {
            System.out.println("Out of chances. The hidden sentence was: " + phrase); // Changed 'str' to 'phrase'
        }
    }
}



