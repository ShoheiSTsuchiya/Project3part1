import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class V4HangmanMain {

    private String phrase;
    private StringBuilder hiddenPhrase;
    private StringBuilder previousGuesses;

    public V4HangmanMain() {
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

        V4HangmanMain hangman = new V4HangmanMain();
        BotPlayer bot = new BotPlayer();

        System.out.println(hangman.phrase);
        System.out.println("Hidden code: " + hangman.hiddenPhrase);

        int nChance = 10;  // default number of chances

        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hangman.hiddenPhrase);

            char guessChar = bot.getGuess();

            boolean isCorrect = hangman.processGuess(guessChar);
            if (!isCorrect) {
                nChance--;
                hangman.previousGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess by Bot! " + nChance + " chances left!");
            }
            else {
                System.out.println("CORRECT guess by Bot!");
            }

            if (hangman.previousGuesses.length() > 0) {
                System.out.println("Incorrect guesses by Bot: " + hangman.previousGuesses.toString());
            }

            if (!hangman.hiddenPhrase.toString().contains("*")) {
                System.out.println("Congratulations to Bot! It guessed the hidden sentence: " + hangman.hiddenPhrase);
                break;
            }
        }

        if (nChance == 0) {
            System.out.println("Bot failed! The hidden sentence was: " + hangman.phrase);
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

    static class BotPlayer {
        private char currentGuess = 'a'; // start from 'a'

        public char getGuess() {
            return currentGuess++;
        }
    }
}