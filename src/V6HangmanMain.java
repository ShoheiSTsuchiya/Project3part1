import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class V6HangmanMain {

    private String phrase;
    private StringBuilder hiddenPhrase;
    private StringBuilder previousGuesses;

    public V6HangmanMain() {
        List<String> phraseList = null;
        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        this.phrase = randomPhrase(phraseList);
        this.hiddenPhrase = createHiddenPhrase(this.phrase);
        this.previousGuesses = new StringBuilder();
    }

    public static void main(String[] args) {

        System.out.println(" *** Welcome to the Wheel of Fortune V6 *** \r\n" +
                "Follow the rules: \r\n" +
                "1. Guess a letter you guess in the hidden phrase and press Enter.\r\n" +
                "2. The game calculates your guess. And get to you know what next step.\r\n" +
                "3. Keep playing to win(open every single hidden character) this game.\r\n");

        V6HangmanMain hangman = new V6HangmanMain();
        BotPlayer bot = new BotPlayer();


        System.out.println("Here is a random phrase.");
        System.out.println("Hidden code: " + hangman.hiddenPhrase);

        int nChance = 15;  // default number of chances

        while (nChance > 0) {
            System.out.println(nChance + " chances left :");
            System.out.println("Guessed code: " + hangman.hiddenPhrase);

            char guessChar = bot.getGuess();

            int matchCount = hangman.processGuess(guessChar);
            if (matchCount == 0) {
                nChance--;
                hangman.previousGuesses.append(guessChar).append(" ");
                System.out.println("INCORRECT Guess by Bot. " + nChance + " chances left.");
            }
            else {
                System.out.println("CORRECT guess by Bot.");
            }

            if (hangman.previousGuesses.length() > 0) {
                System.out.println("Incorrect guesses by Bot: " + hangman.previousGuesses.toString());
            }

            if (!hangman.hiddenPhrase.toString().contains("*")) {
                System.out.println("Congratulations. Bot guessed the hidden sentence: " + hangman.hiddenPhrase);
                break;
            }
        }

        if (nChance == 0) {
            System.out.println("Bot failed. The hidden phrase was: " + hangman.phrase);
        }
    }

    public String randomPhrase(List<String> phraseList) {
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size());
        return phraseList.get(r);
    }

    public StringBuilder createHiddenPhrase(String phrase) {
        StringBuilder hiddenCode = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) != ' ') {
                hiddenCode.append('*');
            }
            else {
                hiddenCode.append(' ');
            }
        }
        return hiddenCode;
    }

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

// guess vowels first and some popular consonants second, and rest of characters last
    static class BotPlayer {
        private String vowels = "aeiou";
        private String popularConsonants = "riotnsl";

        //white down rest of alphabet in order
        private String otherLetters = "bcdfghjklmpquvwxyz";
        private int currentIndex = 0;
        private StringBuilder guessedLetters = new StringBuilder(); //Track Guessed Letters

        public char getGuess() {
            char guess;

            // guess vowels first
            if (currentIndex < vowels.length()) {
                guess = vowels.charAt(currentIndex);
            }
            // popular consonants second
            else if (currentIndex - vowels.length() < popularConsonants.length()) {
                guess = popularConsonants.charAt(currentIndex - vowels.length());
            }
            // anything else last
            else {
                guess = otherLetters.charAt(currentIndex - vowels.length() - popularConsonants.length());
            }

            currentIndex++;
            guessedLetters.append(guess);
            return guess;
        }
    }
}