import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


public class V2HangmanMain {
    public static void main(String[] args) {

        System.out.println(" *** Welcome to the Wheel of Fortune V2 *** \r\n" +
                "Follow the rules: \r\n" +
                "1. Guess a letter you guess in the hidden phrase and press Enter.\r\n" +
                "2. The game calculates your guess and returns a reference.\r\n" +
                "3. Keep playing to win(open every single hidden character) this game.\r\n");

        List<String> phraseList = null;
        // Get the phrase from a file of phrases
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        String phrase = randomPhrase(phraseList);

    //play game with some static method here
    //make a hidden phrase first
        System.out.println("Here is a random phrase.");
        StringBuilder hiddenCode = generateHiddenPhrase(phrase);
        System.out.println("Hidden code: " + hiddenCode);
    //set up number of chances
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of chances you want to play with:");
        int nChance = scanner.nextInt();
    //make StringBuilder to incorrect guesses
        StringBuilder incorrectGuesses = new StringBuilder();
        // keep guesses until chances exists
        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hiddenCode);
            char guessChar = getGuess(scanner);

            int matchFound = processGuess(phrase, hiddenCode, guessChar);

            if (matchFound == 0) {
                nChance--;
                incorrectGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess! " + nChance + " chances left!");
            }
            else {
                System.out.println("CORRECT guess!");
            }
//print incorrect guess when user get
            if (incorrectGuesses.length() > 0) {
                System.out.println("Incorrect guesses: " + incorrectGuesses.toString());
            }
//if * is 0, print and end the loop
            if (!hiddenCode.toString().contains("*")) {
                System.out.println("Congratulations! You guessed the hidden sentence: " + hiddenCode);
                break;
            }
        }

        if (nChance == 0) {
            System.out.println("Out of chances. The hidden sentence was: " + phrase);
        }
    }

    // Returns a single phrase randomly chosen from a list
    public static String randomPhrase(List<String> phraseList) {
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        return phraseList.get(r);
    }

    // Returns the initial hidden phrase
    public static StringBuilder generateHiddenPhrase(String phrase) {
        StringBuilder hiddenCode = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) != ' ') {
                hiddenCode.append('*');
            } else {
                hiddenCode.append(' ');
            }
        }
        return hiddenCode;
    }

    // Gets input from user and returns it
    public static char getGuess(Scanner scanner) {
        System.out.println("Guess a character you think in hidden sentence :");
        String ch = scanner.next().toLowerCase(); // convert to lowercase for consistency
        if (ch.length() == 1) {
            char guess = ch.charAt(0);
            if (Character.isLetter(guess)) {  // check if the character is a letter
                return guess;
            }
            else {
                System.out.println("Invalid input. Please enter a letter from a-z or A-Z.");
            }
        }
        else {
            System.out.println("Invalid input. Please enter a single character.");
        }
        return getGuess(scanner);  // recursively get input until a valid guess is made
    }


    // Returns whether a letter matches, and modifies the partially hidden phrase if there is a match
    public static int processGuess(String phrase, StringBuilder guessCh, char guessChar) {
        int matchCount = 0;
        for (int i = 0; i < phrase.length(); i++) {
            char strChar = phrase.charAt(i);
            if (Character.toLowerCase(guessChar) == Character.toLowerCase(strChar)) {
                matchCount++;
                guessCh.setCharAt(i, strChar);
            }
        }
        return matchCount;
    }
}