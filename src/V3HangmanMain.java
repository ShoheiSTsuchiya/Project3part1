import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class V3HangmanMain {
    //data members declare
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

        this.phrase = randomPhrase(phraseList);             //instance variable for random phrase
        this.hiddenPhrase = createHiddenPhrase(this.phrase);//for hidden phrase that selected
        this.previousGuesses = new StringBuilder();         //to track previous guesses
    }

    public static void main(String[] args) {

        System.out.println(" *** Welcome to the Wheel of Fortune V3 *** \r\n" +
                "Follow the rules: \r\n" +
                "1. Guess a letter you guess in the hidden phrase and press Enter.\r\n" +
                "2. The game calculates your guess. And get to you know what next step.\r\n" +
                "3. Keep playing to win(open every single hidden character) this game.\r\n");
        //make new instance names hangman
        V3HangmanMain hangman = new V3HangmanMain();

        System.out.println("Here is a random phrase.");
        System.out.println("Hidden code is " + hangman.hiddenPhrase);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of chances you want to play with:");
        int nChance = scanner.nextInt();

        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hangman.hiddenPhrase);

            char guessChar = hangman.getGuess(scanner);

            int matchCount = hangman.processGuess(guessChar);
            if (matchCount == 0) {
                nChance--;
                hangman.previousGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess. " + nChance + " chances left!");
            }
            else {
                System.out.println("CORRECT guess.");
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

    //instance of random phrase form the given list
    public String randomPhrase(List<String> phraseList) {
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        return phraseList.get(r);
    }

    //converts the given phrase to hidden version using asterisk
    public StringBuilder createHiddenPhrase(String phrase) {
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

    //get a character guess from player and return it
    public char getGuess(Scanner scanner) {
        System.out.println("Guess a character you think in hidden sentence :");
        String ch = scanner.next().toLowerCase();
        if (ch.length() == 1) {
            char guess = ch.charAt(0);
            if (Character.isLetter(guess)) {
                return guess;
            }
            else {
                System.out.println("Invalid input. Please enter a letter from a-z or A-Z.");
            }
        }
        else {
            System.out.println("Invalid input. Please enter a single character.");
        }
        return getGuess(scanner);
    }

    //check the character guessed against the phrase and update the hidden phrase
    public int processGuess(char guessChar) {
        int matchCount = 0;
        for (int i = 0; i < phrase.length(); i++) {
            char strChar = phrase.charAt(i);
            if (Character.toLowerCase(guessChar) == Character.toLowerCase(strChar)) {
                matchCount++;
                hiddenPhrase.setCharAt(i, strChar);
            }
        }
        return matchCount;
    }
}