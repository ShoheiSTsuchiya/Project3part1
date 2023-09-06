import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class V3HangmanMain {

    private String phrase;
    private StringBuilder hiddenPhrase;
    private StringBuilder previousGuesses;

    public V3HangmanMain() {
        List<String> phraseList = null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        this.phrase = randomPhrase(phraseList);
        this.hiddenPhrase = generateHiddenPhrase(this.phrase);
        this.previousGuesses = new StringBuilder();
    }

    public static void main(String[] args) {

        System.out.println(" ***Welcome to the Wheel of Fortune*** \r\n" +
                "Follow these rules: \r\n" +
                "1. Guess the letter regardless of case and press Enter.\r\n" +
                "2. The game calculates a guess and returns a reference.\r\n" +
                "3. Keep playing to win the game.\r\n");

        V3HangmanMain hangman = new V3HangmanMain();

        System.out.println(hangman.phrase);
        System.out.println("Hidden code is " + hangman.hiddenPhrase);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of chances you want to play with:");
        int nChance = scanner.nextInt();

        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hangman.hiddenPhrase);

            char guessChar = hangman.getGuess(scanner);

            boolean isCorrect = hangman.processGuess(guessChar);
            if (!isCorrect) {
                nChance--;
                hangman.previousGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess! " + nChance + " chances left!");
            }
            else {
                System.out.println("CORRECT guess!");
            }

            if (hangman.previousGuesses.length() > 0) {
                System.out.println("Incorrect guesses: " + hangman.previousGuesses.toString());
            }

            if (!hangman.hiddenPhrase.toString().contains("*")) {
                System.out.println("Congratulations! You guessed the hidden sentence: " + hangman.hiddenPhrase);
                break;
            }
        }

        if (nChance == 0) {
            System.out.println("Out of chances. The hidden sentence was: " + hangman.phrase);
        }
    }

    private String randomPhrase(List<String> phraseList) {
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        return phraseList.get(r);
    }

    private StringBuilder generateHiddenPhrase(String phrase) {
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

    private char getGuess(Scanner scanner) {
        System.out.println("Guess a character you think in hidden sentence :");
        String ch = scanner.next().toLowerCase();
        if (ch.length() == 1) {
            char guess = ch.charAt(0);
            if (Character.isLetter(guess)) {
                return guess;
            } else {
                System.out.println("Invalid input. Please enter a letter from a-z or A-Z.");
            }
        } else {
            System.out.println("Invalid input. Please enter a single character.");
        }
        return getGuess(scanner);
    }

    private boolean processGuess(char guessChar) {
        boolean found = false;
        for (int i = 0; i < phrase.length(); i++) {
            char strChar = phrase.charAt(i);
            if (Character.toLowerCase(guessChar) == Character.toLowerCase(strChar)) {
                found = true;
                hiddenPhrase.setCharAt(i, strChar);
            }
        }
        return found;
    }
}